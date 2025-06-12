# Stage 1: сборка
FROM gradle:8.2.1-jdk17 as builder

WORKDIR /app

COPY .. .

RUN gradle clean build --no-daemon

# Stage 2: запуск
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]
