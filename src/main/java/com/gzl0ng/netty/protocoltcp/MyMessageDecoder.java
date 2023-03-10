package com.gzl0ng.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author:郭正龙
 * @data:2023/1/7
 */
public class MyMessageDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyMessageDecoder 方法被调用");
        //将得到的二进制字节码转成 ->MessageProtocol 数据包(对象)
        int length = in.readInt();
        byte[] content = new byte[length];
        in.readBytes(content);

        //封装成MessageProtocol 对象 放入out，传递给下一个handler处理
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(length);
        messageProtocol.setContent(content);

        out.add(messageProtocol);
    }
}
