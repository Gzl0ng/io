package com.gzl0ng.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author:郭正龙
 * @data:2022/12/27
 */
public class NettyByteBuf01 {
    public static void main(String[] args) {
        //创建一个ByteBuf
        /*
        1.创建一个对象，该对象包含一个数组arr，是一个byte[10]
        2.在nettyBuffer中，不需要使用flip进行反转
            底层维护了readIndex和WriterIndex
        3.通过readIndex和writeIndex和capacity，将buffer分成三个区域
            0---readIndex 已经读取的区域
            readIndex---writeIndex 可读的区域
            writeIndex---capacity可写的区域
         */
        ByteBuf buffer = Unpooled.buffer(10);

        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }

        System.out.println("capacity:"+buffer.capacity());
        //输出
        //不操作readIndex，readIndex不变
//        for (int i = 0; i < buffer.capacity(); i++) {
//            System.out.println(buffer.getByte(i));
//        }
        //操作readIndex
        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.readByte());
        }
        System.out.println("执行完毕");
    }
}
