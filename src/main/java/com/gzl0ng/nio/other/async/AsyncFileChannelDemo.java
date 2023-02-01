package com.gzl0ng.nio.other.async;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

/**
 * @author:郭正龙
 * @data:2022/12/14
 */
//TODO 异步通道使用
public class AsyncFileChannelDemo {

    //Future方式读取数据
    @Test
    public void test1() throws IOException {
       //1创建一个异步文件通道
        Path path = Paths.get("E:\\tmp\\gzl\\02.txt");
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);

        //2创建buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //3调用channel的read方法得到Future
        Future<Integer> future = fileChannel.read(buffer, 0);

        //4判断是否完成isDone,返回true
        while (!future.isDone());

        //5.读取数据到buffer
        buffer.flip();
//        while (buffer.remaining()>0){
//            System.out.println(buffer.get());
//        }
        byte[] data = new byte[buffer.limit()];
        buffer.get(data);
        System.out.println(new String(data));
        buffer.clear();
    }

    //CompletionHandler方式读取数据
    @Test
    public void test2() throws IOException {
        //异步通道
        Path path = Paths.get("E:\\tmp\\gzl\\02.txt");
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path,StandardOpenOption.READ);

        //创建buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //得到CompletionHandler
        fileChannel.read(buffer,0,buffer,new CompletionHandler<Integer,ByteBuffer>(){
            //读取成功调用这方法
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                System.out.println("rusult:" + result);

                buffer.flip();
//        while (buffer.remaining()>0){
//            System.out.println(buffer.get());
//        }
                byte[] data = new byte[buffer.limit()];
                buffer.get(data);
                System.out.println(new String(data));
                buffer.clear();
            }
            //调用失败调用这个方法
            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {

            }
        });
    }

    //Future方式写数据
    @Test
    public void test3() throws IOException {
        //异步通道
        Path path = Paths.get("E:\\tmp\\gzl\\02.txt");
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path,StandardOpenOption.WRITE);

        //buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        buffer.put("gzl".getBytes());
        buffer.flip();

        //future写数据
        Future<Integer> future = fileChannel.write(buffer, 0);

        //写完
        while (!future.isDone());
        buffer.clear();
        System.out.println("write over");
    }

    //CompletionHandler方式写数据
    @Test
    public void test4() throws IOException {
        //异步通道
        Path path = Paths.get("E:\\tmp\\gzl\\02.txt");
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path,StandardOpenOption.WRITE);

        //buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("gzl".getBytes());
        buffer.flip();

        //写入
        fileChannel.write(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                System.out.println("result");
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {

            }
        });
    }
}
