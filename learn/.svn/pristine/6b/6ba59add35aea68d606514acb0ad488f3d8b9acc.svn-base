/**
 * 
 */
package com.yeelim.learn.java.concurrent;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;

/**
 * 
 * 乐观锁
 * @author elim
 * @date 2015-3-6
 * @time 下午3:02:53 
 *
 */
public class OptimisticLock {

	private AtomicLong version = new AtomicLong();
	private ThreadLocal<Long> versionLocal = new ThreadLocal<Long>();
	
	private static final Logger logger = Logger.getLogger(OptimisticLock.class);
	private final static Random random = new Random();
	
	/**
	 * 获取锁
	 */
	public void accqure() {
		long v = version.getAndIncrement() + 1;
		versionLocal.set(v);
	}
	
	/**
	 * 释放锁
	 * @return 释放成功则返回true
	 */
	public boolean release() {
		if (this.version.get() == this.versionLocal.get()) {
			long v1 = this.versionLocal.get();
			this.version.compareAndSet(v1, v1-1);	//可能会修改成功，也可能会修改失败。
			return true;
		}
		return false;
	}
	
	private static class Worker implements Runnable {

		private final OptimisticLock lock;
		
		public Worker(OptimisticLock lock) {
			this.lock = lock;
		}
		
		public void run() {
			this.lock.accqure();
			OptimisticLock.logger.info("----获取到了锁");
			int ms = OptimisticLock.random.nextInt(100);
			try {
				TimeUnit.MILLISECONDS.sleep(ms);	//睡眠一段时间以模拟操作
				OptimisticLock.logger.info("进行操作------，睡眠了" + ms + "ms。");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (!this.lock.release()) {
				OptimisticLock.logger.info("*********释放锁失败，准备对之前的操作进行回滚");
				try {
					TimeUnit.MILLISECONDS.sleep(random.nextInt(20));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				OptimisticLock.logger.info("===========释放锁成功");
			}
		}
		
	}
	
	public static void main(String args[]) throws InterruptedException {
		OptimisticLock lock = new OptimisticLock();
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		for (int i=0; i<100; i++) {
			executorService.execute(new Worker(lock));
		}
		executorService.shutdown();
		executorService.awaitTermination(1, TimeUnit.MINUTES);
		logger.info("terminated status : " + executorService.isTerminated());
	}
	
}
