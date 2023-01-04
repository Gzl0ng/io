package com.gzl0ng.netty.inouthandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author:郭正龙
 * @data:2023/1/3
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();//下断点

        //入站的handler进行解码 MyByteToLongDecoder
//        pipeline.addLast(new MyByteToLongDecoder());
        pipeline.addLast(new MyByteToLongDecoder2());

        //出站的编码器
        pipeline.addLast(new MyLongToByteEncoder());

        //自定义handler处理 业务逻辑
        pipeline.addLast(new MyServerHandler());
    }
}
