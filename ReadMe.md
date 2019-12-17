
## DockerSpringCloud

模块 | 说明
---|---
eureka-server | 注册中心
gateway-server| 网关中心(第二代网关Gateway)
zuul-server| 网关中心(第一代网关Zuul)
config-server| 配置中心
monitoring-server| 监控中心
server-demo| 微服务示例(包含熔断 配置中心的使用示例)




## SpringCloud简介
 组件名称| 所属项目|组件分类
 ---|---|---
Eureka	    |     spring-cloud-netflix	  |   注册中心
Zuul	    |     spring-cloud-netflix	  |   第一代网关
Sidecar	    |     spring-cloud-netflix	  |   多语言
Ribbon	    |     spring-cloud-netflix	  |   负载均衡
Hystrix	    |     spring-cloud-netflix	  |   熔断器
Turbine	    |     spring-cloud-netflix	  |   集群监控
Feign	    |     spring-cloud-openfeign  |   声明式HTTP客户端
Consul	    |     spring-cloud-consul	  |   注册中心
Gateway	    |     spring-cloud-gateway	  |   第二代网关
Sleuth	    |     spring-cloud-seluth	  |   链路追踪
Config	    |     spring-cloud-config	  |   配置中心
Bus	        |     spring-cloud-bus	      |   总线
Pipeline	|     spring-cloud-pipelines  |   部署管道
Dataflow	|     spring-cloud-dataflow	  |   数据处理
zipkin	    |  spring-cloud-starter-zipkin|  链路追踪展示


###### 提示：		   	
    Spring Cloud Zuul处理每个请求的方式是分别对每个请求分配一个线程来处理。根据参考 数据统计，目前Zuul最多能达到1000至2000 QPSo在高并发的场景下，不推荐使用Zuul作 为网关。
    因此出现了 Spring Cloud的第二代网关，BP Spring Cloud Gateway
    Spring CloudGateway底层基于Netty实现（Netty的线程模型是多线程reactor模型，使用boss线程和worker线程接收并异步处理请求，具有很强大的高并发处理能力），
    因此SpringCloudGateway出现的目的就是替代NetflixZuul 
  
