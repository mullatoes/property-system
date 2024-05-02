# Use an OpenJDK 17 image as a base
FROM maven:3.8.4-openjdk-17 AS builder


# 1. ServiceRegistry
# Set the working directory in the container
WORKDIR /ServiceRegistry

# Copy the Maven project files
COPY /ServiceRegistry/pom.xml .

# Download and cache dependencies
RUN mvn dependency:go-offline -B

# Copy the application source code
COPY /ServiceRegistry/src ./src

# Build the application
RUN mvn package -DskipTests

# Use a lightweight base image for the runtime environment
FROM openjdk:17-jdk-alpine




# 2. ApiGateway
# Set the working directory in the container
WORKDIR /ApiGateway

# Copy the Maven project files
COPY /ApiGateway/pom.xml .

# Download and cache dependencies
RUN mvn dependency:go-offline -B

# Copy the application source code
COPY /ApiGateway/src ./src

# Build the application
RUN mvn package -DskipTests

# Use a lightweight base image for the runtime environment
FROM openjdk:17-jdk-alpine

COPY --from=builder ApiGateway/target/*.jar /ApiGateway.jar


#3. AuthService
# Set the working directory in the container
WORKDIR /AuthService

# Copy the Maven project files
COPY /AuthService/pom.xml .

# Download and cache dependencies
RUN mvn dependency:go-offline -B

# Copy the application source code
COPY /AuthService/src ./src

# Build the application
RUN mvn package -DskipTests

# Use a lightweight base image for the runtime environment
FROM openjdk:17-jdk-alpine


#4. NotificationService
# Set the working directory in the container
WORKDIR /NotificationService

# Copy the Maven project files
COPY /NotificationService/pom.xml .

# Download and cache dependencies
RUN mvn dependency:go-offline -B

# Copy the application source code
COPY /NotificationService/src ./src

# Build the application
RUN mvn package -DskipTests

# Use a lightweight base image for the runtime environment
FROM openjdk:17-jdk-alpine


#5. PaymentsBillingService
# Set the working directory in the container
WORKDIR /PaymentsBillingService

# Copy the Maven project files
COPY /PaymentsBillingService/pom.xml .

# Download and cache dependencies
RUN mvn dependency:go-offline -B

# Copy the application source code
COPY /PaymentsBillingService/src ./src

# Build the application
RUN mvn package -DskipTests

# Use a lightweight base image for the runtime environment
FROM openjdk:17-jdk-alpine


#6. PropertyManagementService
# Set the working directory in the container
WORKDIR /PropertyManagementService

# Copy the Maven project files
COPY /PropertyManagementService/pom.xml .

# Download and cache dependencies
RUN mvn dependency:go-offline -B

# Copy the application source code
COPY /PropertyManagementService/src ./src

# Build the application
RUN mvn package -DskipTests

# Use a lightweight base image for the runtime environment
FROM openjdk:17-jdk-alpine


#7. TenantManagementService
# Set the working directory in the container
WORKDIR /TenantManagementService

# Copy the Maven project files
COPY /TenantManagementService/pom.xml .

# Download and cache dependencies
RUN mvn dependency:go-offline -B

# Copy the application source code
COPY /TenantManagementService/src ./src

# Build the application
RUN mvn package -DskipTests

# Use a lightweight base image for the runtime environment
FROM openjdk:17-jdk-alpine




# ........ Copy the jar files to the root ........



# # Set the working directory in the container
# WORKDIR /

# # Copy the built .war file from the builder stage
# COPY --from=builder ServiceRegistry/target/*.jar /ServiceRegistry.jar
# COPY --from=builder ApiGateway/target/*.jar /ApiGateway.jar
# COPY --from=builder AuthService/target/*.jar /AuthService.jar
# COPY --from=builder NotificationService/target/*.jar /NotificationService.jar
# COPY --from=builder PaymentsBillingService/target/*.jar /PaymentsBillingService.jar
# COPY --from=builder PropertyManagementService/target/*.jar /PropertyManagementService.jar
# COPY --from=builder TenantManagementService/target/*.jar /TenantManagementService.jar


# ........ Running ...........


# Expose the ports of your application runs on
EXPOSE 8761
EXPOSE 2028
#EXPOSE 2029
#EXPOSE 2027
#EXPOSE 2023
#EXPOSE 2020
#EXPOSE 2021

# Command to run the application
CMD ["java", "-jar", "ServiceRegistry.jar"]
CMD ["java", "-jar", "ApiGateway.jar"]
CMD ["java", "-jar", "AuthService.jar"]
CMD ["java", "-jar", "NotificationService.jar"]
CMD ["java", "-jar", "PaymentsBillingService.jar"]
CMD ["java", "-jar", "PropertyManagementService.jar"]
CMD ["java", "-jar", "TenantManagementService.jar"]