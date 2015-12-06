/**
 * 
 */
package com.yeelim.learn.netty.decoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.json.JsonObjectDecoder;

import org.apache.log4j.Logger;

/**
 * @author elim
 * @date 2015-3-26
 * @time 下午9:51:10 
 *
 */
public class JSONDecoderServer {
	
	private final static Logger logger = Logger.getLogger(JSONDecoderServer.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EventLoopGroup parentGroup = new NioEventLoopGroup();
		EventLoopGroup childGroup = new NioEventLoopGroup();
		ServerBootstrap sb = new ServerBootstrap();
		sb.group(parentGroup, childGroup);
		sb.option(ChannelOption.SO_BACKLOG, 2048);
		sb.channel(NioServerSocketChannel.class);
		sb.childHandler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new JsonObjectDecoder());
				ch.pipeline().addLast(new ServerChannelHandler());
			}
		});
		try {
			ChannelFuture future = sb.bind(8080).sync();
			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			parentGroup.shutdownGracefully();
			childGroup.shutdownGracefully();
		}
	}
	
	private final static class ServerChannelHandler extends ChannelHandlerAdapter {

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
				throws Exception {
			super.exceptionCaught(ctx, cause);
			logger.info("出异常了。", cause);
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg)
				throws Exception {
			logger.info("---读取到了一条信息-----");
			ByteBuf buf = (ByteBuf) msg;
			byte[] bs = new byte[buf.readableBytes()];
			buf.readBytes(bs);
			String content = new String(bs);
			logger.info(msg.getClass() + "--消息的内容是：-" + content);
			ByteBuf response = Unpooled.wrappedBuffer("{\"name\":\"张翼麟\", \"id\": 1, \"性别\": \"男\"}".getBytes());
			ctx.write(response);
		}

		@Override
		public void channelReadComplete(ChannelHandlerContext ctx)
				throws Exception {
			ctx.flush();
		}
		
	}

}
