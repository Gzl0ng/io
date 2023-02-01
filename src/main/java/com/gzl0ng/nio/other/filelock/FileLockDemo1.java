package com.gzl0ng.nio.other.filelock;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author:郭正龙
 * @data:2022/12/7
 */
public class FileLockDemo1 {
    public static void main(String[] args) throws IOException {
        String input = "hello";
        System.out.println("input:"+input);

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        String path = FileLockDemo1.class.getResource("/").getPath();
        String filePath = path+"01.txt";
        Path inputPath = Paths.get("D:/idea_work/nio/target/classes/01.txt");

        FileChannel channel = FileChannel.open(inputPath, StandardOpenOption.WRITE,StandardOpenOption.APPEND);
        channel.position(channel.size()-1);

        //加锁
        FileLock lock = channel.lock();
        System.out.println("是否共享锁："+lock.isShared());

        channel.write(buffer);

        channel.close();

        //读文件
        readFile(filePath);
    }

    private static void readFile(String filePath) throws IOException {
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String str = bufferedReader.readLine();
        System.out.println("读取内容");

        while (str!=null){
            System.out.println(""+str);
            str=bufferedReader.readLine();
        }

        fileReader.close();
        bufferedReader.close();
    }
}
