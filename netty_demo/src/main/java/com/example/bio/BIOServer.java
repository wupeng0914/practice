package com.example.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description BIOServer
 * @Author wupeng
 * @Motto Stay Hungry, Stay Foolish !
 * @Date 2020/10/22 10:52 上午
 **/
public class BIOServer {

    public static void main(String[] args) throws IOException {
        //使用线程池：
        //1、创建一个线程池；2、如果有客户端连接，就创建一个线程与之通讯
        ExecutorService threadPool = Executors.newCachedThreadPool();
        //创建ServerSocket
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务端启动。。。");
        while (true){
            System.out.println("线程信息：id="+Thread.currentThread().getId() + " 名字="+Thread.currentThread().getName());
            //阻塞监听，等待客户端连接
            final Socket socket = serverSocket.accept();
            System.out.println("新的客户端已连接");
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write("Welcome:".getBytes());
            //创建一个线程与客户端通讯
            threadPool.execute(new Runnable() {
                public void run() {
                    handler(socket);
                }
            });
        }
    }

    private static void handler(Socket socket) {
        try {
            byte[] bytes = new byte[1024];
            // 通过socket获取输入流
            InputStream inputStream = socket.getInputStream();
            // 循环读取客户端数据
            while (true){
                int readlen = inputStream.read(bytes);
                if (readlen != -1){
                    //打印客户端数据
                    System.out.println("client:" + new String(bytes, 0, readlen));
                } else {
                    break;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            System.out.println("关闭client连接");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
