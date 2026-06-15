#!/usr/bin/env bash
# Обновление Necommerce на VPS (запускать на сервере в /opt/necommerce)
set -euo pipefail

cd /opt/necommerce

if [ -n "${GHCR_TOKEN:-}" ]; then
  echo "${GHCR_TOKEN}" | docker login ghcr.io -u "${REGISTRY_OWNER:-softm-bot}" --password-stdin
fi

docker compose -f docker-compose.prod.yml pull
docker compose -f docker-compose.prod.yml up -d
docker compose -f docker-compose.prod.yml ps

sleep 3
curl -sf -o /dev/null -w "frontend HTTP %{http_code}\n" http://127.0.0.1:8888/
curl -sf http://127.0.0.1:9999/api/products | head -c 80
echo ""
