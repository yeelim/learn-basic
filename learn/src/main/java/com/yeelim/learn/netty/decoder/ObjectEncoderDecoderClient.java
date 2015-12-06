/**
 * 
 */
package com.yeelim.learn.netty.decoder;

import org.apache.log4j.Logger;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * @author elim
 * @date 2015-3-28
 * @time 下午1:23:09 
 *
 */
public class ObjectEncoderDecoderClient {

	private final static Logger logger = Logger.getLogger(ObjectEncoderDecoderClient.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int port = 8080;
		String host = "localhost";
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(group).channel(NioSocketChannel.class);
		b.handler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new ObjectEncoder());
				ch.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(ObjectEncoderDecoderClient.class.getClassLoader())));
				ch.pipeline().addLast(new ClientChannelHandler());
			}
		});
		
		try {
			ChannelFuture future = b.connect(host, port).sync();
			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			logger.info("线程被中断了", e);
		} finally {
			group.shutdownGracefully();
		}
		
	}
	
	private final static class ClientChannelHandler extends ChannelHandlerAdapter {

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
				throws Exception {
			logger.info("出异常了", cause);
		}

		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			User user = null;
			for (int i=0; i<10; i++) {
				user = new User(i+1, "Name-" + (i+1));
				ctx.write(user);
				logger.info("发送了一个对象：" + user);
			}
			ctx.flush();
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg)
				throws Exception {
			//直接接收的是一个User对象，其会被ObjectDecoder自动解码
			logger.info("接收到了一个对象，其类型是：" + msg.getClass() + "，对应的内容是：" + msg);
		}
		
		
		
	}

}
