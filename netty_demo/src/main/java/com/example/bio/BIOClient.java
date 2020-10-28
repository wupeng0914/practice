package com.example.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Description BIOClient
 * @Author wupeng
 * @Motto Stay Hungry, Stay Foolish !
 * @Date 2020/10/22 11:05 上午
 **/
public class BIOClient {

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("127.0.0.1", 6666);

        InputStream inputStream = socket.getInputStream();

        OutputStream outputStream = socket.getOutputStream();

        Scanner scanner = new Scanner(System.in);
        byte[] bytes = new byte[1024];
        String msg = null;
        while (true){
            int read = inputStream.read(bytes);
            if (read != -1){
                System.out.println("server: " + new String(bytes, 0  , read));
            }
            msg = scanner.next();
            outputStream.write(msg.getBytes());
            outputStream.flush();
            if (msg.equals("bb"))
                break;
        }
        scanner.close();
        outputStream.close();

    }


}
