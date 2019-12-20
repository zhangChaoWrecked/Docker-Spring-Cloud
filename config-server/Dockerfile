
FROM java:8-jre
VOLUME /docker-spring-cloud
ADD ./target/config-server-0.0.1-SNAPSHOT.jar /docker-spring-cloud/config-server.jar
CMD ["java",  "-jar", "/docker-spring-cloud/config-server.jar"]
EXPOSE 8888

