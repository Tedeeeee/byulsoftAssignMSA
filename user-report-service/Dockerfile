FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY build/libs/user-report-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]