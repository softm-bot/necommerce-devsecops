# Пауза — как продолжить

**Обновлено:** 15.06.2026  
**Студент:** Западаев Андрей, SIBWEB-57  
**Эксперт:** Нияз Кашапов  

---

## Главные ссылки

| Что | Ссылка |
|-----|--------|
| **Отчёт Word** | [диплом.docx на Яндекс.Диске](https://disk.yandex.ru/i/XLLxLm5szE_ZNQ) |
| **GitHub** | https://github.com/softm-bot/necommerce-devsecops |
| **Сайт на VPS** | http://188.225.74.233:8888 |
| **API VPS** | http://188.225.74.233:9999/api/products |
| **Журнал** | `DIPLOM.md` |
| **Снимок паузы** | `docs/PAUSA-2026-06-15.md` |

---

## Что сделано (на паузе)

| Шаг | Результат |
|-----|-----------|
| 0 | Локальный стенд Docker |
| 1 | Репозиторий GitHub |
| 2–3 | CI + GHCR (зелёные Actions) |
| 4 | `sast.yml` в репо (Semgrep) |
| 7 | VPS `188.225.74.233` — Necommerce из GHCR |
| Word | Введение, стенд, **раздел 1** (1.5 и 1.8 — финал, не трогаем) |

---

## Следующий шаг после паузы

**Шаг 5** — Security Checks: Gitleaks + Trivy в GitHub Actions.

В чате напишите:
```
Диплом DevSecOps. Прочитай DIPLOM.md и docs/PAUSA-2026-06-15.md. Продолжаем с шага 5.
```

---

## Быстрые команды

```bash
cd /home/andrey/project/sib-ecommerce-diploma

# Локальный стенд
sudo docker compose up -d

# VPS
ssh root@188.225.74.233
docker ps

# Git
git status
git pull
```

---

## Напоминание

Скрины и описания — в [диплом.docx](https://disk.yandex.ru/i/XLLxLm5szE_ZNQ) (9 рисунков уже есть в разделе 1).
