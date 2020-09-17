package com.marvel.chat.server;

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
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description ChatHadler 处理消息的Handler
 * TextWebSocketFrame：在netty中，是用于为websocket专门处理文本的对象，frame是消息的载体
 * @Author wupeng
 * @Motto Stay Hungry, Stay Foolish !
 * @Date 2020/9/1 11:00 下午
 **/
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    //用于记录和管理所有客户端的Channel
    private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
//    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //当Channel中有新的事件消息时，会自动调用该方法
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame text) throws Exception {
        //获取客户端发送过来的文本消息
        String msg = text.text();
        System.out.println("接收到的数据为："+msg);
        //将接收到的消息发送到所有客户端
        for (Channel client : clients){
            //注意所有的websocket数据都应该以TextWebSocketFrame进行封装
            client.writeAndFlush(new TextWebSocketFrame("[服务器接收到消息：]" + LocalDateTime.now() + ",消息为： " + msg));
        }
    }

    /**
     * 当有新的客户端连接服务器之后会自动调用该方法
     * 获取客户端的channel，并且放入到ChannelGroup中去进行管理
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //将新的连接通道加入到clients
        clients.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //当触发handlerRemoved,ChannelGroup会自动移除对应客户端的channel
        //clients.remover(ctx.channel());
        //asLongText() 唯一ID
        //asShortText() 短ID（有可能会重复）
        System.out.println("客户端断开，channel对应的长ID为：" + ctx.channel().id().asLongText());
        System.out.println("客户端断开，channel对应的短ID为：" + ctx.channel().id().asShortText());
    }
}
