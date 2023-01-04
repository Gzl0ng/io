package com.gzl0ng.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/**
 * @author:郭正龙
 * @data:2022/12/27
 */
public class NettyByteBuf02 {
    public static void main(String[] args) {

        //创建ByteBuf
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello,world!", CharsetUtil.UTF_8);
        //使用相关的方法
        if (byteBuf.hasArray()){//true
            byte[] content = byteBuf.array();
            //将content转成字符串
            System.out.println(new String(content,CharsetUtil.UTF_8));

            System.out.println("byteBuf="+byteBuf);

            System.out.println(byteBuf.arrayOffset());//0
            System.out.println(byteBuf.readerIndex());//0
            System.out.println(byteBuf.writerIndex());//12
            System.out.println(byteBuf.capacity());//36

//            System.out.println(byteBuf.getByte(0));
            System.out.println(byteBuf.readByte());//104 执行一次后可读字节数减一
            int len = byteBuf.readableBytes();
            System.out.println(len);//可读字节数12

            //使用for取出各个字节
            for (int i = 0; i < len; i++) {
                System.out.println((char)byteBuf.getByte(i));
            }

            //按照某个范围读取
            System.out.println(byteBuf.getCharSequence(0,4,CharsetUtil.UTF_8));//hell
            System.out.println(byteBuf.getCharSequence(4,6,CharsetUtil.UTF_8));//o,worl

        }
    }
}
