# Use the official Maven image to build the application
FROM maven:3.8.1-openjdk-17 AS build

# Copy the source code into the container
COPY . /app

# Set the working directory
WORKDIR /app

# Build each module separately
RUN mvn clean install -DskipTests

# Use the official OpenJDK base image to run the application
FROM openjdk:17

# Set the working directory in the container
WORKDIR /app

# Copy the JAR files from the server module to the container
COPY --from=build /app/server/target/*.jar server/

# Expose necessary ports
EXPOSE 8080

# Set the entry point to run the server JAR file
CMD ["java", "-jar", "/app/server/server-0.0.1-SNAPSHOT.jar"]
