package com.gzl0ng.netty.inouthandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author:郭正龙
 * @data:2023/1/3
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //加入一个出战的handler 对数据进行编码
        pipeline.addLast(new MyLongToByteEncoder());

        //入站的解码器
//        pipeline.addLast(new MyByteToLongDecoder());
        pipeline.addLast(new MyByteToLongDecoder2());

        //加入一个自定义handler处理自定义逻辑
        pipeline.addLast(new MyClientHandler());
    }
}
