package com.marvel.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description ChatHadler
 * @Author wupeng
 * @Motto Stay Hungry, Stay Foolish !
 * @Date 2020/9/1 11:00 下午
 **/
public class ChatHadler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");

    //当Channel中有新的事件消息时，会自动调用该方法
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame text) throws Exception {
        //获取客户端发送过来的文本消息
        String msg = text.text();
        System.out.println("接收到的消息为："+msg);

        for (Channel client : clients){
            client.writeAndFlush(new TextWebSocketFrame(dateFormat.format(new Date()) + ": " + msg));
        }
    }

    //当有新的客户端连接服务器之后会自动调用该方法
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //将新的连接通道加入到clients
        clients.add(ctx.channel());
    }
}
