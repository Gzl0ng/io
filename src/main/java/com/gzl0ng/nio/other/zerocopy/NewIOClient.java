package com.gzl0ng.nio.other.zerocopy;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @author:郭正龙
 * @data:2022/12/16
 */
public class NewIOClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(7001));
        String path = "D:\\idea_work\\nio\\src\\main\\resources\\PantumM7300SeriesWindowsDriverV1.3.22_1644228971104.exe";

        //得到一个文件channel
        FileChannel fileChannel = new FileInputStream(path).getChannel();

        //准备发送
        long startTime = System.currentTimeMillis();
        //在linux下一个transferTo 方法就可以完成传输
        //在windows下一次调用transferTo只能发送8m文件,就需要分段传输文件,而且要注意传输时的位置
        //transferTo底层使用到零拷贝
//        long transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        long transferCount = 0 ;
        long start = 0;
        if (fileChannel.size() % (1024 * 8) == 0){
            for (int i = 0; i <= fileChannel.size() / (1024 * 8); i++) {
                transferCount += fileChannel.transferTo(transferCount, 1024*1024 * 8, socketChannel);
            }
        }else {
            for (int i = 0; i <= fileChannel.size() / (1024 * 8); i++) {
                if (i< fileChannel.size() / (1024 * 8) -1){
                    transferCount += fileChannel.transferTo(transferCount, 1024 * 8, socketChannel);
                }else {
                    transferCount += fileChannel.transferTo(transferCount,fileChannel.size() % (1024 * 8) , socketChannel);
                }
            }
        }


        System.out.println("发送总字节数:" + transferCount + "耗时:" + (System.currentTimeMillis()-startTime));

        //关闭
        fileChannel.close();
    }

    //19572192
    @Test
    public void test(){
        int s = 19572192%(8*1024*1024);
        System.out.println(s);
    }
}
