#!/usr/bin/env bash
# Подсказки для сбора скриншотов evidence

set -euo pipefail
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT"

echo "=== Evidence — сбор скриншотов ==="
echo ""

echo "--- Локальный стенд (A1–A3) ---"
if sudo docker compose ps 2>/dev/null | grep -q Up; then
  echo "✅ Docker compose работает"
  sudo docker compose ps
else
  echo "⏳ Запустите: sudo docker compose up -d"
fi
echo ""
echo "  Скрин браузера → evidence/01-cicd/01-local-stand.png"
echo "  URL: http://127.0.0.1:8888"
echo "  Скрин терминала  → evidence/01-cicd/02-docker-ps.png"
echo ""

echo "--- GitHub (B, C) — откройте в браузере ---"
urls=(
  "https://github.com/softm-bot/necommerce-devsecops"
  "https://github.com/softm-bot/necommerce-devsecops/actions/workflows/ci.yml"
  "https://github.com/softm-bot/necommerce-devsecops/pkgs/container/necommerce-backend"
  "https://github.com/softm-bot/necommerce-devsecops/blob/main/.github/workflows/ci.yml"
)
for u in "${urls[@]}"; do
  echo "  $u"
done
echo ""

echo "--- SAST (D) ---"
if git log -1 --oneline 2>/dev/null | grep -qi semgrep; then
  echo "✅ Коммит SAST есть локально"
else
  echo "⏳ Нужен push коммита SAST"
fi
echo "  https://github.com/softm-bot/necommerce-devsecops/actions/workflows/sast.yml"
echo ""

echo "Полная инструкция: docs/EVIDENCE-SKRINY.md"
echo "Чек-листы по папкам: evidence/*/README.md"

# Открыть браузер если есть xdg-open
if command -v xdg-open &>/dev/null; then
  read -r -p "Открыть GitHub Actions в браузере? [y/N] " ans
  if [[ "${ans,,}" == "y" ]]; then
    xdg-open "https://github.com/softm-bot/necommerce-devsecops/actions" 2>/dev/null || true
  fi
fi
