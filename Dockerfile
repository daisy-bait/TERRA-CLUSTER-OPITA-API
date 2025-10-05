FROM openjdk:17-jdk-slim AS builder

WORKDIR /app

COPY . .

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

RUN mkdir -p /app/data

COPY --from=builder /app/target/*jar ada.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "ada.jar"]