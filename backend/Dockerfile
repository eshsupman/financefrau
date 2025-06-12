
FROM gradle:8.2.1-jdk17 as builder

WORKDIR /app

COPY .. .

RUN gradle clean build --no-daemon


FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

ENTRYPOINT ["java","-jar","build/libs/finfrau-0.0.1-SNAPSHOT.jar"]
