FROM maven:3-eclipse-temurin-22-alpine AS build

COPY src /app/src
COPY pom.xml /app

WORKDIR /app

RUN mvn package

FROM openjdk:22 AS final

COPY --from=build /app/target/sparkusBackend-0.0.1-SNAPSHOT.jar /target/app.jar

WORKDIR /target

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]