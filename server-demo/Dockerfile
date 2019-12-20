FROM java:8
VOLUME /docker-spring-cloud
ADD ./target/server-demo-1.0-SNAPSHOT.jar /docker-spring-cloud/server-demo.jar
CMD ["java",  "-jar", "/docker-spring-cloud/server-demo.jar"]
EXPOSE 8081

 