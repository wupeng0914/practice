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
Eureka注册中心添加认证：<br/>
    添加SpringSecurity模块：<br/>
    `<dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-security</artifactId>
     </dependency>`    
    添加application.yml配置：<br/>
    `server:
       port: 8004
     spring:
       application:
         name: eureka-security-server
       security:
         user:
           # 配置spring security登录用户名和密码
           name: root
           password: root 
     eureka:
       instance:
         hostname: localhost
       client:
         fetch-registry: false
         register-with-eureka: false
    `
    添加Java配置类 WebSecurityConfig：<br/>
    默认情况下添加SpringSecurity依赖的应用每个请求都需要添加CSRF token才能访问，Eureka客户端注册时并不会添加，所以需要配置/eureka/**路径不需要CSRF token。<br/>
    eureka-client注册到有登录认证的注册中心：<br/>
    http://${username}:${password}@${hostname}:${port}/eureka/ <br/>
    
    service-url:<br/>
          # 此处需要修改注册中心地址格式<br/>
          defaultZone: http://root:root@localhost:10086/eureka/<br/>
  
Eureka 常用配置
`eureka:<br/>
   client: #eureka客户端配置<br/>
     register-with-eureka: true #是否将自己注册到eureka服务端上去<br/>
     fetch-registry: true #是否获取eureka服务端上注册的服务列表<br/>
     service-url:<br/>
       defaultZone: http://localhost:8001/eureka/ # 指定注册中心地址<br/>
     enabled: true # 启用eureka客户端<br/>
     registry-fetch-interval-seconds: 30 #定义去eureka服务端获取服务列表的时间间隔<br/>
   instance: #eureka客户端实例配置<br/>
     lease-renewal-interval-in-seconds: 30 #定义服务多久去注册中心续约<br/>
     lease-expiration-duration-in-seconds: 90 #定义服务多久不去续约认为服务失效<br/>
     metadata-map:<br/>
       zone: guangdong #所在区域<br/>
     hostname: localhost #服务主机名称<br/>
     prefer-ip-address: false #是否优先使用ip来作为主机名<br/>
   server: #eureka服务端配置<br/>
     enable-self-preservation: false #关闭eureka服务端的保护机制<br/>
` 