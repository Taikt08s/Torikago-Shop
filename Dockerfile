FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
# RUN mvn clean package -DskipTests
FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/torikago-0.0.1-SNAPSHOT.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar"]

# FROM maven:3.8.5-openjdk-17 AS build
# WORKDIR /app
# COPY . .

# # Add debugging step to print the content of the current directory
# RUN ls -la

# # Run Maven build with debugging information
# # RUN mvn clean package -X -DskipTests

# FROM openjdk:17.0.1-jdk-slim
# COPY --from=build /target/torikago-0.0.1-SNAPSHOT.jar demo.jar
# EXPOSE 8080
# ENTRYPOINT ["java","-jar","demo.jar"]
