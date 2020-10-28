package com.example.nio.socket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @Description NIOServer
 * @Author wupeng
 * @Motto Stay Hungry, Stay Foolish !
 * @Date 2020/10/22 6:04 下午
 **/
public class NIOServer {
    public static void main(String[] args) throws Exception{
        //创建ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //得到一个Selector对象
        Selector selector = Selector.open();

        //绑定一个指定端口6666，在服务端监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));

        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        //注册serverSocketChannel到selector，关注事件为OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 循环等待客户端连接
        System.out.println("等待连接。。。");
        while (true){
            // 等待1秒，如果未接收到相关事件，则返回继续
            if (selector.select(1000) == 0){
                continue;
            }

            //如果返回值大于0，表示获取到相关的selectionKey集合
            //1、返回值大于0，表示已获取到关注的事件；
            //2、selector.selectedKeys()方法返回所获取到的关注事件的集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            //遍历SelectionKey集合，对不同事件做不同处理
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();

                //如果是OP_ACCEPT事件，则表示有新的客户端连接
                if (selectionKey.isAcceptable()){
                    //服务端接收新的客户端连接，为新的客户端生成一个SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("客户端连接成功，建立新的socketChannel：" + socketChannel.hashCode());
                    //设置客户端连接socketChannel为非阻塞
                    socketChannel.configureBlocking(false);
                    //将socketChannel注册到selector,关注事件为OP_READ,同时给socketChannel
                    //关联一个buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }

                // 可读事件处理
                if (selectionKey.isReadable()){
                    //通过selectionKey反向获取到对应的channel
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    //获取到该channel关联的buffer
                    ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
                    channel.read(buffer);
                    System.out.println("FROM CLIENT:" + new String(buffer.array()));
                    //手动从集合中移除当前的selectionKey，防止重复操作
                }
                iterator.remove();
            }


        }
    }
}
