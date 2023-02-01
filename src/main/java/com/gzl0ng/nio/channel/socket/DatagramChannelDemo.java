package com.gzl0ng.nio.channel.socket;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;

public class DatagramChannelDemo {
    //发送包的实现
    @Test
    public void sendDatagram() throws IOException, InterruptedException {
        //打开连接
        DatagramChannel sendChannel = DatagramChannel.open();
        InetSocketAddress sendAddress = new InetSocketAddress("127.0.0.1",9999);

        //发送
        while (true){
            ByteBuffer buffer = ByteBuffer.wrap("hello world".getBytes("UTF-8"));
            sendChannel.send(buffer,sendAddress);

            System.out.println("已经完成发送");
            Thread.sleep(2000);
        }
    }

    //接收的实现
    @Test
    public void receiveDatagram() throws IOException {
        //打开DatagramChannel
        DatagramChannel receiveChannel = DatagramChannel.open();

        InetSocketAddress reciveAddress = new InetSocketAddress(9999);

        //绑定
        receiveChannel.bind(reciveAddress);

        //buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //接收
        while (true){
            System.out.println("开始接收");
            buffer.clear();
            SocketAddress soketAddress = receiveChannel.receive(buffer);

            buffer.flip();

            System.out.println(soketAddress.toString());
            System.out.println(Charset.forName("UTF-8").decode(buffer));
        }
    }

    //连接read 和write
    @Test
    public void testConnect() throws IOException {
        //打开DatagramChannel
        DatagramChannel connChannel = DatagramChannel.open();
        InetSocketAddress connAddress = new InetSocketAddress("127.0.0.1",9999);

        //绑定
        connChannel.bind(connAddress);

        //连接
        connChannel.connect(connAddress);

        //write方法
        connChannel.write(ByteBuffer.wrap("hello world".getBytes("UTF-8")));

        //read方法
        ByteBuffer buf = ByteBuffer.allocate(1024);
        while (true){
            buf.clear();
            connChannel.read(buf);
            buf.flip();
            System.out.println(Charset.forName("UTF-8").decode(buf));
        }
    }

}
