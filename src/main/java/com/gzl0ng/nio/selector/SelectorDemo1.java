package com.gzl0ng.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author:郭正龙
 * @data:2022/12/7
 */
/*
selector操作流程
 */
public class SelectorDemo1 {
    public static void main(String[] args) throws IOException {

        //创建selector
        Selector selector = Selector.open();

        //通道
        ServerSocketChannel socketChannel = ServerSocketChannel.open();

        //非阻塞
        socketChannel.configureBlocking(false);

        //绑定连接
        socketChannel.bind(new InetSocketAddress(9999));

        //通道注册到选择器上
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //查询已经就绪通道操作
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        //遍历集合
        Iterator<SelectionKey> iterator = selectionKeys.iterator();
        while (iterator.hasNext()){
            SelectionKey next = iterator.next();
            //判断key的就绪状态操作
            if (next.isAcceptable()){
                
            }else if (next.isConnectable()){
                
            } else if (next.isReadable()) {
                
            } else if (next.isWritable()) {

            }
            iterator.remove();
        }
    }
}
