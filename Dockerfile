# Build stage
FROM maven:3.6.3-openjdk-17 AS build
COPY src /home/app/src
COPY pom.xml /home/app/pom.xml

ARG DATABASE_URL
ARG DATABASE_USERNAME
ARG DATABASE_PASSWORD
ENV DATABASE_URL=$DATABASE_URL
ENV DATABASE_USERNAME=$DATABASE_USERNAME
ENV DATABASE_PASSWORD=$DATABASE_PASSWORD

RUN mvn -f /home/app/pom.xml clean package

# Package stage
FROM openjdk:17-alpine
COPY --from=build /home/app/target/auth-0.0.1-SNAPSHOT.jar /usr/local/lib/demo.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/usr/local/lib/demo.jar"]
