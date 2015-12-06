/**
 * 
 */
package com.yeelim.learn.java.nio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.apache.log4j.Logger;

/**
 * @author elim
 * @date 2015-3-21
 * @time 下午11:18:01 
 *
 */
public class TimerClient {

	private final static Logger logger = Logger.getLogger(TimerClient.class);
	
	private final static class Worker implements Runnable {
		
		public void run() {
			SocketChannel sc = null;
			try {
				sc = SocketChannel.open(new InetSocketAddress("localhost", 8088));
				ByteBuffer wBuffer = ByteBuffer.wrap("Time".getBytes());
				while (wBuffer.hasRemaining()) {//避免出现一次写不完全的情况
					sc.write(wBuffer);
				}
//				sc.write(ByteBuffer.wrap("Time".getBytes()));
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				int len = 0;
				ByteBuffer buffer = ByteBuffer.allocate(1024);
				while ((len = sc.read(buffer)) > 0) {
					baos.write(buffer.array(), 0, len);
					buffer.clear();
				}
				logger.info(sc + "------Current Time is: " + new String(baos.toByteArray()));
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (sc != null) {
					try {
						sc.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
		}
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for (int i=0; i<10; i++) {
			new Thread(new Worker()).start();
		}
	}

}
