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
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

import org.apache.log4j.Logger;

/**
 * @author elim
 * @date 2015-3-28
 * @time 下午10:44:14 
 *
 */
public class ProtobufDecoderServer {

	private static final Logger logger = Logger.getLogger(ProtobufDecoderServer.class);
	
	public static void main(String[] args) {

		int port = 8080;
		EventLoopGroup group = new NioEventLoopGroup();
		ServerBootstrap sb = new ServerBootstrap();
		sb.group(group).channel(NioServerSocketChannel.class);
		sb.childHandler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
				ch.pipeline().addLast(new ProtobufDecoder(UserProto.User.getDefaultInstance()));
				//该Prepender是关键，服务端和客户端都必须添加，它是Encoder的一种。
				ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
				ch.pipeline().addLast(new ProtobufEncoder());
				ch.pipeline().addLast(new ServerHandler());
			}
		});
		try {
			ChannelFuture future = sb.bind(port).sync();
			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			logger.info("线程被中断了", e);
		} finally {
			group.shutdownGracefully();
		}
		
	}
	
	private final static class ServerHandler extends ChannelHandlerAdapter {

		private int index;
		
		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
				throws Exception {
			logger.info("出异常了", cause);
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg)
				throws Exception {
			logger.info("============================" + msg);
			UserProto.User user = (UserProto.User) msg;
			logger.info("接收到了来自客户端的信息：" + user);
			UserProto.User.Builder builder = UserProto.User.newBuilder(user);
			builder.setName("Response: " + builder.getName());
			ctx.write(msg);
			logger.info("Read: " + ++index);
		}

		@Override
		public void channelReadComplete(ChannelHandlerContext ctx)
				throws Exception {
			ctx.flush();
			//等一次接收的所有信息都完成了之后才会调用channelReadComplete方法，即在一次信息处理中
			//channelRead方法可能调用多次，而channelReadComplete只会调用一次。
			logger.info("Complete: " + index);
		}
		
	}

}
