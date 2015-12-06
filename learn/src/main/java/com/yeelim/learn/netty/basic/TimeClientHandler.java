/**
 * 
 */
package com.yeelim.learn.netty.basic;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import org.apache.log4j.Logger;

/**
 * @author elim
 * @date 2015-3-23
 * @time 下午10:48:38 
 *
 */
public class TimeClientHandler extends ChannelHandlerAdapter {

	private final static Logger logger = Logger.getLogger(TimeClientHandler.class);
	
    private final ByteBuf firstMessage;

    public TimeClientHandler() {
        byte[] req = "Time".getBytes();
        firstMessage = Unpooled.buffer(req.length);
        firstMessage.writeBytes(req);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //与服务端建立连接后
        System.out.println("client channelActive..");
        ctx.writeAndFlush(firstMessage);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        System.out.println("client channelRead..");
        //服务端返回消息后
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, "UTF-8");
        System.out.println("Now is :" + body);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        System.out.println("client exceptionCaught..");
        // 释放资源
        logger.warn("Unexpected exception from downstream:"
                + cause.getMessage());
        ctx.close();
    }

}
