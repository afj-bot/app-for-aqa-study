FROM openjdk:18-jdk-alpine

COPY ./target/*.jar ./app.jar

CMD ["java", "-Dserver.port=80","-Dproject.version=1.1.0-1", \
    "-jar", "./app.jar"]