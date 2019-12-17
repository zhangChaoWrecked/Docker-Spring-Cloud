     Zuul 是Netflix OSS 中的 员,是 个基于 JVM 路由和服务端的负载均衡器 提供路
    监控 弹性、安全等方面的服务框架 Zuul 能够与 Eureka Ribbon Hystrix 等组件配
    合使用
    Zuul 的核心是过滤器,通过这些过滤器我们可以扩展出很 功能,比如
      动态路由：动态地将客户端的请求路由到后端不同的服务,做一些逻辑处理,比如
    聚合多个服务的数据返回
      请求监控：可以对 个系统的请求进行监控,记录详细的请求响应日志,可以实时
    统计出当前系统的访问量以及监控状态
      认证鉴权：对每一个访问的请求做认证,拒绝非法请求,保护好后端的服
      压力测试：压力测试是一项很重要的工作,像电商公司需要模拟更多真实的用
    户并发量来保证重大活动时系统的稳定 通过 Zuul 可以动态地将请求转发到后端服
    务的集群中,还可以识别测试流量和真实流量 从而做一些特殊处理
       灰度发布：灰度发布可以保证整体系统的稳定,在初始灰度 的时候就可以发现、调
    问题,以保证其影响度


    Zuul 路由配置:

     默认规则举例:
     API 网关地址:http://localhost:2103
     用户服务名称:user-service
     用户登录接口:/user/login
     那么通过 Zuul 访问登录接口的规则就是http://localhost:2103/user-service/user/login

      1.指定具体服务路由
       zuul.routes.fsh-house.path=/api-house/**
       上述代码将 fsh-house 服务的路由地址配置成了api-house ，也就是当需要访问fsh-house中的接口时，我们可以通过api-house/hello
       来进行,其实就是将服务名称变成了我们自定义的名称 有的时候服务名称太长了，放在URL中不太友好，我就希望变得更友好一点，就可以
         这么去配置 这边 api-house/ 后面一定要配置两个星号，两个星号表示可以转发任意层级的 URL
     2.路由前缀
         比如 http://xxx.com/user/login 这是个登录的接口，现在想将其变成 http://xxx.com/rest/user/login ，
         即在每个接口前面加/rest
       zuul.prefix=/rest
       
       
    Zuul 过滤器:

    Zuul中的过滤器跟我们之前使用的 javax.servlet.Filter不一样， javax.servlet.Filter 只有1种类型，可以通过配置 urlPatterns 来拦截对应的请求

    请求发过来首先到 pre 过滤器，再到routing 过滤器 ，最后 post过滤器，任何一个过滤器有异常 会进入 error 过滤器
      pre    可以在请求被路由之前调用,适用于身份认证的场景，认证通过后再继续执行下面的流程
      route  在路由请求时被调用,适用于灰度发布场景，在将要路由的时候可以做一些自定义的逻辑
      post   在 route error 过滤器之后被调用 速种过滤器将请求路由到达具体的服务之后执行,适用于需要添加响应头，记录响应日志等应用场景
      error  处理请求发生错误时被调用,在执行过程中发送错误时会进入 error 过滤器，可以用来统一记录错误信息


    shouldFilter 是否执行该过滤器 true 为执行， false 为不执行，这个也可以利用配中心来实现，达到动态的开启和关闭过滤器
    • filterType ：过滤器类型，可选值有 pre route post error
    • filterOrder ：过滤器的执行顺序，数值越小，优先级越高
    • run ：执行自己的业务逻辑，本段代码中是通过判断请求 IP 否在黑名单中，决定
    是否进行拦截的 BasicConf 是一个配置类，里面有个字段是 IP 的黑名单，判断条件成立之后，
    通过设置 ctx.setSendZuu!Response(false)，告诉 Zuul不需要将当前请求转发到后端的服务了
    通过 setResponseBody 返回数据给客户端