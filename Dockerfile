FROM openjdk:12-jdk-alpine
ARG JAR_FILE
COPY ${JAR_FILE} app.jar*
