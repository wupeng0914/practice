package com.marvel.icloud.hystrix_service.service.impl;

import com.marvel.icloud.hystrix_service.Result;
import com.marvel.icloud.hystrix_service.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

/**
 * @Description UserServiceImpl
 * @Author wupeng
 * @Motto Stay Hungry, Stay Foolish !
 * @Date 2020/9/21 4:49 下午
 **/
@Service
public class UserServiceImpl implements UserService {

    private static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${service-url.user-service}")
    private String userServicePath;

    @Override
    @HystrixCommand(fallbackMethod = "fallbackMethod")
    public Result getUser(Long id) {
        return restTemplate.getForObject(userServicePath + "/user/query/{1}", Result.class, id);
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallbackMethod",
                    commandKey = "getUserCommand",
                    groupKey = "getUserGroup",
                    threadPoolKey = "getUserThreadPool")
    public Result getUserCommand(Long id) {
        return restTemplate.getForObject(userServicePath + "/user/query/{1}", Result.class, id);
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallbackMethod",ignoreExceptions = {NullPointerException.class})
    public Result getUserException(Long id) {
        if (id == 1){
            throw new IndexOutOfBoundsException("哦豁，报错了");
        } else if (id == 2){
            throw new NullPointerException("哦豁，空指针喽");
        }
        return restTemplate.getForObject(userServicePath + "/user/query/{1}", Result.class, id);
    }

    @Override
    @CacheResult(cacheKeyMethod = "getCacheKey")
    @HystrixCommand(fallbackMethod = "fallbackMethod", commandKey = "getUserCache")
    public Result getUserCache(Long id) {
        LOGGER.info("getUserCache id：{}", id);
        return restTemplate.getForObject(userServicePath + "/user/query/{1}", Result.class, id);
    }

    public Result fallbackMethod(@PathVariable Long id){
        return new Result("服务暂不可用", 500);
    }

    public String getCacheKey(Long id){
        return String.valueOf(id);
    }
}
