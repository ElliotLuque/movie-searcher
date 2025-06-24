FROM node:20 AS build
WORKDIR /app
ARG ANGULAR_BUILD_CONFIG=production
ENV ANGULAR_BUILD_CONFIG=${ANGULAR_BUILD_CONFIG}
COPY . ./
RUN npm install && npm run build -- --configuration=$ANGULAR_BUILD_CONFIG

FROM nginx:stable
COPY --from=build app/dist/movie-searcher/browser /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
