#### 服务注册与发现
Eureka简介：<br/>
    在微服务架构中往往会有一个注册中心，每个微服务都会向注册中心去注册自己的地址及端口信息，注册中心维护着服务名称与服务实例的对应关系。每个微服务都会定时从注册中心获取服务列表，同时汇报自己的运行情况，这样当有的服务需要调用其他服务时，就可以从自己获取到的服务列表中获取实例地址进行调用，Eureka实现了这套服务注册与发现机制。

Eureka注册中心集群:<br/>
    添加application-replica1.yml 与application-replica2.yml 两个配置文件，互相注册对方<br/>
    修改本地配置hosts文件，添加映射：<br/>
    127.0.0.1 replica1<br/>
    127.0.0.1 replica2<br/>
    复制两个启动配置：<br/>
        EurekaServerReplica1、EurekaServerReplica2，分别使用配置文件replca1、replica2<br/>
        Active profiles：replica1<br/>
        Active profiles：replica2<br/>    
    修改Eureka-client，将其连接到集群： <br/>
        service-url：<br/>
            defaultZone： http://replica1:10010/eureka/, http://replica2:10000/eureka/ <br/>
    