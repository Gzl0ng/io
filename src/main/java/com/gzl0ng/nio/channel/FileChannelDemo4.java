package com.gzl0ng.nio.channel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

//通道之间数据传输
public class FileChannelDemo4 {
    public static void main(String[] args) throws IOException {
        String path = FileChannelDemo4.class.getResource("/").getPath();

        //
        RandomAccessFile aFile = new RandomAccessFile(path + "", "rw");
        RandomAccessFile bFile = new RandomAccessFile(path + "", "rw");;

        FileChannel fromChannel = aFile.getChannel();
        FileChannel toChannel = bFile.getChannel();

        //
        int position = 0;
        long size = fromChannel.size();
        fromChannel.transferTo(position,size,toChannel);

        //
        aFile.close();
        bFile.close();
    }
}
