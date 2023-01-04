package com.gzl0ng.netty.inouthandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author:郭正龙
 * @data:2023/1/3
 */
public class MyLongToByteEncoder extends MessageToByteEncoder<Long> {

    //编码方法
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("MyLongToByteEncoder的encoder被调用");
        System.out.println("msg=" + msg);

        out.writeLong(msg);
    }
}
