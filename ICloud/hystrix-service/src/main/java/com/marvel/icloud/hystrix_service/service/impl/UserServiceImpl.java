package com.marvel.icloud.hystrix_service.service.impl;

import com.marvel.icloud.hystrix_service.Result;
import com.marvel.icloud.hystrix_service.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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
    @Autowired
    private RestTemplate restTemplate;

    @Value("${service-url.user-service}")
    private String userServicePath;

    @Override
    @HystrixCommand(fallbackMethod = "fallbackMethod")
    public Result getUser(Long id) {
        return restTemplate.getForObject(userServicePath + "/user/query/{1}", Result.class, id);
    }

    public Result fallbackMethod(@PathVariable Long id){
        return new Result("服务暂不可用", 500);
    }
}
