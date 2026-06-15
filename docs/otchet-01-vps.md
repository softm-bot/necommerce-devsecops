# Раздел 1.8 + VPS — текст для диплом.docx

*(https://disk.yandex.ru/i/XLLxLm5szE_ZNQ)*

---

### 1.8. Развёртывание на VPS

Для выполнения критерия «использование облачных сервисов для раскатки» приложение развёрнуто на выделенном **VPS** (виртуальный сервер) с публичным IP-адресом.

| Параметр | Значение |
|----------|----------|
| IP-адрес | 188.225.74.233 |
| ОС | Ubuntu |
| Доступ | SSH (`root@188.225.74.233`) |
| Каталог приложения | `/opt/necommerce` |
| Образы | `ghcr.io/softm-bot/necommerce-backend:latest`, `ghcr.io/softm-bot/necommerce-frontend:latest` |

**Порядок развёртывания:**

1. Подключение по SSH к серверу.
2. Установка Docker и Docker Compose.
3. Аутентификация в GHCR (`docker login ghcr.io`) с использованием токена GitHub.
4. Создание `docker-compose.yml` с образами из GHCR.
5. Запуск: `docker compose up -d`.

**Проверка доступности (15.06.2026):**

| Сервис | URL | Результат |
|--------|-----|-----------|
| Frontend | http://188.225.74.233:8888 | HTTP 200, каталог товаров |
| Backend API | http://188.225.74.233:9999/api/products | HTTP 200, JSON |
| API через nginx | http://188.225.74.233:8888/api/products | HTTP 200 |

*→ Рис. 8. Сайт Necommerce на VPS в браузере*  
*→ Рис. 9. Терминал: `docker ps` на VPS — контейнеры Up*

**Исправление при развёртывании:** при первой выкладке frontend отображал «белый экран» — в production-сборке React отсутствовал `REACT_APP_API_URL` (файл `.env` не попадает в git). В `necommerce-frontend/Dockerfile` добавлено `ENV REACT_APP_API_URL=/api` и `ENV REACT_APP_MEDIA_URL=/media`. После пересборки образа в CI и `docker compose pull` на VPS приложение работает корректно.

**Связь CI/CD и VPS:** образы собираются в GitHub Actions и публикуются в GHCR; на VPS выполняется `docker compose pull` для получения актуальной версии. Автоматический деплой из пайплайна (job deploy по SSH) запланирован как доработка конвейера.

---

**📎 Яндекс.Диск:** вставьте подраздел 1.8 + рис. 8–9.
