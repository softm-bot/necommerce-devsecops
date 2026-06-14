# Диплом DevSecOps — рабочий журнал

**Студент:** Западаев Андрей  
**Группа:** SIBWEB-57  
**Эксперт:** Нияз Кашапов  
**Задание:** [DevSecOps Track (Netology)](https://github.com/netology-code/sib-Diplom-Track-DevSecOps)  
**Объект:** [Necommerce](https://github.com/netology-code/necommerce-backend) — backend (Kotlin/Spring), frontend (React), Docker  

**Дата старта с нуля:** 13.06.2026

---

## Принцип

Всё, что делалось до этой даты (курсовая, черновики пайплайна, пробные прогоны Semgrep/Trivy/ZAP) — **только теоретическая подготовка**.  
**В этот документ** фиксируем только реальную работу над дипломом: что сделали, когда, какой результат, где артефакт.

---

## Критерии сдачи (5 этапов)

| № | Этап | Что нужно | Статус |
|---|------|-----------|--------|
| 1 | CI/CD | Сборка Docker-образов, push в GHCR, деплой на VPS | ⏳ |
| 2 | SAST | Semgrep в пайплайне, артефакты JSON | ⏳ |
| 3 | DAST | OWASP ZAP на работающем стенде, отчёт | ⏳ |
| 4 | Security Checks | Gitleaks, Trivy, npm audit | ⏳ |
| 5 | Security Gateway | Блокировка при Critical, комментарий в PR | ⏳ |

**Дополнительно:** свой репозиторий на GitHub, скриншоты Actions, итоговый отчёт DOCX/PDF.

---

## План работ (по шагам)

| Шаг | Задача | Статус |
|-----|--------|--------|
| 0 | Necommerce работает локально (`docker compose up`) | ✅ 14.06.2026 |
| 1 | Репозиторий на GitHub, первый push | ✅ 14.06.2026 |
| 2 | Минимальный CI — только сборка Docker | ⏳ файл готов, нужен push |
| 3 | Push образов в GHCR | ⏳ **следующий после CI** |
| 4 | SAST (Semgrep) | ⏳ |
| 5 | Security Checks (Gitleaks, Trivy) | ⏳ |
| 6 | DAST (ZAP) | ⏳ |
| 7 | VPS + автодеплой (свой сервер студента) | ⏳ |
| 8 | Security Gateway + PR | ⏳ |
| 9 | Итоговый отчёт | ⏳ |

---

## Журнал работ

> Каждая строка = одно действие. Новые записи — **сверху** (после этого заголовка).

### 2026-06-14 — Шаг 2: минимальный CI (подготовка) ⏳

**Что сделано:** Создан `.github/workflows/ci.yml` — два job'а: сборка Docker backend и frontend (`push: false`).

**Зачем:** CI проверяет, что код собирается при каждом push. Публикация образов — шаг 3.

**Следующий шаг:** `git add`, `commit`, `push` → проверить Actions → скриншот в `evidence/01-cicd/`.

### 2026-06-14 — Шаг 1: GitHub push ✅

**Что сделано:** `git push -u origin main` — 156 объектов, ветка `main` на GitHub.

**Результат:** https://github.com/softm-bot/necommerce-devsecops — код на GitHub, 3 коммита.

**Следующий шаг:** написать эксперту Ниязу ссылку на репозиторий; Шаг 2 — минимальный CI (сборка Docker).

### 2026-06-14 — Шаг 1: remote GitHub ✅

**Что сделано:**
- Remote: https://github.com/softm-bot/necommerce-devsecops
- Локально: 2 коммита на `main` (`5bef6ac`, `cea7a16`)

**Результат:** Remote настроен, push выполнен пользователем.

**Следующий шаг:** —

### 2026-06-14 — Шаг 1: подготовка Git (локально) ✅

**Что сделано:**
- Удалены вложенные `.git` в `necommerce-backend/` и `necommerce-frontend/` (единый монорепозиторий)
- Убран случайно сохранённый PAT из `docs/po-shagam.md`
- `git init`, ветка `main`, первый коммит `5bef6ac` (113 файлов)
- Проверено: `fcm.json`, `.env`, ключи — **не** в коммите

**Результат:** Код готов к push.

**Следующий шаг:** создать репозиторий на GitHub, `git push`.

### 2026-06-14 — Шаг 0: локальный стенд ✅

**Что сделано:** `sudo docker compose up -d` — подняты контейнеры backend и frontend.

**Проверка:**
- http://127.0.0.1:8888 → HTTP 200
- http://127.0.0.1:9999/api/products → JSON с товарами

**Артефакт:** скриншот сайта → `evidence/01-cicd/00-local-stand.png` (сделать вручную)

**Следующий шаг:** Шаг 1 — репозиторий на GitHub, первый push. Инструкция: `docs/po-shagam.md`

### 2026-06-14 — Полный сброс проекта

**Что сделано:** Удалены все черновики, архив курсовой, старая документация (`diploma/`, `archive/`, `deploy/`, `reports/` и др.). Оставлен только продукт Necommerce + `docker-compose.yml`. Создан единственный рабочий документ **`DIPLOM.md`**.

**Результат:** Чистая база для диплoma. Вся прошлая работа = теоретическая подготовка.

**Следующий шаг:** Шаг 0 — `sudo docker compose up -d`, проверить http://127.0.0.1:8888

### 2026-06-13 — Старт с нуля

**Что сделано:** Решение начать диплом заново. Вся предыдущая работа — теоретическая подготовка. Создан единственный рабочий документ `DIPLOM.md`.

**Результат:** Чистая база: только код Necommerce + `docker-compose.yml`.

**Следующий шаг:** Шаг 0 — поднять Necommerce локально.

---

## Переписка с экспертом

| Дата | Суть |
|------|------|
| 11.06.2026 | Нияз: направление верное, можно доделывать. Нужна **ссылка на репозиторий** или текст пайплайнов. |

---

## Артефакты

Скриншоты и отчёты складываем в `evidence/`:

```
evidence/
├── 01-cicd/
├── 02-sast/
├── 03-dast/
├── 04-security-checks/
└── 05-security-gateway/
```

---

## Полезные команды

```bash
cd /home/andrey/project/sib-ecommerce-diploma

# Локальный стенд
sudo docker compose up -d
sudo docker compose ps

# Сайт: http://127.0.0.1:8888
# API:  http://127.0.0.1:9999/api/products
```

---

## Ссылки

- [Задание трека DevSecOps](https://github.com/netology-code/sib-Diplom-Track-DevSecOps)
- [Necommerce backend](https://github.com/netology-code/necommerce-backend)
- [Necommerce frontend](https://github.com/netology-code/necommerce-frontend)
- [GitHub-репозиторий диплoma](https://github.com/softm-bot/necommerce-devsecops)
