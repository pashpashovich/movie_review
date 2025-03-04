FROM openjdk:21-jdk-slim AS build
WORKDIR /app

COPY image_service/target/image_service-v.jar app2.jar

ENTRYPOINT ["java", "-jar", "/app/app2.jar"]