package com.gzl0ng.nio.other.charset;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * @author:郭正龙
 * @data:2022/12/14
 */
public class CharsetDemo {
    public static void main(String[] args) throws CharacterCodingException {
        //1.获取Charset对象
        Charset charset = Charset.forName("UTF-8");

        //2.获取编码器对象
        CharsetEncoder charsetEncoder = charset.newEncoder();

        //3.创建缓冲区
        CharBuffer buffer = CharBuffer.allocate(1024);
        buffer.put("gzl郭正龙");
        buffer.flip();

        //4.编码
        ByteBuffer encodeBuffer = charsetEncoder.encode(buffer);
        System.out.println("编码之后的结果:");
        for (int i = 0; i < encodeBuffer.limit(); i++) {
            System.out.println(encodeBuffer.get());
        }
        encodeBuffer.flip();
        //5.获取解码器
        CharsetDecoder charsetDecoder = charset.newDecoder();

        //6.解码
        CharBuffer decodeBuffer = charsetDecoder.decode(encodeBuffer);
        System.out.println("解码之后的数据:");
        System.out.println(decodeBuffer.toString());

        //7.使用GBK解码
        Charset charset1 = Charset.forName("GBK");
        encodeBuffer.flip();
        CharBuffer buffer2 = charset1.decode(encodeBuffer);
        System.out.println("使用其他编码进行解码:");
        System.out.println(buffer2.toString());
    }

    //获取Charset所支持的字符编码
    @Test
    public void test(){
        SortedMap<String, Charset> map = Charset.availableCharsets();
        Set<Map.Entry<String, Charset>> set = map.entrySet();
        for (Map.Entry<String, Charset> entry : set) {
            System.out.println(entry.getKey()+"=" + entry.getValue().toString());
        }
    }
}
