# 基于哪个镜像
FROM java:8
# 将目标容器中的目录持久化
VOLUME /docker-spring-cloud
# 拷贝文件到容器
ADD ./target/config-server-0.0.1-SNAPSHOT.jar /docker-spring-cloud/config-server.jar
#指定启动容器时执行的命令
CMD ["java",  "-jar", "/docker-spring-cloud/config-server.jar"]
# 开放8761端口
EXPOSE 8888

