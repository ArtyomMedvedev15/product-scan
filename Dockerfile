FROM maven:3.9.2-eclipse-temurin-17-alpine as builder
COPY ./src src/
COPY ./src/main/resources resources/
COPY ./pom.xml pom.xml
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
COPY --from=builder target/*.war product-scan-0.1.war
EXPOSE 8080
CMD ["java","-jar", "product-scan-0.1.war"]