package com.gzl0ng.nio.buffer;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author:郭正龙
 * @data:2022/12/6
 */
public class BufferDemo02 {
    static private final int start = 0;
    static private final int size = 1024;

    /**
     * 内存映射文件IO操作
     */
    @Test
    public void buff04() throws IOException {
        String path = BufferDemo02.class.getResource("/").getPath();

        RandomAccessFile raf = new RandomAccessFile(path + "01.txt", "rw");
        FileChannel fc = raf.getChannel();
        MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, start, size);


        mbb.put(0,(byte) 97);
        mbb.put(1023,(byte) 122);
        raf.close();
    }

    /**
     * 直接缓冲区
     */
    @Test
    public void buff03() throws IOException {
        String path = BufferDemo02.class.getResource("/").getPath();
        String inputPath = path + "01.txt";
        String outPath = path + "02.txt";

        FileInputStream fileInputStream = new FileInputStream(inputPath);
        FileOutputStream fileOutputStream = new FileOutputStream(outPath);
        FileChannel inChannel = fileInputStream.getChannel();
        FileChannel outChannel = fileOutputStream.getChannel();

        //创建直接缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (true){
            buffer.clear();
            int r = inChannel.read(buffer);
            if (r==-1){
                break;
            }
            buffer.flip();
            outChannel.write(buffer);
        }

        inChannel.close();
        outChannel.close();
        fileInputStream.close();
        fileOutputStream.close();
    }

    /**
     * 只读缓冲区
     */
    @Test
    public void buff02(){
        ByteBuffer buffer = ByteBuffer.allocate(10);
        for (int i = 0; i < 10; i++) {
            buffer.put((byte) i);
        }

        //创建只读缓冲区
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();

        for (int i = 0; i < buffer.capacity(); i++) {
            byte b = buffer.get(i);
            b*=10;
            buffer.put(i,b);
        }

        readOnlyBuffer.position(0);
        readOnlyBuffer.limit(buffer.capacity());

        for (int i = 0; i < readOnlyBuffer.capacity(); i++) {
            System.out.println(readOnlyBuffer.get());
        }
    }

    /**
     * 分片缓冲区
     */
    @Test
    public void buff01(){
        ByteBuffer buffer = ByteBuffer.allocate(10);

        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte)i);
        }

        //创建子缓冲区
        buffer.position(3);
        buffer.limit(7);
        ByteBuffer slice = buffer.slice();

        //改变子缓冲区内容
        for (int i = 0; i < slice.capacity(); i++) {
            byte b = slice.get(i);
            b *= 10;
            slice.put(i,b);
        }

        buffer.position(0);
        buffer.limit(buffer.capacity());

        while (buffer.hasRemaining()){
            System.out.println(buffer.get());
        }
    }


}
