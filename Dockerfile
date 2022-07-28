FROM openjdk:18-jdk-alpine

ENV DATABASE_USERNAME=app
ENV DATABASE_PASSWORD=password123
ENV DATABASE_URL="jdbc:mysql://localhost:3306/app"
ENV APP_DOMAIN="localhost"
ENV FE_URL="http://localhost:3000"

COPY ./target/*.jar ./app.jar

CMD ["java", "-Dserver.port=80","-Dproject.version=1.1.0-1", \
    "-jar", "./app.jar"]