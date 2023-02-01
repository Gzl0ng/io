package com.gzl0ng.nio.channel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelDemo1 {
    //通过FileChannel读取数据到buffer中
    public static void main(String[] args) throws IOException {
        String path = FileChannelDemo1.class.getResource("/").getPath();
        System.out.println(path);

        //1.创建一个FileChannel
        RandomAccessFile aFile = new RandomAccessFile(path+"01.txt","rw");
        FileChannel channel = aFile.getChannel();

        //2.创建一个buffer
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //3.读取数据到buffer中
        int bytesRead = channel.read(buf);
        while (bytesRead != -1){
            System.out.println("读取了" + bytesRead + "字节");
            buf.flip();
            while (buf.hasRemaining()){
                System.out.println((char) buf.get());
            }
            buf.clear();
            bytesRead = channel.read(buf);
        }

        aFile.close();
        System.out.println("结束了");
    }
}
