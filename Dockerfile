FROM openjdk:17-alpine

COPY target/dot_nibss_simulation_task-0.0.1-SNAPSHOT.jar nibss.jar
ENTRYPOINT ["java","-jar","/nibss.jar"]