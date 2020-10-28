package com.example.nio.group_chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @Description GroupChatClient
 * @Author wupeng
 * @Motto Stay Hungry, Stay Foolish !
 * @Date 2020/10/23 4:02 下午
 **/
public class GroupChatClient {

    private final String HOST = "127.0.0.1";
    private final int PORT = 8888;
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    public GroupChatClient() throws IOException {
        selector = Selector.open();
        //连接服务器
        socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
        //设置非阻塞
        socketChannel.configureBlocking(false);
        //将channel注册到selector
        socketChannel.register(selector, SelectionKey.OP_READ);
        //得到username
        username = socketChannel.getLocalAddress().toString().substring(1);
        System.out.println(username + " has connected to server !");
    }

    //发送消息到服务端
    public void sendMsg(String msg){
        msg = username + " : " + msg;
        try {
            socketChannel.write(ByteBuffer.wrap(msg.getBytes()));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //接收消息
    public void readMsg(){
        try {
            int count = selector.select();
            if (count > 0){
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    if (key.isReadable()){
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        //读取消息数据到buffer中
                        socketChannel.read(buffer);

                        String msg = new String(buffer.array());
                        System.out.println(msg.trim());
                    }
                }
                iterator.remove();
            } else {
                System.out.println("暂时没有可用的通道...");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        final GroupChatClient client = new GroupChatClient();
        //启动一个新的线程，每隔3秒从服务端读取一次消息
        new Thread(){
            @Override
            public void run() {
                while (true){
                    client.readMsg();
                    try {
                        TimeUnit.MILLISECONDS.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        //消息输入
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String msg = scanner.nextLine();
            client.sendMsg(msg);
        }

    }

}
