FROM java:8-jre
VOLUME /docker-spring-cloud
ADD ./target/eureka-server-1.0-SNAPSHOT.jar /docker-spring-cloud/eureka-server.jar
CMD ["java",  "-jar", "/docker-spring-cloud/eureka-server.jar"]
EXPOSE 8888

 