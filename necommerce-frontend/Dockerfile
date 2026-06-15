FROM node:16 AS build
WORKDIR /app
COPY package*.json ./
# npm ci требует идеальной синхронизации lock-файла; npm install надёжнее для CI
RUN npm install
COPY . .
# .env в gitignore — задаём URL для production-сборки (nginx проксирует /api и /media)
ENV REACT_APP_API_URL=/api
ENV REACT_APP_MEDIA_URL=/media
RUN npm run build

FROM nginx
WORKDIR /app
COPY --from=build /app/build .
COPY nginx.conf.template /etc/nginx/templates/default.conf.template
