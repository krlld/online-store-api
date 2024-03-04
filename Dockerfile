FROM maven:3.8.4-openjdk-17-slim AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17-slim

COPY ./target/online-store-api.jar /app/
WORKDIR /app

CMD ["java", "-jar", "online-store-api.jar"]