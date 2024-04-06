FROM openjdk:21-jdk

WORKDIR /app

COPY target/worker.jar /app/worker.jar

ENTRYPOINT ["java","-jar","/app/worker.jar"]
