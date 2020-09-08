package com.marvel.icloud.user_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description User
 * @Author wupeng
 * @Motto Stay Hungry, Stay Foolish !
 * @Date 2020/9/8 6:11 下午
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;

    private String username;

    private String password;

}
