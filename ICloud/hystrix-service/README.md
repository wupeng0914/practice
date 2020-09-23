#####Hystrix 服务降级

```java
// 相关依赖
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>
```

服务降级使用的方法：

​		在需要降级的方法上添加 **@HystrixCommand **注解，并指定**fallbackMethod**方法：

```java
@HystrixCommand(fallbackMethod = "fallbackMethod1")
public Result getUser(Long id) {
	return restTemplate.getForObject(userServiceUrl + "/user/{1}", Result.class, id);
}

public Result fallbackMethod1(@PathVariable Long id) {
	return new Result("服务调用失败", 500);
}

```

##### @HystrixCommand

**注解常用参数：**

> fallbackMethod：指定服务降级方法；
>
> ignoreExceptions：忽略某些异常，不发生服务降级；
>
> commandKey：命令名称，用于区分不同的命令；
>
> groupKey：分组名称，Hystrix会根据不同的分组来统计命令的告警及仪表盘信息；
>
> threadPoolKey：线程池名称，用于划分线程池。

##### Hystrix使用缓存

**相关注解：**

> **@CacheResult：**开启缓存，默认所有参数作为缓存的key，**cacheKeyMethod**可以通过返回String类型的方法指定key；
>
> **@CacheKey：**指定缓存的key，可以指定参数或者指定参数中的属性值作为缓存key，**cacheKeyMethod**还可以通过返回String类型的方法指定；
>
> **@CacheRemove：**移除缓存，需要指定commandKey。

```java
@CacheResult(cacheKeyMethod = "getCacheKey")
@HystrixCommand(fallbackMethod = "fallbackMethod1", commandKey = "getUserCache")
public Result getUserCache(Long id) {
	LOGGER.info("getUserCache id:{}", id);
	return restTemplate.getForObject(userServiceUrl + "/user/{1}", Result.class, id);
}

/**
 * 为缓存生成key的方法
 *
 * @return
 */
public String getCacheKey(Long id) {
	return String.valueOf(id);
}
```

##### Hystrix的常用配置

```yml
hystrix:
  command: #用于控制HystrixCommand的行为
    default:
      execution:
        isolation:
          strategy: THREAD #控制HystrixCommand的隔离策略，THREAD->线程池隔离策略(默认)，SEMAPHORE->信号量隔离策略
          thread:
            timeoutInMilliseconds: 1000 #配置HystrixCommand执行的超时时间，执行超过该时间会进行服务降级处理
            interruptOnTimeout: true #配置HystrixCommand执行超时的时候是否要中断
            interruptOnCancel: true #配置HystrixCommand执行被取消的时候是否要中断
          timeout:
            enabled: true #配置HystrixCommand的执行是否启用超时时间
          semaphore:
            maxConcurrentRequests: 10 #当使用信号量隔离策略时，用来控制并发量的大小，超过该并发量的请求会被拒绝
      fallback:
        enabled: true #用于控制是否启用服务降级
      circuitBreaker: #用于控制HystrixCircuitBreaker的行为
        enabled: true #用于控制断路器是否跟踪健康状况以及熔断请求
        requestVolumeThreshold: 20 #超过该请求数的请求会被拒绝
        forceOpen: false #强制打开断路器，拒绝所有请求
        forceClosed: false #强制关闭断路器，接收所有请求
      requestCache:
        enabled: true #用于控制是否开启请求缓存
  collapser: #用于控制HystrixCollapser的执行行为
    default:
      maxRequestsInBatch: 100 #控制一次合并请求合并的最大请求数
      timerDelayinMilliseconds: 10 #控制多少毫秒内的请求会被合并成一个
      requestCache:
        enabled: true #控制合并请求是否开启缓存
  threadpool: #用于控制HystrixCommand执行所在线程池的行为
    default:
      coreSize: 10 #线程池的核心线程数
      maximumSize: 10 #线程池的最大线程数，超过该线程数的请求会被拒绝
      maxQueueSize: -1 #用于设置线程池的最大队列大小，-1采用SynchronousQueue，其他正数采用LinkedBlockingQueue
      queueSizeRejectionThreshold: 5 #用于设置线程池队列的拒绝阀值，由于LinkedBlockingQueue不能动态改版大小，使用时需要用该参数来控制线程数
```

