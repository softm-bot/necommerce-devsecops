FROM node:16 AS build
WORKDIR /app
COPY package*.json ./
# npm ci требует идеальной синхронизации lock-файла; npm install надёжнее для CI
RUN npm install
COPY . .
RUN npm run build

FROM nginx
WORKDIR /app
COPY --from=build /app/build .
COPY nginx.conf.template /etc/nginx/templates/default.conf.template
