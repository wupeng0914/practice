package com.marvel.icloud.feign_service.service;

import com.marvel.icloud.feign_service.Result;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Description UserService
 * @Author wupeng
 * @Motto Stay Hungry, Stay Foolish !
 * @Date 2020/9/23 3:05 下午
 **/
@FeignClient(value = "user-service")
public interface UserService {

//    Result

}
