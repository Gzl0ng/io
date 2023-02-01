package com.gzl0ng.netty.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @author:郭正龙
 * @data:2023/1/5
 */
public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        System.out.println("MyServerHandler被调用");

        //接收到数据，并处理
        int len = msg.getLen();
        byte[] content = msg.getContent();

        System.out.println("服务端接收到消息如下");
        System.out.println("长度=" + len);
        System.out.println("内容=" +new String(content, StandardCharsets.UTF_8));

        System.out.println("服务器接收到消息次数:" + ++count);

        //回复消息

        byte[] responseContent = UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8);
        int responseLength = responseContent.length;
        //构建一个协议包
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(responseLength);
        messageProtocol.setContent(responseContent);

        ctx.writeAndFlush(messageProtocol);
        System.out.println();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
