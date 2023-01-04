package com.gzl0ng.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 * @author:郭正龙
 * @data:2022/12/23
 */
/*
1.自定义一个handler需要继承netty规定好的某个HandlerAdapter(规范)
2.这时我们自定义一个Handler，才能称为一个handler
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    //读取数据事件（读取客户端发送的消息）
    /*
    1.ChannelHandlerContext ctx：上下文对象,含有管道pipeline，通道channel,地址
    2.Object
     msg :就是客户端发送的数据 默认Object
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务器读取线程" + Thread.currentThread().getName() +
                "channel =" + ctx.channel());

        System.out.println("server ctx = " + ctx);
        System.out.println("channel 和 pipeline的关系:");
        Channel channel = ctx.channel();
        ChannelPipeline pipeline = ctx.pipeline();//本质是一个双向列表,出栈入栈问题

        //将msg转成一个ByteBuf
        //ByteBuf是Netty提供的，部署NIO的ByteBuff
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送消息是:" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址：" + ctx.channel().remoteAddress());



        //taskQueue使用：比如这里为一个非常耗时的业务->异步执行 -> 提交到该channel的NIOEventloop的taskQueue中
//        Thread.sleep(10 * 1000);
//        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端~2",CharsetUtil.UTF_8));
//        System.out.println("go on ...");
         /*
        解决方案1 用户程序自定义的普通任务
         */
//        ctx.channel().eventLoop().execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(10 * 1000);
//                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端~2",CharsetUtil.UTF_8));
//                }catch (Exception e){
//                    System.out.println("发生异常" + e.getMessage());
//                }
//            }
//        });
//        //下面这个任务是30秒后输出结果
//        ctx.channel().eventLoop().execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(20 * 1000);
//                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端~3",CharsetUtil.UTF_8));
//                }catch (Exception e){
//                    System.out.println("发生异常" + e.getMessage());
//                }
//            }
//        });
//                System.out.println("go on ...");



        //taskQueue使用2
        //用户自定义定时任务 -> 该任务是提交到scheduleTaskQueue中
//        ctx.channel().eventLoop().schedule(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(5 * 1000);
//                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端~4",CharsetUtil.UTF_8));
//                }catch (Exception e){
//                    System.out.println("发生异常" + e.getMessage());
//                }
//            }
//        },5, TimeUnit.SECONDS);
//        System.out.println("go on ...");

    }

    //数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //writeAndFlush是write + flush
        //将数据写入到缓存，并刷新
        //一般来说对发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端~1",CharsetUtil.UTF_8));
    }

    //处理异常,一般是关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
