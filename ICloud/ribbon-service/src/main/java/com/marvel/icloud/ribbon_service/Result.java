package com.marvel.icloud.user_service.result;

import lombok.Data;

/**
 * @Description Result
 * @Author wupeng
 * @Motto Stay Hungry, Stay Foolish !
 * @Date 2020/9/8 7:18 下午
 **/
@Data
public class Result<T> {

    private T data;

    private String message;

    private int code;

    public Result(){}

    public Result(T data, String message, int code){
        this.data = data;
        this.message = message;
        this.code = code;
    }

    public Result(String message, Integer code) {
        this(null, message, code);
    }

    public Result(T data) {
        this(data, "操作成功", 200);
    }

}
