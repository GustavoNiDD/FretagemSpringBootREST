# Use a base image with Java 17
FROM openjdk:17-jdk-alpine

# Set a volume point to /tmp because it is where a Spring Boot application creates working directories for Tomcat by default.
VOLUME /tmp

# Add the application's jar to the container
ADD target/application.jar app.jar

# Run the jar file 
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]