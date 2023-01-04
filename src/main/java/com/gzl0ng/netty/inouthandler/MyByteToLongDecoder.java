package com.gzl0ng.netty.inouthandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author:郭正龙
 * @data:2023/1/3
 */
public class MyByteToLongDecoder extends ByteToMessageDecoder {
    /**
     *decoder会根据接收的数据，被调用多次，直到确定没有新的元素添加到list
     * 或者是ByteBuf没有更多的可读字节为止
     * 如果list out 部位空，就会将list的内容传递给下一个
     *      channelInboundHandler处理，该处理器的方法也会被调用多次
     * @param ctx 上下文对象
     * @param in   入站的ByteBuf
     * @param out  List集合，将解码的数据传输给下一个handler
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyByteToLongDecoder decoder被调用");

        //因为Long8给字节，需要判断有8个字节，才能读取一个long
        if (in.readableBytes() >= 8){
            out.add(in.readLong());
        }
    }
}
