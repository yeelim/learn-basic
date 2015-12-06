/**
 * 
 */
package com.yeelim.learn.jdk;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.junit.Test;

/**
 * @author elim
 * @date 2014-12-2
 * @time 下午11:24:23 
 *
 */
public class SSLTest {

	private final static class MyTrustManager implements X509TrustManager {

		@Override
		public void checkClientTrusted(X509Certificate[] arg0, String arg1)
				throws CertificateException {
			
		}

		@Override
		public void checkServerTrusted(X509Certificate[] arg0, String arg1)
				throws CertificateException {
			
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
		
	}
	
	@Test
	public void testServer() throws UnknownHostException, IOException, NoSuchAlgorithmException, KeyManagementException {
		SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
		sslContext.init(new KeyManager[] {}, new TrustManager[] {new MyTrustManager()}, new SecureRandom());
		SSLServerSocketFactory sslServerSocketFactory = sslContext.getServerSocketFactory();
		SSLServerSocket sslServerSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(8888);
		SSLSocket sslSocket = (SSLSocket)sslServerSocket.accept();
		System.out.println(sslSocket.getInputStream().available());
		sslSocket.getOutputStream().write("hello client socket".getBytes());
		sslSocket.close();
		sslServerSocket.close();
		System.out.println(sslSocket.getChannel());;
		/*ServerSocketChannel serverSocketChannel = sslServerSocket.getChannel();
		SocketChannel socketChannel = serverSocketChannel.accept();
		ByteBuffer buffer = ByteBuffer.allocate(32 * 1024);
		int byteSize = socketChannel.read(buffer);
		System.out.println(byteSize + "-----------");
		System.out.println(new String(buffer.array()));
		buffer.clear();
		buffer.put("hello SSLSocket".getBytes());*/
	}
	
	@Test
	public void testClient() throws UnknownHostException, IOException, NoSuchAlgorithmException, KeyManagementException {
		SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
		sslContext.init(new KeyManager[] {}, new TrustManager[] {new MyTrustManager()}, new SecureRandom());
		SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
		SSLSocket sslSocket = (SSLSocket)sslSocketFactory.createSocket("localhost", 8888);
		sslSocket.getOutputStream().write("hello server socket".getBytes());
		System.out.println(sslSocket.getInputStream().available());
		sslSocket.close();
	}
	
	@Test
	public void test1() throws IOException {
		try (ServerSocket serverSocket = new ServerSocket(8888);) {
			Socket socket = serverSocket.accept();
			System.out.println(socket);
			SocketChannel channel = socket.getChannel();
			System.out.println(channel);
		}
	}

	@Test
	public void test2() throws UnknownHostException, IOException {
		try (Socket socket = new Socket("localhost", 8888)) {
			SocketChannel socketChannel = socket.getChannel();
			System.out.println(socketChannel);
		}
	}
	
	@Test
	public void test3() throws IOException {
		try (ServerSocketChannel ssc = ServerSocketChannel.open();) {
			ssc.bind(new InetSocketAddress(8888));
			SocketChannel sc = ssc.accept();
			ByteBuffer buffer = ByteBuffer.allocate(32*1024);
			sc.read(buffer);
			System.out.println(new String(buffer.array()));
			buffer.clear();
			buffer.put("hello client".getBytes());
			buffer.flip();
			sc.write(buffer);
		}
		
	}
	
	@Test
	public void test4() throws IOException {
		try (SocketChannel sc = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8888))) {
			ByteBuffer buffer = ByteBuffer.wrap("hello server".getBytes());//wrap以后，对应Buffer的状态就是供读取的，其position将为0，limit将为对应ByteArray的长度
			System.out.println(buffer.position() + "---" + buffer.limit());//0,12
			sc.write(buffer);
			buffer.clear();
			sc.read(buffer);
			System.out.println(new String(buffer.array()));
		}
	}
}
