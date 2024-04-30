FROM openjdk:17-jdk-slim-buster
LABEL authors="kyeiiih"

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]