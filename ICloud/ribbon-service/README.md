####摘要<br/>
Spring Cloud Netflix Ribbon 是Spring Cloud Netflix 子项目的核心组件之一，主要给服务间调用及API网关转发提供负载均衡的功能，本文将对其用法进行详细介绍。

####Ribbon简介<br/>
在微服务架构中，很多服务都会部署多个，其他服务去调用该服务的时候，如何保证负载均衡是个不得不去考虑的问题。负载均衡可以增加系统的可用性和扩展性，当我们使用RestTemplate来调用其他服务时，Ribbon可以很方便的实现负载均衡功能。<br/>

RestTemplate是一个HTTP客户端，使用它我们可以方便的调用HTTP接口，支持GET、POST、PUT、DELETE等方法。<br/>

添加Ribbon依赖：<br/>
`    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
    </dependency>`
    
####使用@Loadbalanced 注解赋予RestTemplate负载均衡的能力<br/>
使用Ribbon的负载均衡功能非常简单，和直接使用RestTemplate没什么两样，只需给RestTemplate添加一个@LoadBalanced即可。<br/>
`@Configuration
 public class RibbonConfig {
     @Bean
     @LoadBalanced
     public RestTemplate restTemplate() {
         return new RestTemplate();
     }
 }`
 
 ####Ribbon的常用配置<br/>
 
 `ribbon:
   ConnectTimeout: 1000 #服务请求连接超时时间（毫秒）
   ReadTimeout: 3000 #服务请求处理超时时间（毫秒）
   OkToRetryOnAllOperations: true #对超时请求启用重试机制
   MaxAutoRetriesNextServer: 1 #切换重试实例的最大个数
   MaxAutoRetries: 1 # 切换实例后重试最大次数
   NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RandomRule #修改负载均衡算法`
   
####指定服务进行配置   
`user-service:
   ribbon:
     ConnectTimeout: 1000 #服务请求连接超时时间（毫秒）
     ReadTimeout: 3000 #服务请求处理超时时间（毫秒）
     OkToRetryOnAllOperations: true #对超时请求启用重试机制
     MaxAutoRetriesNextServer: 1 #切换重试实例的最大个数
     MaxAutoRetries: 1 # 切换实例后重试最大次数
     NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RandomRule #修改负载均衡算法
`

####Ribbon的负载均衡策略<br/>
com.netflix.loadbalancer.RandomRule：从提供服务的实例中以随机的方式；<br/>
com.netflix.loadbalancer.RoundRobinRule：以线性轮询的方式，就是维护一个计数器，从提供服务的实例中按顺序选取，第一次选第一个，第二次选第二个，以此类推，到最后一个以后再从头来过；<br/>
com.netflix.loadbalancer.RetryRule：在RoundRobinRule的基础上添加重试机制，即在指定的重试时间内，反复使用线性轮询策略来选择可用实例；<br/>
com.netflix.loadbalancer.WeightedResponseTimeRule：对RoundRobinRule的扩展，响应速度越快的实例选择权重越大，越容易被选择；<br/>
com.netflix.loadbalancer.BestAvailableRule：选择并发较小的实例；<br/>
com.netflix.loadbalancer.AvailabilityFilteringRule：先过滤掉故障实例，再选择并发较小的实例；<br/>
com.netflix.loadbalancer.ZoneAwareLoadBalancer：采用双重过滤，同时过滤不是同一区域的实例和故障实例，选择并发较小的实例。<br/>


 
 
 