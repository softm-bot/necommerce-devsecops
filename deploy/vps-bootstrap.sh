#!/usr/bin/env bash
# Первичная настройка VPS (Ubuntu). Запуск: bash vps-bootstrap.sh
set -euo pipefail

apt-get update -qq
apt-get install -y -qq curl

if ! command -v docker &>/dev/null; then
  curl -fsSL https://get.docker.com | sh
  systemctl enable docker
  systemctl start docker
fi

apt-get install -y -qq docker-compose-plugin
mkdir -p /opt/necommerce

if command -v ufw &>/dev/null; then
  ufw allow 22/tcp
  ufw allow 8888/tcp
  ufw allow 9999/tcp
  ufw --force enable || true
fi

docker --version
docker compose version
echo "Готово. Скопируйте deploy/docker-compose.prod.yml в /opt/necommerce/"
