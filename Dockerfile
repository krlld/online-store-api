FROM openjdk:17-slim

COPY ./target/online-store-api.jar /app/
WORKDIR /app

CMD ["java", "-jar", "online-store-api.jar"]