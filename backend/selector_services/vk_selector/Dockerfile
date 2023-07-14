# Use a base image with JDK and Maven pre-installed
FROM maven:3.8.3-openjdk-11-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the source code and pom.xml to the working directory
COPY src ./src
COPY pom.xml .

# Compile the Java code
RUN mvn compile

# Set the entry point to run the compiled Java application
CMD ["java", "-cp", "target/classes", "com.company.Main"]
