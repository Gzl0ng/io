package com.gzl0ng.netty.codec2;

import com.gzl0ng.netty.codec.StudentPOJO;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author:郭正龙
 * @data:2022/12/23
 */
/*
1.自定义一个handler需要继承netty规定好的某个HandlerAdapter(规范)
2.这时我们自定义一个Handler，才能称为一个handler
 */
//public class NettyServerHandler extends ChannelInboundHandlerAdapter {
public class NettyServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {


    //读取数据事件（读取客户端发送的消息）
    /*
    1.ChannelHandlerContext ctx：上下文对象,含有管道pipeline，通道channel,地址
    2.Object
     msg :就是客户端发送的数据 默认Object
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {
        //根据dataType来显示不同的信息
        MyDataInfo.MyMessage.DataType dataType = msg.getDataType();
        if (dataType==MyDataInfo.MyMessage.DataType.StudentType){
            MyDataInfo.Student student = msg.getStudent();
            System.out.println("学生id=" + student.getId() + "学生名字=" + student.getName());
        }else if (dataType == MyDataInfo.MyMessage.DataType.WorkerType){
            MyDataInfo.Worker worker = msg.getWorker();
            System.out.println("工人名字=" + worker.getName() + "工人年龄=" + worker.getAge());
        }else {
            System.out.println("传输类型不对");
        }
    }
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//
//        //读取客户端数据StudentPojo。student
//        StudentPOJO.Student student =  (StudentPOJO.Student)msg;
//
//        System.out.println("客户端发送的数据 id=" + student.getId() + " name=" +student.getName());
//    }
    //数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //writeAndFlush是write + flush
        //将数据写入到缓存，并刷新
        //一般来说对发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端~1",CharsetUtil.UTF_8));
    }

    //处理异常,一般是关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
