package com.gzl0ng.nio.other.chat.client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * @author:郭正龙
 * @data:2022/12/15
 */
public class ClientThread implements Runnable{
    private Selector selector;

    public ClientThread(Selector selector) {
        this.selector = selector;
    }

    @Override
    public void run() {
        //5.循环等待新连接接入
//        while (true)
        for (;;) {
            //获取channel数量
            int readChannels = 0;
            try {
                readChannels = selector.select();
                if (readChannels == 0) {
                    continue;
                }
                //获取可用的channel
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                //遍历集合
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();

                    //移除set集合当前selectionKey
                    iterator.remove();

                    //6.根据就绪状态,调用对应方法实现具体业务操作
                    //6.1如果accept状态
                    //6.2如果可读状态
                    if (selectionKey.isReadable()) {
                        readOperator(selector, selectionKey);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //TODO 处理可读状态操作
    private void readOperator(Selector selector, SelectionKey selectionKey) throws IOException {
        //1.从SelectionKey获取到已经就绪的通道
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        //2.创建buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //3.循环读取客户端消息
        String message ="";
        while (socketChannel.read(buffer)>0){
            //切换到读模式
            buffer.flip();

            //读取内容
            message += Charset.forName("UTF-8").decode(buffer);
        }
        //4.将channel注册到选择器上,监听可读状态
        socketChannel.register(selector,SelectionKey.OP_READ);
        //5.把客户端发送的消息，广播到其它客户端
        if (message.length()>0){
            //广播给其它客户端
            System.out.println(message);
//            castOtherClient(message,selector,socketChannel);
        }
    }
}
