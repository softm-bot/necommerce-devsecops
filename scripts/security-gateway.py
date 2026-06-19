#!/usr/bin/env python3
"""
Security Gateway — сводка отчётов SAST / DAST / Security Checks.

Политика (STRICT=0):
  BLOCK — Gitleaks >= 1; Semgrep severity ERROR >= 1
  WARN  — Trivy Image CRITICAL/HIGH; ZAP alerts; npm audit critical/high

STRICT=1 — дополнительно BLOCK при Trivy Image CRITICAL >= 1 или npm critical >= 1
"""
from __future__ import annotations

import json
import os
import sys
from collections import Counter
from pathlib import Path

REPORTS = Path(os.environ.get("REPORTS_DIR", "reports"))
OUT_MD = Path(os.environ.get("GATEWAY_MD", "gateway-report.md"))
OUT_JSON = Path(os.environ.get("GATEWAY_JSON", "gateway-result.json"))
STRICT = os.environ.get("STRICT", "0") == "1"


def load_json(path: Path):
    if not path.exists() or path.stat().st_size == 0:
        return None
    try:
        return json.loads(path.read_text(encoding="utf-8"))
    except json.JSONDecodeError:
        return None


def find_file(*parts: str) -> Path | None:
    p = REPORTS.joinpath(*parts)
    return p if p.exists() else None


def count_gitleaks() -> tuple[int, list]:
    for rel in ("gitleaks/gitleaks-report.json", "gitleaks-report.json"):
        data = load_json(REPORTS / rel)
        if data is None:
            continue
        if isinstance(data, list):
            return len(data), data
        findings = data.get("findings") or data.get("results") or []
        return len(findings), findings
    return 0, []


def count_semgrep(name: str) -> tuple[int, int]:
    """returns (total, error_count)"""
    for rel in (f"sast/{name}", name):
        data = load_json(REPORTS / rel)
        if data is None:
            continue
        results = data.get("results") or []
        errors = sum(
            1 for r in results
            if str(r.get("extra", {}).get("severity", "")).upper() == "ERROR"
        )
        return len(results), errors
    return 0, 0


def count_trivy_json(path: Path | None) -> Counter:
    sev = Counter()
    if path is None:
        return sev
    data = load_json(path)
    if not data:
        return sev
    for r in data.get("Results") or []:
        for v in r.get("Vulnerabilities") or []:
            sev[v.get("Severity", "?")] += 1
    return sev


def count_zap() -> tuple[int, int, int]:
    for rel in ("dast/zap-report.json", "zap-report.json"):
        data = load_json(REPORTS / rel)
        if data is None:
            continue
        alerts = []
        if isinstance(data.get("site"), list) and data["site"]:
            alerts = data["site"][0].get("alerts") or []
        high = sum(1 for a in alerts if a.get("riskcode", a.get("riskCode")) in ("3", 3))
        fail = sum(1 for a in alerts if a.get("riskcode", a.get("riskCode")) in ("4", 4))
        return len(alerts), high, fail
    return 0, 0, 0


def count_npm_audit() -> dict:
    for rel in ("npm/npm-audit-report.json", "npm-audit-report.json"):
        data = load_json(REPORTS / rel)
        if data is None:
            continue
        v = data.get("metadata", {}).get("vulnerabilities") or {}
        return {
            "total": v.get("total", 0),
            "critical": v.get("critical", 0),
            "high": v.get("high", 0),
            "moderate": v.get("moderate", 0),
            "low": v.get("low", 0),
        }
    return {"total": 0, "critical": 0, "high": 0, "moderate": 0, "low": 0}


def main() -> int:
    blocks: list[str] = []
    warns: list[str] = []
    tips: list[str] = []

    gitleaks_n, _ = count_gitleaks()
    if gitleaks_n > 0:
        blocks.append(f"Gitleaks: {gitleaks_n} секрет(ов) в git")
    gitleaks_cell = f"{gitleaks_n} секретов" if gitleaks_n else "0 секретов"
    gitleaks_block = "BLOCK" if gitleaks_n else "—"

    be_total, be_err = count_semgrep("semgrep-backend.json")
    fe_total, fe_err = count_semgrep("semgrep-frontend.json")
    semgrep_total = be_total + fe_total
    semgrep_errors = be_err + fe_err
    if semgrep_errors > 0:
        blocks.append(f"SAST (Semgrep): {semgrep_errors} находок уровня ERROR")
    sast_cell = f"{semgrep_total} находок ({semgrep_errors} ERROR)"
    sast_block = "BLOCK" if semgrep_errors else "по политике"

    zap_total, zap_high, zap_fail = count_zap()
    if zap_fail > 0:
        warns.append(f"DAST (ZAP): {zap_fail} FAIL-предупреждений")
    elif zap_total > 0:
        warns.append(f"DAST (ZAP): {zap_total} предупреждений (baseline)")
    if zap_fail:
        zap_cell = f"FAIL ({zap_fail})"
    elif zap_total:
        zap_cell = f"WARN ({zap_total})"
    else:
        zap_cell = "0 / отчёт не найден"
    zap_block = "по политике"

    img_be = count_trivy_json(find_file("trivy-images", "trivy-image-backend.json"))
    img_fe = count_trivy_json(find_file("trivy-images", "trivy-image-frontend.json"))
    img_crit = img_be.get("CRITICAL", 0) + img_fe.get("CRITICAL", 0)
    img_high = img_be.get("HIGH", 0) + img_fe.get("HIGH", 0)
    if STRICT and img_crit > 0:
        blocks.append(f"Trivy Image: {img_crit} CRITICAL")
    elif img_crit or img_high:
        warns.append(f"Trivy Image: CRITICAL={img_crit}, HIGH={img_high}")
    trivy_cell = f"Critical {img_crit} / High {img_high}"
    trivy_block = "BLOCK" if (STRICT and img_crit) else "по политике"

    npm = count_npm_audit()
    if STRICT and npm["critical"] > 0:
        blocks.append(f"npm audit: {npm['critical']} critical")
    elif npm["total"]:
        warns.append(f"npm audit: {npm['total']} vulnerabilities")
    npm_cell = f"{npm['total']} vulnerabilities ({npm['critical']} critical)"
    npm_block = "BLOCK" if (STRICT and npm["critical"]) else "информирование"

    if img_crit or img_high:
        tips.append("Обновить базовые Docker-образы (eclipse-temurin, node) и пересобрать CI.")
    if npm["total"]:
        tips.append("Frontend: npm audit fix / обновление react-scripts и lock-файла.")
    if zap_total:
        tips.append("DAST: добавить заголовки безопасности (CSP, X-Frame-Options) на nginx/VPS.")
    if not tips:
        tips.append("Критических блокировок нет — поддерживайте регулярные прогоны Security Checks.")

    verdict = "BLOCK" if blocks else "PASS"
    status_emoji = "❌" if blocks else "✅"

    md = f"""## Security Gateway — сводка {status_emoji}

**Итог: {verdict}**

| Проверка | Результат | Блокировка |
|----------|-----------|------------|
| Gitleaks | {gitleaks_cell} | {gitleaks_block} |
| SAST (Semgrep) | {sast_cell} | {sast_block} |
| DAST (ZAP) | {zap_cell} | {zap_block} |
| Trivy Image | {trivy_cell} | {trivy_block} |
| npm audit | {npm_cell} | {npm_block} |
"""
    if blocks:
        md += "\n**Блокировка merge:**\n"
        for b in blocks:
            md += f"- {b}\n"
    if warns:
        md += "\n**Предупреждения:**\n"
        for w in warns:
            md += f"- {w}\n"
    md += "\n**Рекомендации:**\n"
    for t in tips:
        md += f"- {t}\n"
    md += f"\n_commit: `{os.environ.get('GATEWAY_SHA', 'n/a')}` · режим STRICT={'on' if STRICT else 'off'}_\n"

    OUT_MD.write_text(md, encoding="utf-8")
    OUT_JSON.write_text(
        json.dumps(
            {
                "verdict": verdict,
                "blocks": blocks,
                "warnings": warns,
                "tips": tips,
                "gitleaks": gitleaks_n,
                "semgrep_total": semgrep_total,
                "semgrep_errors": semgrep_errors,
                "zap_total": zap_total,
                "trivy_image_critical": img_crit,
                "trivy_image_high": img_high,
                "npm": npm,
            },
            ensure_ascii=False,
            indent=2,
        ),
        encoding="utf-8",
    )

    print(md)
    print(f"\nGateway verdict: {verdict}")
    return 1 if blocks else 0


if __name__ == "__main__":
    sys.exit(main())
