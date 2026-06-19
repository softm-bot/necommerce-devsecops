# Necommerce DevSecOps — описание проекта

**Студент:** Западаев Андрей · **Группа:** SIBWEB-57 · **Эксперт:** Нияз Кашапов

## Цель

Построить CI/CD-пайплайн с интеграцией практик DevSecOps для open-source проекта Necommerce на платформе GitHub Actions.

## Объект

- Backend: Kotlin, Spring Boot, Gradle  
- Frontend: React, npm  
- Контейнеризация: Docker, Docker Compose  
- Реестр образов: GitHub Container Registry (GHCR)  
- Стенд: VPS `188.225.74.233`

## Архитектура пайплайна

```
push / pull_request
    ├── CI — сборка и push образов в GHCR
    ├── SAST — Semgrep
    ├── DAST — OWASP ZAP (baseline)
    └── Security Checks — Gitleaks, Trivy, npm audit
              │
              ▼ (workflow_run)
        Security Gateway — политика, сводка, комментарий в PR
```

Сканеры SAST, DAST и Security Checks работают в информирующем режиме и сохраняют отчёты в Artifacts. **Security Gateway** агрегирует результаты и применяет политику блокировки (`scripts/security-gateway.py`).

## Политика Security Gateway

| Уровень | Условие |
|---------|---------|
| BLOCK (базовый) | Gitleaks ≥ 1; Semgrep ERROR ≥ 1 |
| BLOCK (STRICT=1) | дополнительно Trivy Image CRITICAL ≥ 1; npm critical ≥ 1 |
| WARN | ZAP, Trivy HIGH, npm high/moderate |

## Этапы Netology

| № | Этап | Реализация |
|---|------|------------|
| 1 | CI/CD | `ci.yml`, GHCR, VPS |
| 2 | SAST | `sast.yml`, Semgrep |
| 3 | DAST | `dast.yml`, OWASP ZAP |
| 4 | Security Checks | `security-checks.yml` |
| 5 | Security Gateway | `security-gateway.yml` |

## Структура репозитория

```
.github/workflows/     — конфигурация CI/CD и DevSecOps
necommerce-backend/    — backend
necommerce-frontend/   — frontend
scripts/               — Security Gateway (политика)
deploy/                — скрипты развёртывания на VPS
docker-compose.yml     — локальный стенд
```

## Ссылки

- [Репозиторий](https://github.com/softm-bot/necommerce-devsecops)
- [Задание трека DevSecOps](https://github.com/netology-code/sib-Diplom-Track-DevSecOps)
