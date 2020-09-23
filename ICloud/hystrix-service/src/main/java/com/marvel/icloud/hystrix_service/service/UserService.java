package com.marvel.icloud.hystrix_service.service;

import com.marvel.icloud.hystrix_service.Result;

/**
 * @Description UserService
 * @Author wupeng
 * @Motto Stay Hungry, Stay Foolish !
 * @Date 2020/9/21 4:49 下午
 **/
public interface UserService {
    Result getUser(Long id);

    Result getUserCommand(Long id);

    Result getUserException(Long id);

    Result getUserCache(Long id);
}
