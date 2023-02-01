package com.gzl0ng.nio.other.zerocopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author:郭正龙
 * @data:2022/12/16
 */
public class OldIOClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 7001);

        String path = OldIOClient.class.getResource("/").getPath();
        path = "D:\\idea_work\\nio\\src\\main\\resources\\PantumM7300SeriesWindowsDriverV1.3.22_1644228971104.exe";

        FileInputStream inputStream = new FileInputStream(path);

        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        byte[] bytes = new byte[4096];
        long readCount;
        long total = 0;

        long startTime = System.currentTimeMillis();

        while ((readCount = inputStream.read(bytes))>=0){
            total+=readCount;
            dataOutputStream.write(bytes);
        }

        System.out.println("发送总字节数:" + total + "耗时:" + (System.currentTimeMillis()-startTime));

        dataOutputStream.close();
        socket.close();
        inputStream.close();
    }
}
