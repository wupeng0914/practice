package com.example.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * @Description IHttpServerHandler
 * @Author wupeng
 * @Motto Stay Hungry, Stay Foolish !
 * @Date 2020/10/26 5:42 下午
 **/
public class IHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    //读取客户端数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpRequest){
            System.out.println("pipeline hashcode " + ctx.pipeline().hashCode() + " IHttpServerHandler hash = " + this.hashCode());

            System.out.println("msg 类型="+msg.getClass());

            System.out.println("客户端地址"+ctx.channel().remoteAddress());

        }

        HttpRequest httpRequest = (HttpRequest) msg;
        //获取uri，过滤指定的资源
        URI uri = new URI(httpRequest.uri());
        if ("/favicon.ico".equals(uri.getPath())){
            System.out.println("请求了favicon.ico, 不做响应");
            return;
        }

        //回复消息给浏览器 [http协议]
        ByteBuf content = Unpooled.copiedBuffer("Hello，我是服务器", CharsetUtil.UTF_8);

        //构造一个http响应，即httpresponse
        DefaultFullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
        httpRequest.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
        httpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

        //响应结果
        ctx.writeAndFlush(httpResponse);
    }
}
