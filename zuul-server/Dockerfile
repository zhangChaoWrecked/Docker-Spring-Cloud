FROM java:8
VOLUME /docker-spring-cloud
ADD ./target/zuul-server-1.0-SNAPSHOT.jar /docker-spring-cloud/zuul-server.jar
CMD ["java",  "-jar", "/docker-spring-cloud/zuul-server.jar"]
EXPOSE 2002

 