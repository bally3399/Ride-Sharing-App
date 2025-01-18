FROM maven:3.8.7 as build
COPY . .
RUN mvn -B clean package -DskipTests

FROM openjdk:21
COPY --from=build target/*.jar payment-service.jar

ENTRYPOINT ["java", "-jar", "-Dserver.port=8084", "payment-service.jar"]