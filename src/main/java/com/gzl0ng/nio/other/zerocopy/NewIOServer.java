package com.gzl0ng.nio.other.zerocopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author:郭正龙
 * @data:2022/12/16
 */
//服务器端
public class NewIOServer {
    public static void main(String[] args) throws IOException {
        InetSocketAddress socketAddress = new InetSocketAddress(7001);
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(socketAddress);

        //创建buffer
        ByteBuffer buffer = ByteBuffer.allocate(4096);

        while (true){
            SocketChannel socketChannel = serverSocketChannel.accept();

            int readCount = 0;
            while (-1 != readCount){
                try {
                    readCount = socketChannel.read(buffer);
                }catch (Exception e){
                    e.printStackTrace();
                }
                //倒带 position = 0 mark 作废
                buffer.rewind();
            }
        }
    }
}
