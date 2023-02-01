package com.gzl0ng.nio.channel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

//通道之间数据传输
public class FileChannelDemo3 {
    public static void main(String[] args) throws IOException {
        String path = FileChannelDemo3.class.getResource("/").getPath();
        System.out.println(path);
        //
        RandomAccessFile aFile1 = new RandomAccessFile(path + "02.txt", "rw");
        RandomAccessFile aFile2 = new RandomAccessFile(path + "03.txt", "rw");

        FileChannel fromChannel = aFile1.getChannel();
        FileChannel toChannel = aFile2.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(1024);


        //fromChannel传输到toChannel
        long position = 0;
        long size = fromChannel.size();
        toChannel.transferFrom(fromChannel,position,size);

        fromChannel.read(buf);
        buf.flip();
        toChannel.write(buf);

        aFile1.close();
        aFile2.close();
        System.out.println("over");
    }
}
