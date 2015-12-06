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
import io.netty.handler.codec.marshalling.DefaultMarshallerProvider;
import io.netty.handler.codec.marshalling.DefaultUnmarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;

import org.apache.log4j.Logger;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

/**
 * 
 * 通过JBoss的Marshalling整合Netty进行编码、解码的测试用例。其编码、解码类似于JDK的序列化和反序列化，只是对其进行了增强。
 * 通过Marshalling进行编码、解码发送的对象必须实现了Serializable接口。
 * 
 * @author elim
 * @date 2015-3-29
 * @time 上午10:54:57 
 *
 */
public class MarshallingServer {

	private final static Logger logger = Logger.getLogger(MarshallingServer.class);
	
	public static void main(String[] args) {
		int port = 8080;
		EventLoopGroup group = new NioEventLoopGroup();
		ServerBootstrap sb = new ServerBootstrap();
		sb.group(group).channel(NioServerSocketChannel.class);
		sb.childHandler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(MarshallingFactory.buildDecoder());//解码器
				ch.pipeline().addLast(MarshallingFactory.buildEncoder());//编码器
				ch.pipeline().addLast(new ServerHandler());
			}
		});
		
		try {
			ChannelFuture future = sb.bind(port).sync();
			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			logger.error("出异常了", e);
		} finally {
			group.shutdownGracefully();
		}
	}
	
	public final static class MarshallingFactory {
		
		/**
		 * 构建MarshallingEncoder
		 * @return
		 */
		public static MarshallingEncoder buildEncoder() {
			MarshallerFactory factory = Marshalling.getProvidedMarshallerFactory("serial");
			MarshallingConfiguration config = new MarshallingConfiguration();
			config.setVersion(5);
			MarshallerProvider provider = new DefaultMarshallerProvider(factory, config);
			return new MarshallingEncoder(provider);
		}
		
		/**
		 * 构建一个MarshallingDecoder
		 * @return
		 */
		public static MarshallingDecoder buildDecoder() {
			MarshallerFactory factory = Marshalling.getProvidedMarshallerFactory("serial");
			MarshallingConfiguration config = new MarshallingConfiguration();
			config.setVersion(5);
			UnmarshallerProvider provider = new DefaultUnmarshallerProvider(factory, config);
			return new MarshallingDecoder(provider);
		}
		
	}
	
	private static class ServerHandler extends ChannelHandlerAdapter {

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
				throws Exception {
			logger.error("出错了", cause);
		}

		/* (non-Javadoc)
		 * @see io.netty.channel.ChannelHandlerAdapter#channelRead(io.netty.channel.ChannelHandlerContext, java.lang.Object)
		 */
		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg)
				throws Exception {
			User user = (User) msg;
			logger.info("=============接收到的信息是：" + msg);
			user.setName("Response: " + user.getName());
			ctx.write(user);
		}

		/* (non-Javadoc)
		 * @see io.netty.channel.ChannelHandlerAdapter#channelReadComplete(io.netty.channel.ChannelHandlerContext)
		 */
		@Override
		public void channelReadComplete(ChannelHandlerContext ctx)
				throws Exception {
			ctx.flush();//等所有的信息都接收完并发送完后再一次性刷到客户端。
		}
		
	}

}
