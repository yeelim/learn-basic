/**
 * 
 */
package com.yeelim.learn.netty.basic;

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
import io.netty.handler.codec.LineBasedFrameDecoder;

import java.util.Date;

import org.apache.log4j.Logger;

/**
 * 测试过程：
 * 1、基础的Netty通信测试。
 * 2、测试拆包/粘包问题。
 * 3、测试解决拆包/粘包问题。
 * 
 * @author elim
 * @date 2015-3-23
 * @time 下午10:05:04 
 *
 */
public class TimeServer {

	private final static Logger logger = Logger.getLogger(TimeServer.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int port = 8080;
		new TimeServer().bind(port);
	}
	
	public void bind(int port) {
		EventLoopGroup parentGroup = new NioEventLoopGroup();
		EventLoopGroup childGroup = new NioEventLoopGroup();
		ServerBootstrap sb = new ServerBootstrap();
		sb.group(parentGroup, childGroup);
		sb.channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 1024);
		sb.childHandler(new ChildChannelHandler());	//处理客户端发送过来的请求信息
		try {
			ChannelFuture future = sb.bind(port).sync();
			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			parentGroup.shutdownGracefully();
			childGroup.shutdownGracefully();
		}
	}
	
	private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			//NioSocketChannel
			logger.info(ch.getClass() + "--------initChannel------" + ch);
			//以换行符“\r\n”或“\n”进行分隔，每次分隔作为一个包，每个包的最大字符长度限制为2048.
			ch.pipeline().addLast(new LineBasedFrameDecoder(2048));
			ch.pipeline().addLast(new TimeServerHandler1());
			logger.info("----add TimeServerHandler----");
		}
		
	}
	
	private class TimeServerHandler1 extends ChannelHandlerAdapter {

		private int counter;
		
		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
				throws Exception {
			ctx.close();//关闭连接，释放资源
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg)
				throws Exception {
			logger.info("准备读取信息。。。。。。");
			ByteBuf buf = (ByteBuf) msg;
			byte[] bs = new byte[buf.readableBytes()];
			buf.readBytes(bs);
			String request = new String(bs);
			logger.info("接收到的请求信息是：" + request + "---counter: " + ++counter);
			if (request.equals("Time")) {
				//发送的内容只能是ByteBuf。
				ctx.write(Unpooled.copiedBuffer((new Date().toString()+"\n").getBytes()));
			} else {
				ctx.write(Unpooled.copiedBuffer("错误命令".getBytes()));
			}
		}

		@Override
		public void channelReadComplete(ChannelHandlerContext ctx)
				throws Exception {
			//正常来讲对于Netty而言，其在进行写操作的时候只会将对应的信息写到缓冲区中，通过flush可以将对应的信息写到对应的SocketChannel中。
			ctx.flush();
		}
		
	}

}
