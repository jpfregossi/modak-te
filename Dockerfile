FROM maven:3.8.1-openjdk-17-slim AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/notification-service-0.0.1-SNAPSHOT.jar notification-service.jar

ENTRYPOINT ["java", "-jar", "notification-service.jar"]