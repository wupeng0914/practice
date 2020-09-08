package com.marvel.icloud.user_service.servicve;

import com.marvel.icloud.user_service.entity.User;

/**
 * @Description UserService
 * @Author wupeng
 * @Motto Stay Hungry, Stay Foolish !
 * @Date 2020/9/8 6:15 下午
 **/
public interface UserService {

    User queryById(Long id);

}
