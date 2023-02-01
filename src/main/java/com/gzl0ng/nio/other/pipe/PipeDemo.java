package com.gzl0ng.nio.other.pipe;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * @author:郭正龙
 * @data:2022/12/7
 */
/*
只能从sink往source传;(source没有write方法，sink没有read方法)
 */
public class PipeDemo {
    public static void main(String[] args) throws IOException {
        //1.获取管道
        Pipe pipe = Pipe.open();

        //2.获取sink通道
        Pipe.SinkChannel sinkChannel = pipe.sink();

        //3.创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("hello".getBytes());
        buffer.flip();

        //4.写入数据
        sinkChannel.write(buffer);

        //5.获取source通道
        Pipe.SourceChannel sourceChannel = pipe.source();

        //6.创建缓冲区，读取数据
        ByteBuffer buffer2 = ByteBuffer.allocate(1024);
//        buffer2.flip();
        int length = sourceChannel.read(buffer2);

        System.out.println(new String(buffer2.array(),0,length));

        //7.通道关闭
        sourceChannel.close();
        sinkChannel.close();
    }
}
