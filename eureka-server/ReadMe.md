
## 注册中心API


功能 | 描述
---|---
注册新的应用实例 |	POST /eureka/apps/ (appld}	可以输入json或xml格式的body, 成功返回204
注销应用实例|	DELETE/eureka/apps/ (appld}/{instanceld}	成功返回200
应用实例发送心跳|	PUT /eureka/apps/ {appld}/ {instanceld}	成功返回200,如果instanceld不存 在返回404
查询所有实例|	GET /eureka/apps	成功返回200,输出json或xml格式
査询指定appld的实例|	GET /eureka/apps/(appld)	成功返回200,输出json或xml格式
根据指定appld和instance- id 查询|	GET /eureka/apps/(appld)/(instanceld)	成功返回200,输出json或xml格式
根据指定instanceld查询|	GET/eureka/instances/(instanceld}	成功返回200,输出json或xml格式
暂停应用实例|	PUT/eureka/apps/(appId}/(instanceId)/status?value=OUT_OF_SERVICE	成功返回200,失败返回500
恢复应用实例 |	DELETE /eureka/apps/ (appld} / {instanceld} /status?value=UP (value 参数可不传)	成功返回200,失败返回500

###### 提示：
      GET http://alpha:123456@localhost:8761/eureka/apps/server-demo     查询AppId指定实例
      GET http://alpha:123456@localhost:8761/eureka/apps/server-demo/server-demo:192.168.188.188:8081    查询AppId 和 instance- id指定实例
	  PUT http://alpha:123456@localhost:8761/eureka/apps/server-demo/server-demo:192.168.188.188:8081/status?value=OUT_OF_SERVICE 	   暂停应用实例 	
      DELETE http://alpha:123456@localhost:8761/eureka/apps/server-demo/server-demo:192.168.188.188:8081  注销应用实例
      POST  http://alpha:123456@localhost:8761/eureka/apps/server-demo/server-demo:192.168.188.188:8081  增加应用实例





      Spring Cloud Eureka Spring Cloud Netflix 微服务 件的一部分,基于 Netflix Eureka做了二次封装,
     主要负责实现微服务架构中的服务治理功能 Spring Cloud Eureka 是一个基于 REST 的服务,并且提供了基于Java
    的客户端组件 能够非常方便地将服务注册到Spring Cloud Eureka 中进行统一管理服务治理在微服务架构中是必不可
    以少的一部分。

       阿里开源的 Dubbo 框架就是针对于服务治理的 服务治理必须要有个注册中心,除了用 Eureka 作为注册中心外,
    我们还可以使用 Consul Etcd Zookeeper 等来作为服务的注册中心用过 Dubbo 的读者应该清楚, Dubbo 中也有几种
    注册中心,比如基于 Zookeeper 基于Redis的等,用得最多的还 Zookeeper方式至于使用哪种方式都是可以的,中心无
    非就是管理所有服务的信息和状态 若用我们生活中的列子来说明的话。

    例如:
      12306 网站比较合适 首先 12306 网站就好比一个注册中心,我们的顾客就好 比调用的客户端,当我坐火车时,会去 12306 网站上看有没有票,
    有票就可 以购买,然后拿到火车的班次 时间等,最后出发。

      程序也是一样,需要调用某个服务的时候,你会先去Eureka 中去拉取服务列表,查看你调用的服务在不在其中,在的
    话就拿到服务地址、端口等信息,然后调用注册中 心带来的好处就是你不需要知道有多少提供方,你只需要关注注册中心即可,
    你不必关心有多少火车在运行,你只需要去 12306 网站上看有没有票可以买就可以了为什么 Eureka Zookeeper 更适合作为
    注册中心呢？
      主要是因为 Eureka 是基于 AP则构建的,而 ZooKeeper 是基于 CP 原则构建的 在分布式系统领域有个著名的CAP定
    理,C为数据一致性；A为服务可用性；P为服务对网络分区故障的容错性 这三个特性在任何分布式系统中都不能同时满足,最多
    同时满足两个 Zookeeper有一个 Leader ,而且在 Leader 无法使用的时候通过 Paxos ( ZAB 算法举出一个新的 Leader这个
    Leader 的任务就是保证写数据的时候只向这个 Leader 写人Leader 会同步信息到其他节点 通过这个操作就可以保证数据的一致
    性总而言之,想要保证 AP 就要用 Eureka 想要保证 CP 就要用 Zookeeper Dubbo 中大部分都是基于 Zookeeper 作为注册中心的
    Spring Cloud 当然首选 Eureka

    1.服务的监控 com.listener.EurekaStateChangeListener


    一、 Eureka 高可用搭建

     下面我们以2个节点为例来说明搭建方式 假设我们有 master slaveone 两台机器
     1.将 master注册到 slaveone 上面
     2.将 slaveone注册到 master 上面
     如果是 台机器,以此类推：
     1.将 master 注册到 slaveone slavetwo 上面
     2.将 slaveone 注册到 master slavetwo 上面
     3.将 slavetwo注册到 master slaveone 上面


    二、搭建步骤
     需要增2个属性文件,通过不同的环境来启动不同的实例

    application-master.properties:
                server.port=8761
                #指向你的从节点的 Eureka
                eureka.client.serviceUrl.defaultZone=http://用户名:密码@slaveone:8762/eureka/

    application-slaveone.properties:
                server.port=8762
                #指向你的主节点的 Eureka
                eureka.client.serviceUrl.defaultZone=http://用户名:密码@master:8761/eureka/


    application.properties:
                spring.application.name=fangjia-eureka
                eureka.instance.hostname=localhost
                #由于该应用为注册中心,所以设置为 false 代表不向汪册中心注册自己
                eureka.client.register-with-eureka=false
                #由于注册中心的职责就是维护服务实例 他并不需要去检索服务
                eureka.client.fetch-registry=false
                #开启 认证
                security.basic.enabled=true
                #用户名
                security.user.name=wrecked
                #密码
                security.user.password=123456
                #指定不同的环境
                spring.profiles.active=master

     A机器默认用master B机器 将 {spring.profiles.active=master} 修改为  {spring.profiles.active=slaveone}

      这样就将 master注册到了slaveone,slaveone注册到了 master 中,无论谁挂掉了,应用都能继续使用存活的这个注册中心
    在客户端中我们通过配置eureka.client.serviceUrl.defaultZone来指定对应的注册中心,当我们的注册中心有多个节点后,
    那就需要修改 eureka.client.serviceUrl.defaultZone配置为多个节点的地址,多个地址用英文逗号隔开即可:

    eureka.client.serviceUrl.defaultZone=http://${security.user.name}:${security.user.password}@master:8761/eureka/,
    http://${security.user.name}:${security.user.password}@slaveone:8672/eureka/