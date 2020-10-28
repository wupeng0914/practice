package com.example.nio.group_chat;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @Description GroupChatServer
 * @Author wupeng
 * @Motto Stay Hungry, Stay Foolish !
 * @Date 2020/10/23 3:24 下午
 **/
public class GroupChatServer {

    private Selector selector;

    private ServerSocketChannel listenChannel;

    private static final int PORT = 8888;

    public GroupChatServer(){
        try {
            selector = Selector.open();
            listenChannel = ServerSocketChannel.open();
            //绑定端口
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            //设置非阻塞模式
            listenChannel.configureBlocking(false);
            //将该listenChannel注册到selector
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void listen() {
        while (true){
            try {
                int count = selector.select();
                if (count > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        //监听到accept事件处理
                        if (key.isAcceptable()) {
                            SocketChannel socketChannel = listenChannel.accept();
                            //设置非阻塞模式
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            //上线提示
                            System.out.println(socketChannel.getRemoteAddress() + "上线");
                        }
                        //监听到可读事件处理
                        if (key.isReadable()) {
                            doReadData(key);
                        }
                        //删除当前key，防止服务端重复操作
                        iterator.remove();
                    }
                } else {
                    System.out.println("WATTING....");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void doReadData(SelectionKey key) {
        //反向获取到关联的channel
        SocketChannel channel = null;
        try {
            //得到channel
            channel = (SocketChannel) key.channel();
            //创建buffer
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            int readLength = channel.read(byteBuffer);
            if (readLength > 0){
                String msg = new String(byteBuffer.array());
                //消息打印
                System.out.println("FROM CLIENT: " + msg);
                //向其他除了自己的客户端转发消息
                sendToOtherClient(msg, channel);
            }
        } catch (Exception e){
            try {
                System.out.println(channel.getRemoteAddress() + "下线了");
                //注销注册
                key.cancel();
                //关闭通道
                channel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    //转发消息给其他客户
    private void sendToOtherClient(String msg, SocketChannel channel) throws IOException {
        System.out.println("服务器转发消息中。。。");
        for (SelectionKey key : selector.keys()){
            Channel targetChannel = key.channel();
            if (targetChannel instanceof SocketChannel && targetChannel != channel){
                SocketChannel dest = (SocketChannel) targetChannel;
                //将msg存储到buffer中
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                //将buffer中的数据写入到通道中
                dest.write(buffer);
            }
        }
    }

    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }
}
