package com.gzl0ng.nio.other.chat.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * @author:郭正龙
 * @data:2022/12/14
 */
//服务器端
public class ChatServer {
    //服务器段启动方法
    public void startServer() throws IOException {
        //1.创建Selector选择器
        Selector selector = Selector.open();

        //2.创建ServerSocketChannel通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //3.为channel通道绑定监听端口
        serverSocketChannel.bind(new InetSocketAddress(8000));
        //设置非阻塞模式
        serverSocketChannel.configureBlocking(false);

        //4.1把channel注册到选择器上
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务器已经启动成功了");

        //5.循环等待新连接接入
//        while (true)
        for (;;){
            System.out.println("监听线程:" + Thread.currentThread().getName());
            //获取channel数量
            int readChannels = selector.select();

            if (readChannels == 0){
                continue;
            }
            //获取可用的channel
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //遍历集合
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();

                //移除set集合当前selectionKey
                iterator.remove();

                //6.根据就绪状态,调用对应方法实现具体业务操作
                //6.1如果accept状态
                if (selectionKey.isAcceptable()){
                    acceptOperator(serverSocketChannel,selector);
                }
                //6.2如果可读状态
                if (selectionKey.isReadable()){
                    readOperator(selector,selectionKey);
                }
            }
        }
    }

    //TODO 处理可读状态操作
    private void readOperator(Selector selector, SelectionKey selectionKey) throws IOException {
        //1.从SelectionKey获取到已经就绪的通道
        SocketChannel  socketChannel = (SocketChannel) selectionKey.channel();
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
            System.out.println("转发线程:" + Thread.currentThread().getName());
            //广播给其它客户端
            System.out.println(message);
            castOtherClient(message,selector,socketChannel);
        }
    }

    //TODO 广播给其他客户端
    private void castOtherClient(String message, Selector selector, SocketChannel socketChannel) throws IOException {
        //1.获取所有已经接入客户端
        Set<SelectionKey> selectionKeySet = selector.keys();

        //2.循环向所有channel广播消息
        for (SelectionKey selectionKey : selectionKeySet) {
            //获取每个channel
            Channel targetChannel = selectionKey.channel();
            //不需要给自己发送
            if (targetChannel instanceof SocketChannel && targetChannel != socketChannel){
                ((SocketChannel) targetChannel).write(StandardCharsets.UTF_8.encode(message));
            }
        }
    }

    //TODO 处理接入状态操作
    private void acceptOperator(ServerSocketChannel serverSocketChannel, Selector selector) throws IOException {
        //1.接入状态，创建socketChannel
        SocketChannel socketChannel = serverSocketChannel.accept();

        //2.把socketChannel设置为非阻塞模式
        socketChannel.configureBlocking(false);

        //3.把channel注册到selector选择器上，监听可读状态
        socketChannel.register(selector,SelectionKey.OP_READ);

        //4.客户端回复信息
        socketChannel.write(Charset.forName("UTF-8")
                .encode("欢迎进入聊天室，注意隐私安全"));
    }

    //启动主方法
    public static void main(String[] args) {
        try {
            new ChatServer().startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
