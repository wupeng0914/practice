package com.example.netty.rpc;

/**
 * @Description HelloServiceImpl
 * @Author wupeng
 * @Motto Stay Hungry, Stay Foolish !
 * @Date 2020/10/28 4:05 下午
 **/
public class HelloServiceImpl implements HelloService {

    private static int count=0;

    @Override
    public String hello(String msg) {
        System.out.println("收到客户端消息="+msg);
        if (msg != null){
            return "客户你好，我已收到你的消息[" + msg + "] 第" + (++count) + " 次";
        } else {
            return "你好客户端，我已收到你的消息";
        }
    }
}
