package com.marvel.chat.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @Description WebSocketServerInitializer WebSocket通道初始化器
 * @Author wupeng
 * @Motto Stay Hungry, Stay Foolish !
 * @Date 2020/9/15 4:47 下午
 **/
public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        /***************支持Http协议**************/
        //webSocket 基于http协议，需要有http的编解码器
        pipeline.addLast(new HttpServerCodec());
        //对写大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());
        //添加对HTTP请求和响应的聚合器：只需要使用Netty进行Http编程都需要使用
        //对HTTPMessage进行聚合，聚合成FullHttpRequest 或者FullHttpResponse
        //在netty编程中都会使用到Handler
        pipeline.addLast(new HttpObjectAggregator(1024*64));

        /***************支持WebSocket**************/
        //websocket服务器处理的协议，用于指定给客户端连接访问的路由: /ws
        //此handler会帮助处理一些握手动作：shaking(close,ping,pong) ping+pong = 心跳
        //对于websocket来讲，都是以frames进行传输的，不同的数据类型对应的frames也不同
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        //添加自定义的handler
        pipeline.addLast(new ChatHandler());

    }
}
