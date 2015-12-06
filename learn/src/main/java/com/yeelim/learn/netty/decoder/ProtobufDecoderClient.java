/**
 * 
 */
package com.yeelim.learn.netty.decoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

import org.apache.log4j.Logger;

/**
 * @author elim
 * @date 2015-3-28
 * @time 下午10:44:42 
 *
 */
public class ProtobufDecoderClient {

	private final static Logger logger = Logger.getLogger(ProtobufDecoderClient.class);
	
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
				//解决读半包问题
				ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
				//解析Protobuf对象
				ch.pipeline().addLast(new ProtobufDecoder(UserProto.User.getDefaultInstance()));
				//也是一种进行编码的Encoder，服务端和客户端都必须添加
				ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
				//对发送的Protobuf对象进行编码
				ch.pipeline().addLast(new ProtobufEncoder());
				ch.pipeline().addLast(new ClientHandler());
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
	
	/**
	 * 与服务端进行通信的处理类
	 * @author elim
	 * @date 2015-3-28
	 * @time 下午11:25:56 
	 *
	 */
	private final static class ClientHandler extends ChannelHandlerAdapter {

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
				throws Exception {
			logger.info("抛出异常了", cause);
		}

		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			for (int i=0; i<10; i++) {
				UserProto.User.Builder builder = UserProto.User.newBuilder();
				builder.setId(i+1);
				builder.setName("Name_" + builder.getId());
				//直接将生成的UserProto.User对象发送到服务端
				UserProto.User user = builder.build();
				ctx.write(user);
				logger.info("********发送了消息：" + user);
			}
			ctx.flush();
		}

		public void channelRead(ChannelHandlerContext ctx, Object msg)
				throws Exception {
			UserProto.User user = (UserProto.User) msg;
			logger.info("接收到了来自服务端的信息，内容是：" + user);
		}
		
		
		
	}

}
