package com.marvel.icloud.user_service.servicve.impl;

import com.marvel.icloud.user_service.entity.User;
import com.marvel.icloud.user_service.servicve.UserService;
import org.springframework.stereotype.Service;

/**
 * @Description UserServiceImpl
 * @Author wupeng
 * @Motto Stay Hungry, Stay Foolish !
 * @Date 2020/9/8 6:15 下午
 **/

@Service
public class UserServiceImpl implements UserService {
    @Override
    public User queryById(Long id) {
        return new User(id, "wupeng", "password");
    }
}
