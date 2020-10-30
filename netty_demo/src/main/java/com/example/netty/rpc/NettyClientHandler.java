package com.example.netty.rpc;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 * @Description NettyClientHandler
 * @Author wupeng
 * @Motto Stay Hungry, Stay Foolish !
 * @Date 2020/10/28 7:28 下午
 **/
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    private ChannelHandlerContext context;//上下文
    private String result;//返回的结果
    private String para;//客户端调用方法时，传入的参数

    // 与服务器创建连接后，便会调用该方法
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive 被调用 ");
        context = ctx;
    }

    //接收到服务器的数据后，调用该方法
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead 被调用 ");
        result = msg.toString();
        notify();//唤醒等待的线程

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    //被代理对象调用，发送数据给服务器，-> wait -> 等待被唤醒(channelRead) -> 返回结果
    @Override
    public synchronized Object call() throws Exception {
        System.out.println("call 被调用");
        context.writeAndFlush(para);
        //进行wait
        wait();//等待channelRead方法获取到服务器的结果后，唤醒
        System.out.println("call2 被调用");
        return result;//服务方返回的结果
    }

    void setPara(String para){
        System.out.println("setPara");
        this.para = para;
    }
}
