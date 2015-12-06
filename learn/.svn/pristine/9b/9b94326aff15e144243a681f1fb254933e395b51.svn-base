/**
 * 
 */
package com.yeelim.learn.java.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * @author elim
 * @date 2015-1-28
 * @time 下午11:20:13 
 *
 */
public class CycleBarrierTest3 {

	private final static Logger logger = Logger.getLogger(CycleBarrierTest3.class);
	
	
	public static void main(String args[]) {
		new CycleBarrierTest3().test();
	}
	@Test
	public void test() {
		int parties = 10;
		CyclicBarrier barrier = new CyclicBarrier(parties);
		Worker worker = null;
		for (int i=1; i<parties; i++) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			worker = new Worker(i, barrier);
			worker.start();
		}
		logger.info("主线程准备进行Await……");
		try {
			barrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
		
		logger.info("主线程运行完毕……" + barrier.getNumberWaiting());
		logger.info(barrier.isBroken() + "参与者数量为：" + barrier.getParties());
	}
	
	private class Worker extends Thread {
		
		private final int id;
		private final CyclicBarrier barrier;
		
		public Worker(int id, CyclicBarrier barrier) {
			this.id = id;
			this.barrier = barrier;
		}
		
		public void run() {
			logger.info("线程" + id + "已经开始运行！");
			if (barrier.getNumberWaiting() >= 5) {
				//抛出异常后其它线程都将处于等待状态
				try {
					throw new RuntimeException("***************");
				} catch (Exception e) {
					e.printStackTrace();
					//中断当前线程可以使CyclicBarrier处于中断状态。进而可以中断所有处于等待状态的参与者线程，即对同一个barrier的await都将抛出中断异常，即broken异常。
					Thread.currentThread().interrupt();	
				}
			}
			try {
				this.barrier.await();
			} catch (Exception e) {
				//被打断后就抛出异常，阻止流程继续运行，对于有事务控制的逻辑，也可以阻止事务的提交。
				e.printStackTrace();
				logger.error(this.barrier.isBroken() + "-----线程id：" + id, e);
			}
			logger.info("线程" + id + "运行完毕！");
		}
		
	}
	
	
}
