FROM openjdk:17

WORKDIR /app

ADD build/libs/*-SNAPSHOT.jar /app/app.jar
ADD src/main/resources/application-docker.properties /app/config/application.properties

RUN groupadd -g 1001 group1 && useradd -u 1001 -g group1 -m user1
RUN chown -R user1:group1 /app && chmod -R 777 /app
USER 1001:1001

CMD ["java", "-jar", "-Dspring.config.location=/app/config/", "app.jar"]
