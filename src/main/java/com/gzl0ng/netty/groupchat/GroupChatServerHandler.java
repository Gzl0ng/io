package com.gzl0ng.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author:郭正龙
 * @data:2022/12/27
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {
//    public static List<Channel> channels= new ArrayList<Channel>();

    //使用一个hashmap管理,私聊管理，用户管理
//    public static Map<String,Channel> channels = new HashMap<String,Channel>();
//    public static Map<User,Channel> channels2 = new HashMap<User,Channel>();



    //定义一个channel 组 管理所有的channel
    /*
    GlobalEventExecutor.INSTANCE全局事件执行器：单例
     */
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //handlerAdded 表示连接建立，一旦连接，第一个被执行
    //将当前channel加入到channelGroup
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //将该客户加入聊天的消息推送给其它在线的客户端
        /*
        该方法会将 channelGroup中所有的channel比哪里，并发送消息
        不需要手动去遍历了
         */
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + " 加入聊天\n" +sdf.format(new Date()) + "\n");
        channelGroup.add(channel);

//        channels.put("id100",channel);
//        channels2.put(new User(10,"123"),channel);
    }

    //表示断开连接,将xx客户离开消息推送给当前在线的客户
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客户端]"+channel.remoteAddress()+" 离开了\n");
        System.out.println("channelGroup size" + channelGroup.size());
    }

    //表示channel处于活动状态，提升xx上线
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 上线了~");
    }

    //表示channel 参与不活动状态，提升xx下线了
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 下线了~");
    }

    //读取数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //获取到当前channel
        Channel channel = ctx.channel();
        //这时我们遍历channelGroup ，工具不同的情况，回送不同的消息
        channelGroup.forEach(ch -> {
            if (channel != ch){//不是当前channel，转发消息
                ch.writeAndFlush("[客户]" + channel.remoteAddress() +"发送了消息" +msg + "\n");
            }else {
                ch.writeAndFlush("[自己]" + msg+"\n");
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //关闭通道
        ctx.close();
    }
}
