package com.example.nio.channel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Description NIOFIleChannel
 * @Author wupeng
 * @Motto Stay Hungry, Stay Foolish !
 * @Date 2020/10/22 4:41 下午
 **/
public class NIOFIleChannel {


    public static void main(String[] args) throws Exception {
//        write();
//        read();
//        copy1();
        copy2();
    }

    private static void copy1() throws Exception{
        FileInputStream inputStream = new FileInputStream("./file.txt");
        FileChannel readChannel = inputStream.getChannel();

        FileOutputStream outputStream = new FileOutputStream("./file_copy.txt");
        FileChannel writeChannel = outputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        while (true){
            byteBuffer.clear();// 这一步很重要
            int read = readChannel.read(byteBuffer);
            System.out.println("read: " + read);
            if (read == -1){
                break;
            }
            byteBuffer.flip();
            writeChannel.write(byteBuffer);
        }

        inputStream.close();
        outputStream.close();

    }

    private static void copy2() throws Exception{
        FileInputStream inputStream = new FileInputStream("./file.txt");
        FileOutputStream outputStream = new FileOutputStream("./file_copy2.txt");

        FileChannel readChannel = inputStream.getChannel();
        FileChannel writeChannel = outputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        writeChannel.transferFrom(readChannel, 0, readChannel.size());

        readChannel.close();
        writeChannel.close();
        inputStream.close();
        outputStream.close();

    }

    private static void read() throws Exception {
        File file = new File("./file.txt");
        FileInputStream inputStream = new FileInputStream(file);
        FileChannel channel = inputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate((int)file.length());

        channel.read(byteBuffer);

        System.out.println(new String(byteBuffer.array()));

        inputStream.close();

    }

    private static void write() throws Exception{
        String str = "我和我的祖国！";
        FileOutputStream outputStream = new FileOutputStream("./file.txt");
        //通过输出流获取相应的FileChannel
        FileChannel channel = outputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes());

        byteBuffer.flip();

        channel.write(byteBuffer);
        outputStream.close();
    }

}
