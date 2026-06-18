# 05-security-gateway — Security Gateway

## Чек-лист (шаг 8)

| Файл | Что снять | Рис. | Статус |
|------|-----------|------|--------|
| `01-gateway-green.png` | Actions → «Security Gateway» → Success | **Рис. 21** | ⏳ |
| `02-pr-comment.png` | Pull Request → Conversation → комментарий бота | **Рис. 22** | ⏳ |
| `03-gateway-policy.png` | `scripts/security-gateway.py` или job «Проверить политику» | опц. | ⏳ |

**Workflow:** `.github/workflows/security-gateway.yml`  
**Скрипт политики:** `scripts/security-gateway.py`

## Как получить Рис. 22

1. `git push` workflow на GitHub  
2. Ветка `demo/gateway-test` → Pull Request в `main`  
3. Дождаться Security Checks → автоматически стартует **Security Gateway**  
4. PR → вкладка **Conversation** → комментарий «Security Gateway — сводка»
