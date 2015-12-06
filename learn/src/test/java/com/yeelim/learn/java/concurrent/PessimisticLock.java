/**
 * 
 */
package com.yeelim.learn.java.concurrent;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;


/**
 * 自实现的悲观锁
 * @author elim
 * @date 2015-3-6
 * @time 下午3:44:58 
 *
 */
public class PessimisticLock {

	private final Lock lock = new ReentrantLock();
	private static final Logger logger = Logger.getLogger(OptimisticLock.class);
	private final static Random random = new Random();
	
	/**
	 * 获取锁
	 */
	public void accqure() {
		lock.lock();
	}
	
	/**
	 * 释放锁
	 * @return 释放成功则返回true
	 */
	public boolean release() {
		this.lock.unlock();
		return true;
	}
	
	private static class Worker implements Runnable {

		private final PessimisticLock lock;
		
		public Worker(PessimisticLock lock) {
			this.lock = lock;
		}
		
		public void run() {
			this.lock.accqure();
			logger.info("----获取到了锁");
			int ms = random.nextInt(100);
			try {
				TimeUnit.MILLISECONDS.sleep(ms);	//睡眠一段时间以模拟操作
				logger.info("进行操作------，睡眠了" + ms + "ms。");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (!this.lock.release()) {
				logger.info("*********释放锁失败，准备对之前的操作进行回滚");
				try {
					TimeUnit.MILLISECONDS.sleep(random.nextInt(20));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				logger.info("===========释放锁成功");
			}
		}
		
	}
	
	public static void main(String args[]) throws InterruptedException {
		PessimisticLock lock = new PessimisticLock();
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		for (int i=0; i<100; i++) {
			executorService.execute(new Worker(lock));
		}
		executorService.shutdown();
		executorService.awaitTermination(1, TimeUnit.MINUTES);
		logger.info("terminated status : " + executorService.isTerminated());
	}
	
	
}
