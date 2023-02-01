package com.gzl0ng.nio.selector;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * @author:郭正龙
 * @data:2022/12/7
 */
public class SelectorDemo2 {

    //客户端
    @Test
    public void clientDemo() throws IOException {
        //1.获取通道，绑定主机和端口号
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(9999));

        //2.切换非阻塞模式
        socketChannel.configureBlocking(false);

        //3.创建buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //4.写入数据
//        buffer.put("hello".getBytes("UTF-8"));
        buffer.put(new Date().toString().getBytes());

        //5.切换模式
        buffer.flip();

        //6.写入通道
        socketChannel.write(buffer);

        //7.关闭
        buffer.clear();
        socketChannel.close();
    }

    //服务端
    @Test
    public void serverDemo() throws IOException {
        //1.选择服务通道
        ServerSocketChannel socketChannel = ServerSocketChannel.open();

        //2.切换非阻塞模式
        socketChannel.configureBlocking(false);

        //3.创建buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //4.绑定端口号
        socketChannel.bind(new InetSocketAddress(9999));

        //5.获取selector选择器
        Selector selector = Selector.open();

        //6.通道注册到选择器
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //7.通道注册到选择器,进行监听
        while (selector.select()>0){
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //遍历
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                //获取就绪操作
                SelectionKey next = iterator.next();
                //判断什么操作
                if (next.isAcceptable()){
                    //获取连接
                    SocketChannel accept = socketChannel.accept();

                    //切换非阻塞模式
                    accept.configureBlocking(false);

                    //注册
                    accept.register(selector,SelectionKey.OP_READ);

                } else if (next.isWritable()) {

                } else if (next.isReadable()) {
                    SocketChannel channel = (SocketChannel) next.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

                    //读取数据
                    int length = 0;
                    while ((length = channel.read(buffer))>0){
                        byteBuffer.flip();
                        System.out.println(new String(byteBuffer.array(),0,length));
                    }
                    byteBuffer.clear();
                } else if (next.isConnectable()) {

                }
                iterator.remove();
            }
        }

        //8.选择器进行沦陷，进行后续操作
    }

    //客户端
    public static void main(String[] args) throws IOException {
        //1.获取通道，绑定主机和端口号
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(9999));

        //2.切换非阻塞模式
        socketChannel.configureBlocking(false);

        //3.创建buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String str = scanner.next();

            //4.写入数据
//        buffer.put("hello".getBytes("UTF-8"));
            buffer.put((new Date().toString()+ "--->" + str).getBytes(Charset.forName("UTF-8")));

            //5.切换模式
            buffer.flip();

            //6.写入通道
            socketChannel.write(buffer);

            //7.关闭
            buffer.clear();
        }

    }


    @Test
    public void test(){
        System.out.println(new Date().toString());
    }
}
