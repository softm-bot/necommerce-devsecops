# Диплом — по шагам (с объяснениями)

Каждый шаг = одна понятная задача. Не перескакиваем.

---

## Общая карта (куда идём)

```
Шаг 0  Локальный стенд          ✅
Шаг 1  GitHub-репозиторий       ✅
Шаг 2  Минимальный CI (сборка)  ✅
Шаг 3  Push образов в GHCR      ← СЛЕДУЮЩИЙ
Шаг 4  SAST (Semgrep)
Шаг 5  Security Checks
Шаг 6  DAST (ZAP)
Шаг 7  Ваш VPS + автодеплой
Шаг 8  Security Gateway
Шаг 9  Отчёт для сдачи
```

**VPS** — на шаге 7. Сначала GitHub и пайплайн, потом сервер. Так проще: не нужно чинить сразу и CI, и SSH.

---

## Шаг 0 — локальный стенд ✅

### Зачем
Перед автоматизацией убеждаемся: **приложение вообще запускается**. Это наш «эталон» — то, что потом будет на VPS.

### Что такое Docker Compose
**Docker** — упаковывает программу в контейнер.  
**Compose** — файл-инструкция «запусти backend и frontend вместе».

Файл: `docker-compose.yml` — две строки «скачай образ backend, образ frontend, свяжи их».

### Что сделали
```bash
cd /home/andrey/project/sib-ecommerce-diploma
sudo docker compose up -d
```

| Флаг | Значение |
|------|----------|
| `up` | Запустить контейнеры |
| `-d` | *detached* — в фоне, терминал свободен |

### Результат (14.06.2026)
| Проверка | Адрес | Ответ |
|----------|-------|-------|
| Сайт | http://127.0.0.1:8888 | HTTP 200 |
| API | http://127.0.0.1:9999/api/products | JSON с товарами |

**Скриншот для evidence:** откройте сайт в браузере → сохраните в `evidence/01-cicd/00-local-stand.png`

### Полезные команды
```bash
sudo docker compose ps      # статус контейнеров
sudo docker compose logs -f # логи (Ctrl+C — выход)
sudo docker compose down    # остановить
```

---

## Шаг 1 — GitHub-репозиторий ⏳ СЛЕДУЮЩИЙ

### Зачем
- Эксперт Нияз просил **ссылку на репозиторий**
- GitHub Actions (CI/CD) работает **только** с кодом на GitHub
- Туда же позже добавим VPS-секреты для деплоя

### Что такое репозиторий
Папка с кодом на GitHub + история изменений (git). Каждый `push` = отправить новую версию на сервер GitHub.

### Что нужно сделать ВАМ

#### 1.1 Создать репозиторий на сайте
1. https://github.com/new
2. **Repository name:** `necommerce-devsecops` (или своё имя)
3. **Public** — публичный (Actions бесплатнее)
4. **Без** README, .gitignore, license — они уже есть локально
5. **Create repository**

#### 1.2 Токен доступа (Personal Access Token)
GitHub не принимает обычный пароль при push из терминала.

1. https://github.com/settings/tokens
2. **Generate new token (classic)**
3. Права: `repo`, `write:packages`
4. Скопируйте токен — показывается один раз (в файлы проекта **не** сохранять)

#### 1.3 Первый push из терминала
```bash
cd /home/andrey/project/sib-ecommerce-diploma

git init
git branch -M main
git add .
git status   # fcm.json НЕ должен быть в списке!

git commit -m "Шаг 1: старт диплома DevSecOps, Necommerce + docker-compose"

git remote add origin https://github.com/ВАШ_ЛОГИН/necommerce-devsecops.git
git push -u origin main
```

При запросе пароля вставьте **токен**, не пароль от аккаунта.

#### 1.4 Написать эксперту
Отправить Ниязу Кашапову ссылку: https://github.com/softm-bot/necommerce-devsecops

---

## Шаг 2 — минимальный CI (сборка Docker) ⏳

### Зачем
**CI** (*Continuous Integration* — непрерывная интеграция) = при каждом `push` GitHub **автоматически** проверяет, что код **собирается**.

Сейчас проверяем только одно: **Docker-образы backend и frontend собираются без ошибок**.  
Публикацию образов (GHCR) добавим на **шаге 3**.

### Что такое GitHub Actions
Встроенный «робот» на GitHub. Читает файл `.github/workflows/ci.yml` и выполняет шаги на виртуальной машине в облаке.

| Термин | Значение |
|--------|----------|
| **workflow** | Сценарий (файл `.yml`) |
| **job** | Задача: backend или frontend |
| **runner** | Виртуальная машина Ubuntu от GitHub |
| **step** | Один шаг: checkout, docker build… |

### Что создано
Файл: `.github/workflows/ci.yml` — два job'а параллельно:
1. **build-backend** — `docker build` в `necommerce-backend/`
2. **build-frontend** — `docker build` в `necommerce-frontend/`

`push: false` — образ **не** выгружается никуда, только проверка сборки.

### Что сделать ВАМ

```bash
cd /home/andrey/project/sib-ecommerce-diploma

git add .github/workflows/ci.yml docs/po-shagam.md DIPLOM.md
git commit -m "Шаг 2: минимальный CI — сборка Docker backend и frontend"
git push
```

### Как проверить результат

1. Откройте https://github.com/softm-bot/necommerce-devsecops/actions  
2. Должен появиться прогон **«CI — Docker build»**  
3. Оба job'а зелёные = сборка успешна  

**Скриншот для evidence:** `evidence/01-cicd/02-ci-green.png`

Backend может собираться **5–15 минут** (Gradle качает зависимости) — это нормально.

### Если job красный
- Откройте job → красный step → читайте лог  
- Скопируйте ошибку в чат — разберём

**Следующий шаг (3):** добавить `push: true` и секрет `GHCR_TOKEN` — публикация образов в GitHub Container Registry.

---

## Шаг 7 — ваш VPS (заранее, чтобы понимать)

**VPS** — виртуальный сервер в интернете. Ваш личный «компьютер 24/7».

| На вашем ПК | На VPS |
|-------------|--------|
| `127.0.0.1:8888` — только вы | `ВАШ_IP:8888` — доступен из интернета |
| Выключили ПК — сайт упал | Сервер работает постоянно |

На шаге 7:
1. Арендуете VPS (Timeweb, Selectel, Hetzner…)
2. Устанавливаете Docker (`deploy/vps-bootstrap.sh` — создадим позже)
3. Добавляете в GitHub **Secrets**: `VPS_HOST`, `VPS_USER`, `VPS_SSH_KEY`
4. Пайплайн сам выкладывает новую версию после успешных проверок

**Deploy (деплой)** = автоматическая выкладка на VPS после зелёного пайплайна.

---

## Что делать прямо сейчас

1. ✅ Шаг 0 — стенд  
2. ✅ Шаг 1 — GitHub  
3. ⏳ **Шаг 2** — выполните push (команды выше в разделе «Шаг 2»)  
4. Откройте Actions, дождитесь зелёного прогона, скриншот → `evidence/01-cicd/`
