FROM java:8-jre
VOLUME /docker-spring-cloud
ADD ./target/monitoring-server-1.0-SNAPSHOT.jar /docker-spring-cloud/monitoring-server.jar
CMD ["java",  "-jar", "/docker-spring-cloud/monitoring-server.jar"]
EXPOSE 8888

 