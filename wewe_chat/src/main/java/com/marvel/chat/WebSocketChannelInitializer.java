package com.marvel.chat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @Description WebSocketChannelInitializer
 * @Author wupeng
 * @Motto Stay Hungry, Stay Foolish !
 * @Date 2020/9/1 10:41 下午
 **/
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {

    //初始化通道，加载对应的ChannelHandler
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //获取管道，将一个个的ChannelHandler添加到管道中
        ChannelPipeline pipeline = socketChannel.pipeline();
        //添加一个http的编解码器
        pipeline.addLast(new HttpServerCodec());
        //添加一个用于大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());
        //添加一个聚合器，将HttpMessage 聚合成FullHttpRequest/Response
        pipeline.addLast(new HttpObjectAggregator(1024*64));
        //需要指定接收请求的路由，这里指定必须使用以ws后缀结尾的url才能访问
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        //添加自定义的Handler
        pipeline.addLast(new ChatHadler());

    }
}
