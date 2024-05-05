FROM maven:3.8.4-jdk-11 as build

COPY src /app/src
COPY pom.xml /app

WORKDIR /app

RUN mvn clean install -DskipTests

FROM eclipse-temurin:11-jre-jammy AS final

COPY --from=build /app/target/sparkusBackend-0.0.1-SNAPSHOT.jar /target/app.jar

WORKDIR /target

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]