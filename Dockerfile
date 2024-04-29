FROM maven:3.6.3-openjdk-17 AS build
COPY . .

ARG DATABASE_URL
ARG DATABASE_USERNAME
ARG DATABASE_PASSWORD

ARG MINIO_URL
ARG MINIO_BUCKET
ARG MINIO_USERNAME
ARG MINIO_PASSWORD
ARG MINIO_PUBLIC_HOST

ENV DATABASE_URL=$DATABASE_URL
ENV PORT=$PORT
ENV ADDRESS=$HOST
ENV DATABASE_USERNAME=$DATABASE_USERNAME
ENV DATABASE_PASSWORD=$DATABASE_PASSWORD

ENV MINIO_URL=$MINIO_URL
ENV MINIO_BUCKET=$MINIO_BUCKET
ENV MINIO_USERNAME=$MINIO_USERNAME
ENV MINIO_PASSWORD=$MINIO_PASSWORD
ENV MINIO_PUBLIC_HOST=$MINIO_PUBLIC_HOST

RUN mvn clean package

FROM openjdk:17
EXPOSE 8082
COPY --from=build /target/products-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]