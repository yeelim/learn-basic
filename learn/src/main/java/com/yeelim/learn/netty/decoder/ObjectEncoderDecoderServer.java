/**
 * 
 */
package com.yeelim.learn.netty.decoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import org.apache.log4j.Logger;

/**
 * 
 * 直接发送的对象必须实现Serializable接口，且在发送端Channel的pipeline中必须加上ObjectEncoder以对对象进行编码，然后
 * 在接收端Channel的pipeline中必须加上ObjectDecoder以对对象进行解码。
 * 
 * @author elim
 * @date 2015-3-28
 * @time 下午1:09:25 
 *
 */
public class ObjectEncoderDecoderServer {

	private final static Logger logger = Logger.getLogger(ObjectEncoderDecoderServer.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int port = 8080;
		EventLoopGroup parentGroup = new NioEventLoopGroup();
		EventLoopGroup childGroup = new NioEventLoopGroup();
		ServerBootstrap sb = new ServerBootstrap();
		sb.channel(NioServerSocketChannel.class).group(parentGroup, childGroup);
		sb.childHandler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new ObjectDecoder(1024*1024, ClassResolvers.weakCachingResolver(ObjectEncoderDecoderServer.class.getClassLoader())));
				ch.pipeline().addLast(new ObjectEncoder()).addLast(new ServerChannelHandler());
				
			}
		});
		try {
			ChannelFuture future = sb.bind(port).sync();
			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.info("线程被中断了", e);
		} finally {
			parentGroup.shutdownGracefully();
			childGroup.shutdownGracefully();
		}

	}
	
	private final static class ServerChannelHandler extends ChannelHandlerAdapter {

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
				throws Exception {
			logger.info("出异常了", cause);
		}

		/* (non-Javadoc)
		 * @see io.netty.channel.ChannelHandlerAdapter#channelRead(io.netty.channel.ChannelHandlerContext, java.lang.Object)
		 */
		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg)
				throws Exception {
			//此处可以直接转换成一个User对象，是因为客户端发送过来的是一个通过ObjectEncoder进行编码的User对象，
			//服务端在接收到该信息时通过ObjectDecoder对其进行了解码，使其变为一个User对象，所以此处可以直接使用。
			User user = (User) msg;
			logger.info("接收到了一个User对象，其内容是：" + msg);
			user.setName("Response: " + user.getName());
			//直接发送的User对象会通过配置的ObjectEncoder进行编码后再传递到客户端。
			ctx.writeAndFlush(user);
		}
		
	}

}
