package com.marvel.icloud.hystrix_service.controller;

import com.marvel.icloud.hystrix_service.Result;
import com.marvel.icloud.hystrix_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description UserHystrixController
 * @Author wupeng
 * @Motto Stay Hungry, Stay Foolish !
 * @Date 2020/9/21 4:29 下午
 **/
@RestController
@RequestMapping("/user")
public class UserHystrixController {

    @Autowired
    private UserService userService;

    @RequestMapping("/{id}")
    public Result testFallback(@PathVariable Long id){
        return userService.getUser(id);
    }

    @RequestMapping("/testCommandKey/{id}")
    public Result getUserCommand(@PathVariable Long id){
        return userService.getUserCommand(id);
    }

    @GetMapping("/testException/{id}")
    public Result getUserException(@PathVariable Long id){
        return userService.getUserException(id);
    }

}
