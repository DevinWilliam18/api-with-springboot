# # Use a base image with JDK installed
# FROM openjdk:11-jre-slim

# # Set working directory in the container
# WORKDIR /app
 
# COPY .mvn/ .mvn
# COPY mvnw pom.xml ./

# RUN ./mvnw dependency:go-offline
 
# COPY src ./src
 
# CMD ["./mvnw", "spring-boot:run"]

# Stage 1: Build stage

FROM maven:3.8-openjdk-11-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY ./src ./src
RUN mvn package -DskipTests

# Stage 2: Runtime stage
FROM openjdk:11-ea-11-jre
WORKDIR /app
COPY --from=build /app/target/demo-0.0.1.jar ./demo-0.0.1.jar
CMD ["java", "-jar", "demo-0.0.1.jar"]