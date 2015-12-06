/**
 * 
 */
package com.yeelim.learn.java.concurrent;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * @author elim
 * @date 2015-2-1
 * @time 下午11:17:11 
 *
 */
public class BlockingQueueTest {
	
	private final static Logger logger = Logger.getLogger(BlockingQueueTest.class);
	private final static AtomicLong	 number = new AtomicLong();
	private final static Random random = new Random();
	
	@Test
	public void test() {
		BlockingQueue<Long> blockingQueue = new LinkedBlockingQueue<>(10);
		for (int i=0; i<10; i++) {
			new Producer(blockingQueue).start();
			if (i%2 == 0) {
				new Consumer(blockingQueue).start();
			}
		}
		try {
			Thread.sleep(1000 * 20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private final class Producer extends Thread {
		
		private final BlockingQueue<Long> blockingQueue;
		
		private Producer(BlockingQueue<Long> blockingQueue) {
			this.blockingQueue = blockingQueue;
		}
		
		public void run() {
			try {
				Thread.sleep(random.nextInt(500) + 500);
			} catch (InterruptedException e2) {
				e2.printStackTrace();
			}
			long n1 = number.incrementAndGet();
			logger.info("准备调用add()方法往blockingQueue里面添加一个元素：" + n1);
			blockingQueue.add(n1);//AbstractQueue底层是直接调用offer方法，如果返回为false，则抛出异常，表名队列已经满了。
			long n2 = number.incrementAndGet();
			boolean offerResult = blockingQueue.offer(n2);//满了就直接返回false，否则直接插入，不阻塞
			logger.info("调用offer往blockingQueue里面添加一个元素：" + n2 + "，结果是：" + offerResult);
			long n3 = number.incrementAndGet();
			try {
				boolean offerResult2 = blockingQueue.offer(n3, 1000, TimeUnit.MILLISECONDS);
				logger.info("调用offer(time,unit)往blockingQueue添加一个元素：" + n3 + "，结果是：" + offerResult2);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			try {
				long n4 = number.incrementAndGet();
				logger.info("准备调用put方法往blockingQueue中添加一个元素：" + n4);
				blockingQueue.put(n4);
				logger.info("调用put方法往blockingQueue中添加元素：" + n4 + "成功");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private final class Consumer extends Thread {
		
		private final BlockingQueue<Long> blockingQueue;
		
		private Consumer(BlockingQueue<Long> blockingQueue) {
			this.blockingQueue = blockingQueue;
		}
		
		public void run() {
			try {
				Thread.sleep(random.nextInt(500) + 500);
			} catch (InterruptedException e2) {
				e2.printStackTrace();
			}
			Long n1 = blockingQueue.peek();
			logger.info("通过peek()方法查看队首元素，对应值为：" + n1);
			try {
				long n2 = blockingQueue.remove();	//从队首移除一个元素，如果队列为空则抛出异常。
				logger.info("调用remove()方法从队首移除元素：" + n2);
			} catch (Exception e) {
				
			}
			Long n3 = blockingQueue.poll();	//从队首移除一个元素，不阻塞，立即返回，队列为空则为null
			logger.info("调用poll()从队列移除一个元素：" + n3);
			try {
				Long n4 = blockingQueue.poll(1000, TimeUnit.MILLISECONDS);//从队列移除一个元素，队列为空时，则最多等待指定1000ms。
				logger.info("调用poll(time,unit)移除一个元素：" + n4);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				long n5 = blockingQueue.take();	//从队首移除一个元素，队列为空时将阻塞。
				logger.info("调用take()方法从队首移除一个元素：" + n5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
