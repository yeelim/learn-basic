/**
 * 
 */
package com.yeelim.learn.java.concurrent;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * 当资源不足进入阻塞状态时，内部将把对应的线程入队列，然后根据先进先出的方式获取对应的资源和出队列。
 * @author elim
 * @date 2015-1-26
 * @time 上午11:16:11 
 *
 */
public class AbstractQueueSynchronizerTest {

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
	
	private class ResourceManager extends AbstractQueuedSynchronizer {

		/**
		 * serialVersionUID
		 */
		private static final long serialVersionUID = -6952256663265967478L;

		public ResourceManager(int resourceSize) {
			assert resourceSize > 0;
			this.setState(resourceSize);
		}
		
		public Object getResource() {
			try {
				this.acquireSharedInterruptibly(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
				return null;
			}
			return new Object();
		}
		
		public void releaseSource() {
			this.releaseShared(1);
		}
		
		@Override
		protected int tryAcquireShared(int arg) {//-1为没有获取到，
			for (;;) {
				int state = this.getState();
				int remain = state - arg;
				if (remain >= 0) {
					this.compareAndSetState(state, remain);
					return remain;
				} else {
					return -1;
				}
			}
		}

		@Override
		protected boolean tryReleaseShared(int arg) {
			for (;;) {
				int state = this.getState();
				int remain = state + arg;
				if (this.compareAndSetState(state, remain)) {
					return true;
				}
			}
		}

		@Override
		protected boolean isHeldExclusively() {
			return false;
		}
		
	}
	
}
