# Инструкция: скриншоты evidence (догнать пропущенное)

Откройте этот файл и идите **сверху вниз**. На каждый пункт — один скрин → сохранить **точно** в указанную папку.

---

## Сейчас (шаги 0–4 уже сделаны в коде)

### Блок A — локальный стенд (~5 мин)

| № | Действие | Файл |
|---|----------|------|
| A1 | `sudo docker compose up -d` | — |
| A2 | Браузер http://127.0.0.1:8888 | `evidence/01-cicd/01-local-stand.png` |
| A3 | Терминал `sudo docker compose ps` | `evidence/01-cicd/02-docker-ps.png` |

### Блок B — GitHub репозиторий (~3 мин)

| № | URL | Файл |
|---|-----|------|
| B1 | https://github.com/softm-bot/necommerce-devsecops | `evidence/01-cicd/03-github-repo.png` |
| B2 | https://github.com/softm-bot/necommerce-devsecops/blob/main/.github/workflows/ci.yml | `evidence/01-cicd/06-workflow-ci-yml.png` |

### Блок C — CI + GHCR (~5 мин)

| № | URL | Файл |
|---|-----|------|
| C1 | https://github.com/softm-bot/necommerce-devsecops/actions/workflows/ci.yml → последний **зелёный** | `evidence/01-cicd/04-actions-ci-green.png` |
| C2 | https://github.com/softm-bot/necommerce-devsecops/pkgs/container/necommerce-backend | `evidence/01-cicd/05-ghcr-packages.png` |

**На скрине C1 должно быть видно:**
- название workflow «CI — Docker build & GHCR»
- зелёная галочка Success
- оба job: Backend и Frontend

### Блок D — SAST Semgrep (~5 мин)

Сначала убедитесь, что SAST запушен:
```bash
cd ~/project/sib-ecommerce-diploma
git log -1 --oneline   # должен быть коммит про Semgrep
git push               # если опережает origin
```

| № | URL | Файл |
|---|-----|------|
| D1 | https://github.com/softm-bot/necommerce-devsecops/actions/workflows/sast.yml → зелёный прогон | `evidence/02-sast/01-actions-sast-green.png` |
| D2 | Открыть job «SAST — backend» → шаг «Краткий отчёт в лог» | `evidence/02-sast/02-semgrep-backend-log.png` |
| D3 | Внизу прогона → **Artifacts** | `evidence/02-sast/03-semgrep-artifacts.png` |
| D4 | Download `semgrep-backend` и `semgrep-frontend` | `evidence/02-sast/*.json` |

---

## Позже (после следующих шагов)

| Шаг | Папка | README |
|-----|-------|--------|
| 5 Security Checks | `evidence/04-security-checks/` | там чек-лист |
| 6 DAST | `evidence/03-dast/` | там чек-лист |
| 7 VPS | `evidence/01-cicd/07-vps-deploy.png` | — |
| 8 Gateway | `evidence/05-security-gateway/` | там чек-лист |

---

## Как делать скрин в Linux

| Способ | Как |
|--------|-----|
| **GNOME** | `Print Screen` — весь экран; `Shift+Print` — область |
| **Flameshot** | `flameshot gui` |
| **Терминал → файл** | `sudo docker compose ps 2>&1 \| script evidence/01-cicd/02-docker-ps.txt` (или скрин) |

Сохраняйте через **«Сохранить как»** с нужным именем в нужную папку.

**Копия для отчёта:** вставляйте те же скрины в [диплом.docx](https://disk.yandex.ru/i/XLLxLm5szE_ZNQ) + краткое описание (2–3 предложения).

---

## После заполнения

В `DIPLOM.md` можно отметить: «evidence 01-cicd и 02-sast заполнены».

Всего **сейчас** минимум **11 файлов** (9 скринов + 2 JSON).
