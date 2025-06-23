# Etapa 1: Construir Angular
FROM node:20 AS build-angular
WORKDIR /app
COPY /src/frontend/ ./
RUN npm install && npm run build

# Etapa 2: Construir Spring Boot con Angular estático
FROM maven:3.9-eclipse-temurin-17 AS build-spring
WORKDIR /app
COPY /src/backend/ ./
COPY --from=build-angular /app/dist/movie-searcher/ ./src/main/resources/static/
RUN mvn clean package -DskipTests

# Etapa 3: Imagen final para ejecución
FROM eclipse-temurin:17
WORKDIR /app
COPY --from=build-spring /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
