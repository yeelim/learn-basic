/**
 * 
 */
package com.yeelim.learn.java.concurrent;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * @author elim
 * @date 2015-1-26
 * @time 上午9:10:39 
 *
 */
public class SemaphoreTest {

	private final static Logger logger = Logger.getLogger(SemaphoreTest.class);
	private final static Random random = new Random();
	
	
	@Test
	public void test() {
		logger.info("开始执行……");
		long t1 = System.currentTimeMillis();
		int resourceNum	= 10;
		int threadCounts = 50;
		ResourceManager resourceManager = new ResourceManager(resourceNum);
		CountDownLatch latch = new CountDownLatch(threadCounts);
		for (int i=0; i<threadCounts; i++) {
			new Worker(resourceManager, latch).start();
		}
		try {
			latch.await();
		} catch (InterruptedException e) {
			logger.info("主线程被打断了……", e);
		}
		logger.info("整个流程执行完毕，耗费时间：" + (System.currentTimeMillis() - t1) + "ms");
	}
	
	private class Worker extends Thread {
		
		private final ResourceManager resourceManager;
		private final CountDownLatch latch;
		public Worker(ResourceManager resourceManager, CountDownLatch latch) {
			this.resourceManager = resourceManager;
			this.latch = latch;
		}
		
		public void run() {
			Object resource = null;
			try {
				logger.info("准备获取一个资源------");
				resource = this.resourceManager.getResource();
				logger.info("获取到了一个资源------" + resource);
				int sleep = random.nextInt(1000);
				Thread.sleep(sleep);
				logger.info("睡眠了" + sleep + "ms");
			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			} finally {
				if (resource != null) {
					this.resourceManager.releaseSource();
					logger.info("释放了一个资源");
				}
				this.latch.countDown();
			}
		}
		
	}
	
	private class ResourceManager {
		
		private Semaphore signal;
		
		public ResourceManager(int resourceNum) {
			this.signal = new Semaphore(resourceNum, true);
		}
		
		/**
		 * 获取资源
		 * @return
		 * @throws InterruptedException
		 */
		public Object getResource() throws InterruptedException {
			this.signal.acquire();
			return new Object();
		}
		
		/**
		 * 释放资源
		 */
		public void releaseSource() {
			this.signal.release();
		}
		
	}
	
}
