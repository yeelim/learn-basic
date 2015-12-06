/**
 * 
 */
package com.yeelim.learn.java.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;

/**
 * @author elim
 * @date 2014-12-4
 * @time 下午11:29:13 
 *
 */
public class SelectorTest {

	@Test
	public void test() throws IOException {
		URL baidu = new URL("http://www.sina.com.cn");
		int port = baidu.getPort()!=-1 ? baidu.getPort() : baidu.getDefaultPort();
		SocketAddress address = new InetSocketAddress(baidu.getHost(), port);
		Selector selector = Selector.open();
		SocketChannel channel = SocketChannel.open();
		channel.configureBlocking(false);
		channel.connect(address);
		channel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ);
		boolean finished = false;
		while (!finished) {
			selector.select();
			Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
			while (iter.hasNext()) {
				SelectionKey key = iter.next();
				iter.remove();
				if (key.isValid() && key.isConnectable()) {
					System.out.println("--------connectable--------");
					SocketChannel targetChannel = (SocketChannel)key.channel();
					if (targetChannel.finishConnect()) {//连接成功
						InetSocketAddress isa = (InetSocketAddress)targetChannel.getRemoteAddress();
						String host = isa.getHostString();
						System.out.println("host==================" + host);
						String request = "GET http://www.sina.com.cn HTTP/1.0\r\n\r\nHost:"+host+"\r\n";
						ByteBuffer buffer = ByteBuffer.wrap(request.getBytes("UTF-8"));
						targetChannel.write(buffer);
					} else {//连接失败
//						key.cancel();
						System.out.println("---------cancel---------");
						finished = true;
					}
				} else if (key.isValid() && key.isReadable()) {
					System.out.println("---------readable---------");
					SocketChannel targetChannel = (SocketChannel)key.channel();
					ByteBuffer buffer = ByteBuffer.allocate(2*1024);
					Path path = Paths.get("D:\\test\\baidu1.txt");
					FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
					int len = 0;
					while ((len=targetChannel.read(buffer)) != -1 && buffer.position() > 0) {//大于0说明读到了数据。不等于-1，说明还没有结束
						System.out.println(len + "--------" + buffer.position());
						buffer.flip();
						fileChannel.write(buffer);
						buffer.compact();
						System.out.println("===========" + buffer.position());
					}
					if (len == -1) {//说明已经读完了
						finished = true;
					}
				}
			}
		}
		
	}

	@Test
	public void test2() throws IOException {
		Collection<URL> urlColl = new ArrayList<URL>();
		urlColl.add(new URL("http://www.baidu.com"));
		urlColl.add(new URL("http://www.sina.com.cn"));
		urlColl.add(new URL("http://www.qq.com"));
//		urlColl.add(new URL(new URL("http://www.baidu.com"), "index.html"));
		urlColl.add(new URL("http://www.163.com"));
		urlColl.add(new URL("http://www.ifeng.com"));
		urlColl.add(new URL("http://www.cntv.cn"));
		int total = urlColl.size();
		int finished = 0;
		Selector selector = Selector.open();
		Map<SocketAddress, String> addrMap = this.urlToMap(urlColl);
		for (Map.Entry<SocketAddress, String> entry : addrMap.entrySet()) {
			this.register(selector, entry.getKey());
		}
		while (finished < total) {
			selector.select();
			Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
			while (iter.hasNext()) {
				SelectionKey key = iter.next();
				iter.remove();
				if (key.isConnectable()) {
					SocketChannel channel = (SocketChannel)key.channel();
					System.out.println("------connectable----- " + channel.getRemoteAddress());
					if (channel.finishConnect()) {
						InetSocketAddress addr = (InetSocketAddress)channel.getRemoteAddress();
						String path = "http://"+addr.getHostString();
						String request = "GET "+path+" HTTP/1.0\r\n\r\nHost:"+addr.getHostString()+"\r\n";
						ByteBuffer buffer = ByteBuffer.wrap(request.getBytes("UTF-8"));
						channel.write(buffer);
					}
				} else if (key.isReadable()) {
					SocketChannel channel = (SocketChannel)key.channel();
					System.out.println("------readable----- " + channel.getRemoteAddress());
					ByteBuffer buffer = ByteBuffer.allocate(32*1024);
					InetSocketAddress addr = (InetSocketAddress)channel.getRemoteAddress();
					int len = 0;
					Path path = Paths.get("D:\\test", addr.getHostString()+".txt");
					FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
					while ((len=channel.read(buffer)) != -1 && buffer.position() > 0) {
						buffer.flip();
						fileChannel.write(buffer);
						buffer.compact();
					}
					if (len == -1) {
						finished++;
						System.out.println(addr.getHostString() + "**********");
						key.cancel();
					}
				}
			}
		}
		
	}
	
	private Map<SocketAddress, String> urlToMap(Collection<URL> urlColl) {
		Map<SocketAddress, String> addrMap = new HashMap<SocketAddress, String>();
		if (urlColl != null && !urlColl.isEmpty()) {
			SocketAddress addr = null;
			int port = 0;
			for (URL url : urlColl) {
				port = url.getPort() != -1 ? url.getPort() : url.getDefaultPort();
				addr = new InetSocketAddress(url.getHost(), port);
				addrMap.put(addr, url.getPath());
			}
		}
		return addrMap;
	}
	
	private void register(Selector selector, SocketAddress address) throws IOException {
		SocketChannel channel = SocketChannel.open();
		channel.configureBlocking(false);
		channel.connect(address);
		channel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ);
	}
	
}
