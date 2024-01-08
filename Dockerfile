FROM openjdk:18
LABEL maintainer="fretesinfinity"
ADD target/application.jar .
ENTRYPOINT ["java", "-jar", "application.jar"]