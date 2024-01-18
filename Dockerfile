FROM openjdk:18
LABEL maintainer="fretesinfinity"

COPY target/application.jar /app/application.jar
WORKDIR /app

ENTRYPOINT ["java", "-jar", "application.jar"]
