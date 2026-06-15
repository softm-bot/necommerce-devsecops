# 01-cicd — скриншоты CI/CD

## Чек-лист (шаги 0–3 + VPS позже)

| Файл | Что снять | Статус |
|------|-----------|--------|
| `01-local-stand.png` | Сайт http://127.0.0.1:8888 в браузере (каталог товаров) | ⏳ |
| `02-docker-ps.png` | Терминал: `sudo docker compose ps` — оба контейнера Up | ⏳ |
| `03-github-repo.png` | Страница репозитория на GitHub (файлы, ветка main) | ⏳ |
| `04-actions-ci-green.png` | Actions → «CI — Docker build & GHCR» → **Success**, оба job | ⏳ |
| `05-ghcr-packages.png` | GitHub → Packages → necommerce-backend и frontend | ⏳ |
| `06-workflow-ci-yml.png` | Файл `.github/workflows/ci.yml` на GitHub (код виден) | ⏳ |
| `07-vps-deploy.png` | *(позже, шаг 7)* Сайт по IP VPS или deploy job Success | — |

## Как снять

### 01-local-stand.png
```bash
sudo docker compose up -d   # если не запущено
```
Браузер → http://127.0.0.1:8888 → скрин всей страницы магазина.

### 02-docker-ps.png
```bash
cd ~/project/sib-ecommerce-diploma
sudo docker compose ps
```
Скрин терминала с портами 8888 и 9999.

### 03-github-repo.png
https://github.com/softm-bot/necommerce-devsecops

### 04-actions-ci-green.png
https://github.com/softm-bot/necommerce-devsecops/actions/workflows/ci.yml

Откройте **последний зелёный** прогон → оба job: Backend + Frontend **Success**.

### 05-ghcr-packages.png

**Сначала:** вы должны быть **войти на GitHub** под `softm-bot`.

**Рабочие ссылки (не `/pkgs/container/...` — та даёт 404):**

1. https://github.com/softm-bot/necommerce-devsecops/packages  
2. https://github.com/softm-bot?tab=packages  

На странице должны быть **necommerce-backend** и **necommerce-frontend**.

**Если список пустой** — образы не попали в GHCR. См. раздел «Если Packages пусто» в `docs/po-shagam.md` или попросите помощь в чате: проверить секрет `GHCR_TOKEN` и сделать `git push`.

**Если пакеты есть, но Private:** откройте пакет → **Package settings** → **Change visibility** → **Public** (для скриншота в отчёт).

Скрин → `evidence/01-cicd/05-ghcr-packages.png`

### 06-workflow-ci-yml.png
https://github.com/softm-bot/necommerce-devsecops/blob/main/.github/workflows/ci.yml
