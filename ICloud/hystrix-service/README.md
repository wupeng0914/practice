#### Hystrix 服务降级

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

##### @HystrixCommand注解常用参数：

> fallbackMethod：指定服务降级方法；
>
> ignoreExceptions：忽略某些异常，不发生服务降级；
>
> commandKey：命令名称，用于区分不同的命令；
>
> groupKey：分组名称，Hystrix会根据不同的分组来统计命令的告警及仪表盘信息；
>
> threadPoolKey：线程池名称，用于划分线程池。