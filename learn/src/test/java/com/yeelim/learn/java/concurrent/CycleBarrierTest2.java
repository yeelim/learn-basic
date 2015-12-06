/**
 * 
 */
package com.yeelim.learn.java.concurrent;

import java.util.ArrayList;
import java.util.List;
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
public class CycleBarrierTest2 {

	private final static Logger logger = Logger.getLogger(CycleBarrierTest2.class);
	private final static List<Thread> partyThreads = new ArrayList<Thread>();
	
	@Test
	public void test() {
		partyThreads.clear();
		partyThreads.add(Thread.currentThread());
		int parties = 10;
		CyclicBarrier barrier = new CyclicBarrier(parties);
		Worker worker = null;
		for (int i=1; i<parties; i++) {
			worker = new Worker(i, barrier);
			partyThreads.add(worker);
			worker.start();
		}
		logger.info("主线程准备进行Await……");
		try {
			barrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
		
		logger.info("主线程运行完毕……" + barrier.getNumberWaiting());
		logger.info("参与者数量为：" + barrier.getParties());
	}
	
	public void interruptAll(List<Thread> threads) {
		if (threads != null && !threads.isEmpty()) {
			for (Thread thread : threads) {
				thread.interrupt();
			}
		}
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
			if (barrier.getNumberWaiting() == 9) {
				//抛出异常后其它线程都将处于等待状态
				try {
					throw new RuntimeException("ABCDEFGHI");
				} catch (Exception e) {
					e.printStackTrace();
					CycleBarrierTest2.this.interruptAll(partyThreads);
				}
			}
			try {
				this.barrier.await();
			} catch (Exception e) {
				CycleBarrierTest2.this.interruptAll(partyThreads);
				//被打断后就抛出异常，阻止流程继续运行，对于有事务控制的逻辑，也可以阻止事务的提交。
				throw new RuntimeException(String.valueOf(id));
			}
			logger.info("线程" + id + "运行完毕！");
		}
		
	}
	
	
}
