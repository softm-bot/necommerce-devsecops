# Evidence — доказательства для отчёта

Скриншоты и файлы для сдачи эксперту. **Имена файлов — строго по списку** (удобно вставлять в Word).

## Прогресс

| Папка | Этап | Скринов нужно | Статус |
|-------|------|---------------|--------|
| [01-cicd](01-cicd/) | CI/CD, GHCR | 6 | ⏳ |
| [02-sast](02-sast/) | SAST Semgrep | 3 | ⏳ |
| [03-dast](03-dast/) | DAST ZAP | 3 | ⏳ |
| [04-security-checks](04-security-checks/) | Gitleaks, Trivy | 4 | ⏳ (после шага 5) |
| [05-security-gateway](05-security-gateway/) | Gateway, PR | 3 | ⏳ (после шага 8) |

**Подробная инструкция:** [../docs/EVIDENCE-SKRINY.md](../docs/EVIDENCE-SKRINY.md)

## Быстрый старт (догнать пропущенное)

```bash
cd /home/andrey/project/sib-ecommerce-diploma
./evidence/open-for-screenshots.sh   # подсказки + проверка стенда
```

## Правила

1. Формат: **PNG** (или JPG)
2. На скрине виден **URL** или **заголовок страницы**
3. Не обрезать статус **Success** / **Failure**
4. JSON из Actions → **Download artifact** → положить рядом со скрином

## Репозиторий

https://github.com/softm-bot/necommerce-devsecops
