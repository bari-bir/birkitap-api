FROM maven:3.8.4-openjdk-17 AS builder

COPY src /src
COPY pom.xml /pom.xml

RUN mvn -f /pom.xml clean package

FROM openjdk:17
ENV TZ="Asia/Almaty"
COPY --from=builder /target/*.jar /app.jar
COPY share /etc/share
CMD ["--server.port=8080"]
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar","--spring.config.location=/etc/share/application.properties"]