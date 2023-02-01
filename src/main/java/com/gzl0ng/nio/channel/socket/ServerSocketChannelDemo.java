package com.gzl0ng.nio.channel.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;


//在页面访问127.0.0.1:8888
//输出二次Incoming connection from /127.0.0.1:61723
public class ServerSocketChannelDemo {
    public static void main(String[] args) throws IOException, InterruptedException {
        //端口号设置
        int port = 8888;

        //buffer
        ByteBuffer buf = ByteBuffer.wrap("hello world".getBytes());

        //打开ServerSocketChannel
        ServerSocketChannel ssc = ServerSocketChannel.open();
        //绑定
        ssc.socket().bind(new InetSocketAddress(port));

        //设置非阻塞模式
        ssc.configureBlocking(false);

        //监听是否有新链接传入
        while (true){
            System.out.println("Waiting for connection");
            SocketChannel sc = ssc.accept();//如果设置阻塞模式：只有链接来了才往下面执行
            if (sc == null){//没有链接传入
                System.out.println("null");
                Thread.sleep(2000);
            }else {
                System.out.println("Incoming connection from "+
                        sc.socket().getRemoteSocketAddress());
                buf.rewind();//指针设为0
                sc.write(buf);
                sc.close();
            }
        }
    }
}
