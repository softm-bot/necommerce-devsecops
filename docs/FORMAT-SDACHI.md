# Формат сдачи диплома DevSecOps

**Эксперт:** Нияз Кашапов  
**Задание:** [DevSecOps Track](https://github.com/netology-code/sib-Diplom-Track-DevSecOps)

## Основные артефакты

1. **GitHub-репозиторий** — https://github.com/softm-bot/necommerce-devsecops  
2. **Отчёт DOCX/PDF** — описание процесса, скриншоты, анализ результатов  
3. **Папка `evidence/`** — локальная копия скриншотов и отчётов (опционально)

## Структура отчёта

1. Введение — цель, объект, платформа  
2. CI/CD — сборка, GHCR, VPS  
3. SAST — Semgrep  
4. DAST — OWASP ZAP  
5. Security Checks — Gitleaks, Trivy, npm audit  
6. Security Gateway — политика, блокировка, PR  
7. Заключение — соответствие критериям Netology, направления развития  

## Критерии Netology (5 этапов)

| Этап | Требование |
|------|------------|
| CI/CD | Сборка, доставка образов, деплой |
| SAST | Статический анализ, артефакты |
| DAST | Сканирование работающего сервиса |
| Security Checks | Gitleaks, Trivy, npm audit |
| Security Gateway | Блокировка по политике, комментарии в PR |

## Чек-лист перед сдачей

- [ ] Репозиторий публичный, эксперт имеет ссылку  
- [ ] Все 5 workflow активны в Actions  
- [ ] Отчёт содержит скриншоты и описание по каждому этапу  
- [ ] Уточнён формат загрузки у эксперта (LMS / email)
