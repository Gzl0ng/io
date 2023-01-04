package com.gzl0ng.netty.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author:郭正龙
 * @data:2022/12/27
 */
public class MyServer {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup(8);

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))//在bossGroup增加一个日志处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //加入一个netty提供的IdleStateHandler
                            /*
                            1.IdleStateHandler是netty提供的处理空闲状态的处理器
                            2.long readerIdleTime:表示多长事件没有读操作了，就会发送一个心跳检测包检测是否还是连接状态
                            3.long writerIdleTime:表示多长时间没有写操作了，就会发送一个心跳检测包检测是否还是连接状态
                            4.long allIdleTime:表示多长时间没有读写操作了,就会发送一个心跳检测包检测是否还是连接状态
                            5.文档说明
                            Triggers an {@link IdleStateEvent} when a {@link Channel} has not performed
 * read, write, or both operation for a while.
                            6.当IdleStateEvent触发后，就会传递给管道的下一个handler去处理
                            通过调用(触发)下一个handler的userEventTriggered 在该方法中去处理IdleStateEvent（读空闲，写空闲，读写空闲）
                             */
                            pipeline.addLast(new IdleStateHandler(13,5,2, TimeUnit.SECONDS));
                            //加入一个对空闲检测进一步处理的handler(自定义)
                            pipeline.addLast(new MyServerHandler());
                        }
                    });

            //启动服务器
            ChannelFuture channelFuture = serverBootstrap.bind(7000).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
