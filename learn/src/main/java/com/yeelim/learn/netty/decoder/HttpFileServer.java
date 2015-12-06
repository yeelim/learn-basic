/**
 * 
 */
package com.yeelim.learn.netty.decoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.CharsetUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.Logger;

/**
 * 
 * 采用Netty基于Http协议开发文件服务器
 * 
 * @author elim
 * @date 2015-3-30
 * @time 下午10:30:33 
 *
 */
public class HttpFileServer {

	private final static Logger logger = Logger.getLogger(HttpFileServer.class);
	private final static Path ROOT = Paths.get(System.getProperty("user.dir"));
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int port = 8080;
		EventLoopGroup group = new NioEventLoopGroup();
		ServerBootstrap sb = new ServerBootstrap();
		sb.group(group).option(ChannelOption.SO_BACKLOG, 1024);
		sb.channel(NioServerSocketChannel.class);
		sb.childHandler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new HttpRequestDecoder());
				ch.pipeline().addLast(new HttpObjectAggregator(65536));
				ch.pipeline().addLast(new HttpResponseEncoder());
				ch.pipeline().addLast(new ChunkedWriteHandler());
				ch.pipeline().addLast(new RequestHandler());
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
	
	private final static class RequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

		@Override
		protected void messageReceived(ChannelHandlerContext ctx,
				FullHttpRequest request) throws Exception {
			if (request.decoderResult().isFailure()) {
				sendError(ctx, HttpResponseStatus.BAD_REQUEST);
			} else if (request.method() != HttpMethod.GET) {
				sendError(ctx, HttpResponseStatus.METHOD_NOT_ALLOWED);
			} else {
				String uri = request.uri();
				Path path = Paths.get(System.getProperty("user.dir"), uri.replace('/', File.separatorChar));
				FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
				response.headers().set("Content-Type", "text/html;charset=UTF-8");
				if (!Files.exists(path)) {
					response.content().writeBytes("对应的资源不存在".getBytes(CharsetUtil.UTF_8));
				} else if (Files.isDirectory(path)) {//
					String result = this.buildHtml4Dir(path);
					ByteBuf buf = Unpooled.copiedBuffer(result, CharsetUtil.UTF_8);
					response.content().writeBytes(buf);
					buf.release();
				} else {//文件
//					MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
					//以未知的文本类型进行展示。
					response.headers().set("Content-Type", "text/XXX");
					response.content().writeBytes(Files.readAllBytes(path));
				}
				ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
			}
		}
		
		private String buildHtml4Dir(Path dir) {
			StringBuilder builder = new StringBuilder("<!DOCTYPE html>\r\n<html><head><title>");
			builder.append(dir.getFileName()).append("</title></head><body><div>")
											.append(dir.toString())
											.append("</div><ul>");
			builder.append("<li><a href=\"/" + this.getRelaPath(dir.getParent()) + "\">..</a></li>");
			try {
				DirectoryStream<Path> ds = Files.newDirectoryStream(dir);
				for (Path path : ds) {
					builder.append("<li><a href=\"/").append(this.getRelaPath(path)).append("\">").append(path.getFileName()).append("</a></li>");
				}
			} catch (IOException e) {
				logger.info("遍历目录失败", e);
			}
			builder.append("</ul></body></html>\r\n");
			return builder.toString();
		}
		
		/**
		 * 获取相对路径
		 * @param path
		 * @return
		 */
		private String getRelaPath(Path path) {
			return ROOT.relativize(path).toString();
		}
		
		/**
		 * 发送错误信息到客户端
		 * @param ctx
		 * @param status
		 */
		private void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
			FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, Unpooled.copiedBuffer("Failure: " + status.toString() + "\r\n", CharsetUtil.UTF_8));
			response.headers().set("Content-Type", "text/plain;charset=UTF-8");
			ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
		}
		
	}

}
