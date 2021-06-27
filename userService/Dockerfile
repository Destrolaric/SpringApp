FROM openjdk:12-jdk-alpine
COPY build/libs/userService-?.?.?.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]