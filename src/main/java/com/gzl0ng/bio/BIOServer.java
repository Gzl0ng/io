package com.gzl0ng.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author:郭正龙
 * @data:2022/12/16
 */
/*
telnet 去连接这个服务端即可测试
 */
public class BIOServer {
    public static void main(String[] args) throws IOException {
        //线程池机制

        //思路
        //1.创建一个线程池
        //2.如果有客户端连接，就创建一个线程，与之通讯(单独写一个方法)
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        //创建ServerSocket
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器启动了");
        while (true){
            System.out.println("线程信息 id =" + Thread.currentThread().getId() + "线程名称 =" + Thread.currentThread().getName());
            //监听，等待客户端连接
            System.out.println("等待连接");
            final Socket socket = serverSocket.accept();
            System.out.println("连接到一个客户端");

            //创建一个线程，与之通讯(单独写一个方法)
            newCachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {//我们重写
//                    可以和客户端通信
                    handler(socket);
                }
            });
        }
    }

    //编写一个handler方法，和客户端通讯
    public static void handler(Socket socket) {
        byte[] bytes = new byte[1024];
        //通过socket获取输入流
        try {
            System.out.println("线程信息 id =" + Thread.currentThread().getId() + "线程名称 =" + Thread.currentThread().getName());
            InputStream inputStream = socket.getInputStream();

            //循环的读取客户端发送的数据
            while (true){
                System.out.println("线程信息 id =" + Thread.currentThread().getId() + "线程名称 =" + Thread.currentThread().getName());
                System.out.println("read...");
                int read = inputStream.read(bytes);
                if (read != -1){
                    System.out.println(new String(bytes,0,read));//输出客户端发送的数据
                }else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            System.out.println("关闭client连接");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
