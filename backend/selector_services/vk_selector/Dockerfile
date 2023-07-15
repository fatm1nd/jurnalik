FROM maven:3.8-openjdk-18-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean install package install package

#
# Package stage
#
FROM openjdk:18-slim
EXPOSE 8080
ADD /target/demo.jar demo.jar
ENTRYPOINT ["java","-jar","demo.jar"]