package com.gzl0ng.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author:郭正龙
 * @data:2022/12/27
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {
    /*
    ctx上下文
    evt事件
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            //将evt向下转型IdleStateEvent
            IdleStateEvent event = (IdleStateEvent) evt;
            String eventType = null;

            switch (event.state()){
                case READER_IDLE:
                    eventType="读空闲";
                    break;
                case WRITER_IDLE:
                    eventType="写空闲";
                    break;
                case ALL_IDLE:
                    eventType="读写空闲";
                    break;
            }
            System.out.println(ctx.channel().remoteAddress() + "--超时事件发生" + eventType);
            System.out.println("服务器做相应处理..");

            //模拟处理：如果发生空闲，我们关闭通道
            ctx.channel().close();
        }
    }
}
