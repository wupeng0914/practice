package com.marvel.chat.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Description WebSocketServer
 * @Author wupeng
 * @Motto Stay Hungry, Stay Foolish !
 * @Date 2020/9/15 4:41 下午
 **/
public class WebSocketServer {
    public static void main(String[] args) throws InterruptedException {
        //初始化主线程池（boss线程池）
        NioEventLoopGroup mainGroup = new NioEventLoopGroup();
        //初始化从线程池（worker线程池）
        NioEventLoopGroup subGroup = new NioEventLoopGroup();
        try {
            //创建服务器启动器
            ServerBootstrap bootstrap = new ServerBootstrap();
            //指定所要使用的主线程池和从线程池
            bootstrap.group(mainGroup, subGroup)
                    //指定使用Nio通道类型
                    .channel(NioServerSocketChannel.class)
                    //指定通道初始化器加载通道处理器
                    .childHandler(new WebSocketServerInitializer());
            //绑定端口号启动服务器，并等待服务器启动
            //ChannelFuture 是netty的回调消息
            ChannelFuture future = bootstrap.bind(9090).sync();
            //等待服务器socket关闭
            future.channel().closeFuture().sync();
        } finally {
            //优雅的关闭boss线程池和worker线程池
            mainGroup.shutdownGracefully();
            subGroup.shutdownGracefully();
        }
    }
}
