FROM maven:3.6.3-openjdk-17 AS build
COPY . .

ARG DATABASE_URL
ARG DATABASE_USERNAME
ARG DATABASE_PASSWORD
ENV DATABASE_URL=$DATABASE_URL
ENV PORT=$PORT
ENV DATABASE_USERNAME=$DATABASE_USERNAME
ENV DATABASE_PASSWORD=$DATABASE_PASSWORD

RUN mvn clean package

FROM openjdk:17
EXPOSE 8082
COPY --from=build /target/products-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]