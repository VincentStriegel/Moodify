# Use a base image with Java and Maven
FROM maven:3.8.2-eclipse-temurin-17 AS build

WORKDIR /app

# Copy the Maven project and build the JAR
COPY pom.xml .
COPY src ./src
RUN mvn clean package

# Create a smaller image with just the JAR file
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy the JAR file from the build image
COPY target/*.jar app.jar

# Define the command to run the application
CMD ["java", "-jar", "app.jar"]