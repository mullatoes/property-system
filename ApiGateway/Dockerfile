# Use an OpenJDK 17 image as a base
FROM maven:3.8.4-openjdk-17 AS builder

# Set the working directory in the container
WORKDIR /ApiGateway

# Copy the Maven project files
COPY pom.xml .

# Download and cache dependencies
RUN mvn dependency:go-offline -B

# Copy the application source code
COPY src ./src

# Build the application
RUN mvn package -DskipTests

# Use a lightweight base image for the runtime environment
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /ApiGateway

# Copy the built .war file from the builder stage
COPY --from=builder /ApiGateway/target/*.jar /ApiGateway/NotificationService.jar

# Expose the port your application runs on
#EXPOSE 8189

# Command to run the application
CMD ["java", "-jar", "NotificationService.jar"]