FROM openjdk:21-jdk-slim AS build
WORKDIR /app

COPY . .

RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

RUN mvn clean install -DskipTests

FROM openjdk:21-jdk-slim
WORKDIR /app

COPY --from=build /app/image-service/target/image-service-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]