# Necommerce DevSecOps

Дипломная работа по треку DevSecOps (Нетология).  
**Студент:** Западаев Андрей · **Группа:** SIBWEB-57

Монорепозиторий интернет-магазина [Necommerce](https://github.com/netology-code/necommerce-backend): backend (Kotlin/Spring), frontend (React), CI/CD и проверки безопасности в GitHub Actions.

## Репозиторий

https://github.com/softm-bot/necommerce-devsecops

## Локальный запуск

```bash
docker compose up -d
```

- Сайт: http://127.0.0.1:8888  
- API: http://127.0.0.1:9999/api/products  

## Пайплайн

| Workflow | Назначение |
|----------|------------|
| `ci.yml` | Сборка Docker, публикация в GHCR |
| `sast.yml` | Semgrep (backend, frontend) |
| `dast.yml` | OWASP ZAP baseline |
| `security-checks.yml` | Gitleaks, Trivy, npm audit |
| `security-gateway.yml` | Политика, сводка, комментарии в PR |

## Стенд

- **GHCR:** `ghcr.io/softm-bot/necommerce-backend`, `necommerce-frontend`
- **VPS:** http://188.225.74.233:8888

## Документация

- [DIPLOM.md](DIPLOM.md) — описание проекта и архитектуры
- [docs/FORMAT-SDACHI.md](docs/FORMAT-SDACHI.md) — структура отчёта
