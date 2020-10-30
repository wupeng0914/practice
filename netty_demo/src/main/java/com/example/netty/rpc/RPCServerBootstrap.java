package com.example.netty.rpc;

/**
 * @Description ServerBootstrap
 * @Author wupeng
 * @Motto Stay Hungry, Stay Foolish !
 * @Date 2020/10/28 4:09 下午
 **/
public class RPCServerBootstrap {

    public static void main(String[] args) throws InterruptedException {
        NettyServer.start("127.0.0.1", 7000);
    }

}
