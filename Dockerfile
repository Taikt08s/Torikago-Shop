# Use the official OpenJDK base image
#FROM adoptopenjdk/openjdk17:alpine
#
## Set the working directory inside the container
#WORKDIR /app
#
## Copy the compiled JAR file into the container at /app
#COPY target/Torikago-Shop-0.0.1-SNAPSHOT.jar /app/app.jar
#
## Expose the port that your application runs on (adjust if needed)
#EXPOSE 8080
#
## Command to run your application
#CMD ["java", "-jar", "app.jar"]


FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build target/Torikago-Shop-0.0.1-SNAPSHOT.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar"]