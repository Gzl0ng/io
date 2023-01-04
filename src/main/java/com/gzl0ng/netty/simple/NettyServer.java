package com.gzl0ng.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author:郭正龙
 * @data:2022/12/23
 */
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        //创建BossGroup 和 WorkGroup
        /*
        1.创建二个线程组bossGroup，workerGroup
        2.bossGroup只处理连接请求，真正的与客户端业务处理，会交给workerGroup完成
        3.2个都是无限循环
        4.bossGroup和workerGroup含有的子线程（NioEventLoop）的个数
            默认实际cpu核数 * 2
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(8);//默认cpu核数*2

        try {
            //创建服务器端的启动对象，配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            //使用链式编程进行设置
            bootstrap.group(bossGroup,workerGroup)//设置二个线程组
                    .channel(NioServerSocketChannel.class)//使用NioServerSocketChannel作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG,128)//设置线程队列等待连接的个数
                    .childOption(ChannelOption.SO_KEEPALIVE,true)//设置保持活动连接状态
                   // .handler(null) //该handler对应bossGroup，childHandler对应workGroup
                    .childHandler(new ChannelInitializer<SocketChannel>() {//创建一个通道初始化对象（匿名对象）
                        //给pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            System.out.println("客户socketChannel hashcode" +
                                    ch.hashCode());//可以使用一个集合管理客户socketChannel ,在推送消息时，可以将
                            //业务加入到各个channel对应的NIOEventLoop的taskQueue或者scheduleTaskQueue
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    });//给我们的workGroup的EventLoopGroup对应的管道设置处理器

            System.out.println("。。。。。服务器 is ready");

            //绑定一个端口并且同步,生成一个ChannelFuture对象
            //启动服务器（并绑定端口）
            ChannelFuture cf = bootstrap.bind(6668).sync();

            //给cf注册监听器,监控我们关心的事件
            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (cf.isSuccess()){
                        System.out.println("监听端口6668成功");
                    }else {
                        System.out.println("监听端口6668失败");
                    }
                }
            });

            //对关闭的通道进行监听
            cf.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
