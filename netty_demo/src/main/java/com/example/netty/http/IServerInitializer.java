package com.example.netty.http;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @Description IServerInitializer
 * @Author wupeng
 * @Motto Stay Hungry, Stay Foolish !
 * @Date 2020/10/26 5:33 下午
 **/
public class IServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        //得到管道
        ChannelPipeline pipeline = ch.pipeline();
        //添加一个netty提供的httpServerCodec
        //HttpServerCodec说明：
        //1、HttpServerCodec是netty提供的处理http的编解码器
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());
        //2、增加一个自定义的handler
        pipeline.addLast("MyHttpServerHandler", new IHttpServerHandler());

    }

}
