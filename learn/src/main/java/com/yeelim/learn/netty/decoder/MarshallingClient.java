/**
 * 
 */
package com.yeelim.learn.netty.decoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import org.apache.log4j.Logger;

import com.yeelim.learn.netty.decoder.MarshallingServer.MarshallingFactory;

/**
 * @author elim
 * @date 2015-3-29
 * @time 上午11:15:50 
 *
 */
public class MarshallingClient {

	private final static Logger logger = Logger.getLogger(MarshallingClient.class);
	
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
				ch.pipeline().addLast(MarshallingFactory.buildDecoder());
				ch.pipeline().addLast(MarshallingServer.MarshallingFactory.buildEncoder());
				ch.pipeline().addLast(new ClientHandler());
				
			}
			
		});
		
		try {
			b.connect(host, port).sync().channel().closeFuture().sync();
		} catch (InterruptedException e) {
			logger.error("出错了", e);
		} finally {
			group.shutdownGracefully();
		}
	}
	
	private final static class ClientHandler extends ChannelHandlerAdapter {

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
				throws Exception {
			logger.error("出异常了", cause);
		}

		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			User user = null;
			for (int i=0; i<10; i++) {
				user = new User(i+1, "Name_" + (i+1));
				ctx.write(user);
			}
			ctx.flush();
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg)
				throws Exception {
			User user = (User) msg;
			logger.info("从服务端接收到信息：" + user);
		}
		
	}

}
