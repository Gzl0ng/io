package com.gzl0ng.netty.protocoltcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author:郭正龙
 * @data:2023/1/5
 */
/*
编码5次发送数据
MyClientHandler先被调用（入站）
MyMessageEncoder后被调用（出战）

解码五次接收数据
MyMessageDecoder被调用（入站）
MyClientHandler先被调用（出站）
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new MyMessageEncoder());//加入编码器
        pipeline.addLast(new MyMessageDecoder());//加入解码器
        pipeline.addLast(new MyClientHandler());
    }
}
