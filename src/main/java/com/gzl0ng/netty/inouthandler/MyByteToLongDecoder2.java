package com.gzl0ng.netty.inouthandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author:郭正龙
 * @data:2023/1/4
 */
public class MyByteToLongDecoder2 extends ReplayingDecoder<Void> {


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyByteToLongDecoder decoder2被调用");

        //ReplayingDecoder不需要判断数据是否足够读取，内部会进行处理判断
            out.add(in.readLong());
    }
}
