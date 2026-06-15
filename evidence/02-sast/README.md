# 02-sast — скриншоты SAST (Semgrep)

## Чек-лист (шаг 4)

| Файл | Что снять | Статус |
|------|-----------|--------|
| `01-actions-sast-green.png` | Actions → «SAST — Semgrep» → Success, 2 job | ⏳ |
| `02-semgrep-backend-log.png` | Job «SAST — backend» → шаг «Краткий отчёт» (строки находок) | ⏳ |
| `03-semgrep-artifacts.png` | Низ прогона → Artifacts: semgrep-backend, semgrep-frontend | ⏳ |
| `semgrep-backend.json` | Download artifact → положить сюда | ⏳ |
| `semgrep-frontend.json` | Download artifact → положить сюда | ⏳ |

## Ссылки

- Actions SAST: https://github.com/softm-bot/necommerce-devsecops/actions/workflows/sast.yml
- Workflow: https://github.com/softm-bot/necommerce-devsecops/blob/main/.github/workflows/sast.yml

## Если SAST ещё не запушен

```bash
git push   # коммит «Шаг 4: SAST — Semgrep»
```

Дождитесь зелёного прогона, затем снимайте.
