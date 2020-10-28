package com.example.netty.group_chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description GroupChatServerHandler
 * @Author wupeng
 * @Motto Stay Hungry, Stay Foolish !
 * @Date 2020/10/27 11:17 上午
 **/
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * handlerAdded表示连接建立，一旦建立连接，该方法会第一个被执行
     * 将channel加入到ChannelGroup
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //将该客户端加入的消息发送给其他客户端
        //ChannelGroup的writeAndFlush() 方法，会遍历channelGroup中的所有的channel，并发送消息
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + "加入聊天" + dateFormat.format(new Date()) + "\n");
        channelGroup.add(channel);
    }

    /**
     * 断开连接时会执行的方法，客户端离开，推送离开信息给其他客户端
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客户端]"+channel.remoteAddress()+"离开了");
        System.out.println("当前在线人数："+channelGroup.size());
    }

    /**
     * channel处于活动状态，提示xxx上线
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "上线了~~");
        System.out.println("当前在线人数："+channelGroup.size());
    }

    /**
     * 读取数据
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();

        channelGroup.forEach(ch -> {
            if (!ch.equals(channel)){// 判断不是当前channel自身，则转发消息
                ch.writeAndFlush("[客户]" + channel.remoteAddress() + "：" + msg + "\n");
            } else {//是自己则回显自己发送的消息
                channel.writeAndFlush("我自己：" + msg + "\n");
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
