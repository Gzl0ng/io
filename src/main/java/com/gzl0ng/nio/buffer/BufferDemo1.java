package com.gzl0ng.nio.buffer;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

/**
 * @author:郭正龙
 * @data:2022/12/6
 */
public class BufferDemo1 {

    @Test
    public void buffer01() throws IOException {
        String path = BufferDemo1.class.getResource("/").getPath();

        //FileChannel
        RandomAccessFile aFile = new RandomAccessFile(path + "01.txt", "rw");
        FileChannel channel = aFile.getChannel();

        //创建buffer,指定大小
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //读
        int byteRead = channel.read(buffer);
        while (byteRead != -1){
            //read模式
            buffer.flip();

            while (buffer.hasRemaining()){
                System.out.println((char) buffer.get());
            }
            buffer.clear();
            byteRead = channel.read(buffer);
        }

        aFile.close();
    }

    @Test
    public void buffer02(){
        //创建buffer,指定大小
        IntBuffer buffer = IntBuffer.allocate(8);

        //buffer放
        for (int i = 0; i < buffer.capacity(); i++) {
            int j = 2*(i+1);
            buffer.put(j);
        }

        //重置缓冲区
        buffer.flip();

        //获取
        while (buffer.hasRemaining()){
            int value = buffer.get();
            System.out.println(value+" ");
        }
    }
}
