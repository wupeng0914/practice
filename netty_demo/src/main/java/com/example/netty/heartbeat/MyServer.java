package com.example.netty.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @Description MyHeart
 * @Author wupeng
 * @Motto Stay Hungry, Stay Foolish !
 * @Date 2020/10/27 7:06 下午
 **/
public class MyServer {

    public static void main(String[] args) throws InterruptedException {

        //创建两个线程
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();

                            //加入一个netty提供的IdleStateHandler
                            //说明：
                            //1、IdleStateHandler 是netty提供的处理空闲状态的处理器
                            //2、long readIdleTime：表示多长时间没有读，就会发送一个心跳检测包以检测是否连接
                            //3、long writeIdleTime：表示多长时间没有写，就会发送一个心跳检测包以检测是否连接
                            //4、long allIdleTime：表示多长时间没有读写，就会发送一个心跳检测包以检测是否连接
                            //5、当IdleStateEvent触发后，就会传递给管道的下一个handler去处理，通过调用（触发）下一个handler的userEventTriggered，
                            //   在该方法中去处理IdleStateEvent（读空闲、写空闲、读写空闲）
                            pipeline.addLast(new IdleStateHandler(13,5,2, TimeUnit.SECONDS));
                            //加入一个对空闲检测进一步处理的handler（自定义的）
                            pipeline.addLast(new MyServerHandler());
                        }
                    });

            ChannelFuture future = bootstrap.bind(7000).sync();
            future.channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

}
