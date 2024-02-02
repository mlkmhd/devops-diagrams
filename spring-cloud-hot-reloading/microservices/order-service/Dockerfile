FROM openjdk:17

WORKDIR /app

ADD build/libs/*-SNAPSHOT.jar /app/app.jar

ADD src/main/resources/application-docker.properties /app/config/application.properties
ADD src/main/resources/bootstrap-docker.properties /app/config/bootstrap.properties

CMD ["java", "-jar", "-Dspring.config.location=/app/config/", "app.jar"]