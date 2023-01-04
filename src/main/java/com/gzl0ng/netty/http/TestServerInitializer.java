package com.gzl0ng.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author:郭正龙
 * @data:2022/12/26
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //向管道加入处理器

        //得到管道
        ChannelPipeline pipeline = ch.pipeline();

        //加入netty提供的httpServerCodec  codec => [coder -decoder]
        pipeline.addLast("myHttpServerCodec",new HttpServerCodec());
        /*
        1.HttpServerCodec是netty提供的http的编码解码器
        2.增加一个自定义的handler
         */
        pipeline.addLast("myTestHttpServerHandler",new TestHttpServerHandler());

        System.out.println("ok");
    }
}
