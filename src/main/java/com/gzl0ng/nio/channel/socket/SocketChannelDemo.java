package com.gzl0ng.nio.channel.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketChannelDemo {
    public static void main(String[] args) throws IOException {
        int port = 8888;

        //创建socketChannel方式1
//        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("www.baidu.com", port));//这里与baidu建立不了连接，无法演示效果
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(port));

        //方式2
//        SocketChannel socketChannel1 = SocketChannel.open();
//        socketChannel1.connect(new InetSocketAddress("www.baidu.com",port));

        //参数设置
        /**
         * SO_SNDBUF 套接字发送缓冲区大小
         * SO_RCVBUF 套接字接收缓冲区大小
         * SO_KEEPALIVE 保活连接
         * O_REUSEADDR 复用地址
         * SO_LINGER 有数据传输时延缓关闭 Channel (只有在非阻塞模式下有用)
         * TCP_NODELAY 禁用 Nagle 算法
         */
        socketChannel.setOption(StandardSocketOptions.SO_KEEPALIVE,Boolean.TRUE)
                .setOption(StandardSocketOptions.TCP_NODELAY,Boolean.TRUE);
//        socketChannel.getOption(StandardSocketOptions.SO_KEEPALIVE);

        //阻塞模式设置
        socketChannel.configureBlocking(false);

        //读操作
        ByteBuffer buf = ByteBuffer.allocate(16);
        socketChannel.read(buf);
        socketChannel.close();
        System.out.println("read over");

        //socket状态
        /**
         * 1.管道是否open
         * 2.管道是否连接
         * 3.管道是否正在连接
         * 4.校验正在套接字连接的管道是否完成连接,关闭的管道执行这个会报错
         */
//        boolean openStatus = socketChannel.isOpen();
//        boolean connectedStatus = socketChannel.isConnected();
//        boolean connectionPendingStatus = socketChannel.isConnectionPending();
//        boolean finishConnectStatus = socketChannel.finishConnect();
//
//        System.out.println(openStatus);
//        System.out.println(connectedStatus);
//        System.out.println(connectionPendingStatus);
//        System.out.println(finishConnectStatus);
    }
}
