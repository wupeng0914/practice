package com.marvel.icloud.user_service.controller;

import com.marvel.icloud.user_service.entity.User;
import com.marvel.icloud.user_service.result.Result;
import com.marvel.icloud.user_service.servicve.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description UserController
 * @Author wupeng
 * @Motto Stay Hungry, Stay Foolish !
 * @Date 2020/9/8 7:16 下午
 **/
@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/user/query/{id}")
    public Result<User> getUser(@PathVariable Long id){
        log.info("根据id获取用户信息");
        return new Result<User>(userService.queryById(id));
    }

}
