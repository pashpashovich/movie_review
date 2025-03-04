FROM openjdk:21-jdk-slim AS build
WORKDIR /app

COPY movie_service/target/movie_service-v.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]