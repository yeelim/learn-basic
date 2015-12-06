/**
 * 
 */
package com.yeelim.learn.netty.basic;

import io.netty.bootstrap.Bootstrap;
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
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;

import org.apache.log4j.Logger;

/**
 * @author elim
 * @date 2015-3-23
 * @time 下午10:27:59 
 *
 */
public class TimeClient {

	private final static Logger logger = Logger.getLogger(TimeClient.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String host = "127.0.0.1";
		int port = 8080;
		new TimeClient().connect(host, port);
	}
	
	public void connect(String host, int port) {
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true);
		b.handler(new ChildChannelHandler());
		try {
			ChannelFuture future = b.connect(host, port).sync();
			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}
	}
	
	private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			logger.info("-----initChannel----");
			ch.pipeline().addLast(new LineBasedFrameDecoder(2048));
			ch.pipeline().addLast(new TimeClientHandler1());
			logger.info("----add TimeClientHandler----");
		}
		
	}

	private class TimeClientHandler1 extends ChannelHandlerAdapter {

		private int counter;
		
		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
				throws Exception {
			logger.info("出异常了", cause);
			ctx.close();
		}

		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			logger.info("准备发送信息给服务端。");
			for (int i=0; i<100; i++) {
				ctx.writeAndFlush(Unpooled.copiedBuffer("Time\n".getBytes()));
			}
			logger.info("发送信息给服务端成功。");
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg)
				throws Exception {
			logger.info("从服务端收到的信息是：" + msg);
			ByteBuf data = (ByteBuf) msg;
			byte[] bs = new byte[data.readableBytes()];
			data.readBytes(bs);
			logger.info("接收到的真实内容是：" + new String(bs) + "---" + ++counter);
			ctx.close();
		}
		
	}
	
}
