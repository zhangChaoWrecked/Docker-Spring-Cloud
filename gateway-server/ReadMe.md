  gateway-server 网关  负责转发请求到  service-background-manage 、service-user
 
 
  一 、 SpringCloud Gateway 简单描述
 
         目标是代替Netflix Zuul 它不仅能提供统一的路由方式 并且基于Filter链的方式提供了网关的基本功能:安全 监控 埋点和限流
 
         .Route
              网关的基础元素 由ID 目标URL 断言 过滤器组成 当请求到达网关的时候 由GateWay Handler Mapping 进行断言进行路由匹配 当断言为真的时候
          匹配到路由
 
         .Predicate
              匹配条件
 
         .Filter
              GateWay的过滤器 可以在请求发出前后进行一些业务的处理  只有pre post 两种
 
             filters:
              -AddRequestHeader=X-Request-Foo,Bar       #AddRequestHeader过滤器工厂 符合规则匹配成功后再请求头中加入该请求头
             filters:
              -RemoveRequestHeader=X-Request-Foo,Bar    #AddRequestHeader过滤器工厂 符合规则匹配成功后再请求头中移除该请求头
             filters:
              -SetStatus=401                            #设置响应码
              filters:
              -RedirectTo=302,www.baidu.com             #重定向
 
         全局过滤器 com.filter.IPCheckFilter
 
 
         以及自定义限流 com.cxytiandi.spring_cloud_gateway.CustomRedisRateLimiter
         跨域配置(配置文件或者类)  com.config.CorsConfig
         自定义异常处理    com.exception.JsonExceptionHandler
         重试机制 阅读配置文件
 
 
 
 二、 多个微服务的swagger 集成  com.swagger.DocumentationConfig
 
 
 
 
 
