/**
 * 
 */
package com.yeelim.learn.java.concurrent;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

/**
 * @author elim
 * @date 2015-3-5
 * @time 下午4:31:39 
 *
 */
public class ThreadPoolExecutorTest {

	private final static Logger logger = Logger.getLogger(ThreadPoolExecutorTest.class);
	private final static Random random = new Random();
	
	public static void main(String args[]) {
		long s1 = System.currentTimeMillis();
		ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 10, 100, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(10));
		for (int i=0; i<50; i++) {
			logger.info("current pool size: " + pool.getPoolSize() + ", completed task nums: " + pool.getCompletedTaskCount() + ", queue size: " + pool.getQueue().size() + ", task count: " + pool.getTaskCount());
			pool.execute(new Worker());
			try {
				TimeUnit.MILLISECONDS.sleep(random.nextInt(10));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		long s2 = System.currentTimeMillis();
		pool.shutdown();
		long s3 = System.currentTimeMillis();
		logger.info((s2-s1) + "---整个过程耗时：" + (s3-s1) + "ms");
	}
	
	private final static class Worker implements Runnable {

		@Override
		public void run() {
			int s = random.nextInt(50) + 1;
			logger.info("准备睡眠" + s + "ms");
			try {
				TimeUnit.MILLISECONDS.sleep(s);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			logger.info("睡眠了" + s + "ms");
		}
		
	}
	
}
