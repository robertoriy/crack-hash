FROM openjdk:21-jdk

WORKDIR /app

COPY target/manager.jar /app/manager.jar

ENTRYPOINT ["java","-jar","/app/manager.jar"]
