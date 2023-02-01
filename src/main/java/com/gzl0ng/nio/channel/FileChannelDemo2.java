package com.gzl0ng.nio.channel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

//FileChannel写操作
public class FileChannelDemo2 {
    public static void main(String[] args) throws IOException {

        String path = FileChannelDemo2.class.getResource("/").getPath();

        //1.打开FileChannel
        RandomAccessFile aFile = new RandomAccessFile(path+"001.txt","rw");
        FileChannel channel = aFile.getChannel();

        //2.创建buffer对象
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        String newData = "data gzl";
        buffer.clear();

        //3.写入内容
        buffer.put(newData.getBytes());
        buffer.flip();

        //4.FileChannel完成
        while (buffer.hasRemaining()){
             channel.write(buffer);
        }
        channel.close();

        System.out.println("传输完成");
    }
}
