package com.gzl0ng.netty.inouthandler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author:郭正龙
 * @data:2023/1/3
 */
public class MyClientHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("服务器的ip=" + ctx.channel().remoteAddress() + " 收到的数据=" + msg);
    }

    //重写channelActive发送数据

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MyClientHandler发送数据");
//        ctx.writeAndFlush(Unpooled.copiedBuffer(""));
        ctx.writeAndFlush(123456L);//发送的是一个long

        //分析
        /*
        1.abcdabcdabcd是16个字节
        2.该处理器的前一个handler是MyLongToByteEncoder
        3.MyLongToByteEncoder父类是 MessageToByteEncoder
        4.MessageToByteEncoder的writer方法会判断是不是应该处理的类型

        因此在编写Encoder 时注意传入数据类型和处理的数据类型是否一致
         */
//        ctx.writeAndFlush(Unpooled.copiedBuffer("abcdabcdabcdabcd", CharsetUtil.UTF_8));
    }
}
