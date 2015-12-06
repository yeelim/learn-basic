/**
 * 
 */
package com.yeelim.learn.netty.decoder;

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
import io.netty.handler.codec.json.JsonObjectDecoder;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import org.apache.log4j.Logger;

/**
 * @author elim
 * @date 2015-3-26
 * @time 下午9:53:22 
 *
 */
public class JSONDecoderClient {

	private final static Logger logger = Logger.getLogger(JSONDecoderClient.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int prot = 8080;
		String host = "127.0.0.1";
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true);
		b.group(group).handler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				logger.info("init channel");
				ch.pipeline().addLast(new JsonObjectDecoder());
				ch.pipeline().addLast(new ClientChannelHandler());
			}
			
		});
		try {
			ChannelFuture future = b.connect(host, prot).sync();
			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}
	}
	
	private final static class ClientChannelHandler extends ChannelHandlerAdapter {
		
		private Random r = new Random();

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
				throws Exception {
			super.exceptionCaught(ctx, cause);
			logger.info("出错了。", cause);
		}

		/**
		 * 当成功与服务端建立了连接后会调用该方法，此后即可开始往服务端发送信息
		 */
		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			logger.info("成功建立了连接");
			for (int i=0; i<10; i++) {
				ctx.writeAndFlush(this.getMsg(i));
			}
		}

		private ByteBuf getMsg(int id) {
			int random = r.nextInt(id + 10);
			String msg = "{\"id\": "+id+",\"random随机数\": "+random+"}{\"id\": "+id+",\"random\": "+random+"}";
			ByteBuf request = null;
			try {
				request = Unpooled.wrappedBuffer(msg.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return request;
		}
		
		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg)
				throws Exception {
			ByteBuf buf = (ByteBuf) msg;
			byte[] bs = new byte[buf.readableBytes()];
			buf.readBytes(bs);
			String content = new String(bs);
			logger.info("接收到了来自服务端的信息：" + msg.getClass() + "---------" + content);
			ctx.close();
		}
		
	}

}
