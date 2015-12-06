/**
 * 
 */
package com.yeelim.learn.java.nio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.junit.Assert;

/**
 * 利用Selector进行多路复用其实际上也是使用的单个线程来处理所有的请求，只是其不会针对单个的连接的读或者写进行阻塞，通过select确定了只有在一个连接确认是可以
 * 读或者写的时候才进行读或者写，这样就减少了阻塞的时间。
 * 
 * @author elim
 * @date 2015-3-20
 * @time 下午11:12:11 
 *
 */
public class TimerServer {
	
	private final static Logger logger = Logger.getLogger(TimerServer.class);
	private static volatile boolean end = false;

	public static void main(String args[]) {
		ExecutorService executor = Executors.newFixedThreadPool(15);
		int port = 8088;
		ServerSocketChannel ssc = null;
		try {
			ssc = ServerSocketChannel.open();
			Selector selector = Selector.open();
			ssc.configureBlocking(false);
			ssc.register(selector, SelectionKey.OP_ACCEPT);
			ssc.bind(new InetSocketAddress(port));
			long t1 = 0;
			int count = 0;
			while (!end) {
				selector.select(1000);
				Set<SelectionKey> keys = selector.selectedKeys();
				for (Iterator<SelectionKey> iter = keys.iterator(); iter.hasNext(); ) {
					if (count++ == 0) {
						t1 = System.currentTimeMillis();
					}
					SelectionKey key = iter.next();
					logger.info(key + "---" + keys.size());
					iter.remove();
					if (key.isValid()) {
						if (key.isAcceptable()) {
							SocketChannel sc = ssc.accept();
							sc.configureBlocking(false);
							sc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
							logger.info("1----------" + key.isReadable() + "----" + key.isWritable());
						} else if (key.isReadable()) {
							Assert.assertTrue(key.isWritable());
							executor.execute(new Worker(key));
							//如果这里不加上key.cancel()，那么在Worker进行处理中完成读取之前，这个key会一直存在，因为其一直是处于可读的状态
							key.cancel();
						} else if (key.isWritable()) {
							logger.info("**********Writable*******" + key);
						}
					} else {
						key.cancel();
					}
				}
			}
			long t2 = System.currentTimeMillis();
			logger.info("服务端耗费时间是：" + (t2-t1));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ssc != null) {
				try {
					ssc.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			executor.shutdown();
		}
		
	}
	
	private static void setEnd(boolean end) {
		TimerServer.end = end;
	}
	
	private final static class Worker implements Runnable {

		private final static AtomicInteger counter = new AtomicInteger();
		private SelectionKey key;
		
		private Worker(SelectionKey key) {
			this.key = key;
		}
		
		@Override
		public void run() {
			SocketChannel sc = (SocketChannel) key.channel();
			try {
				TimeUnit.SECONDS.sleep(1); //sleep 1s.
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ByteBuffer buffer = ByteBuffer.allocate(2);
			int len = 0;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				while ((len = sc.read(buffer)) > 0) {
					buffer.flip();
					baos.write(buffer.array(), 0, len);
					buffer.clear();
				}
				String request = new String(baos.toByteArray());
				logger.info(sc + "--------Request------: " + request);
				if (request != null && "Time".equals(request)) {
					String dateStr = new Date().toString();
					ByteBuffer response = ByteBuffer.wrap(dateStr.getBytes());
					sc.write(response);
					sc.close();
					key.cancel();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (counter.incrementAndGet() >= 10) {
					TimerServer.setEnd(true);
				}
			}
		}
		
	}
	
}
