FROM node:20 AS build
WORKDIR /app
COPY . ./
RUN npm install && npm run build --configuration=production

FROM nginx:stable
COPY --from=build app/dist/movie-searcher/browser /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
