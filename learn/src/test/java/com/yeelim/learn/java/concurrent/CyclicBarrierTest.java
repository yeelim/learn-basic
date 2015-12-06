/**
 * 
 */
package com.yeelim.learn.java.concurrent;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * @author elim
 * @date 2015-1-12
 * @time 下午10:51:52 
 *
 */
public class CyclicBarrierTest {

	private final static Logger logger = Logger.getLogger(CyclicBarrierTest.class);
	
	private class BarrierThread extends Thread {
		
		private int id;
		private CyclicBarrier barrier;
		
		public BarrierThread(int id, CyclicBarrier barrier) {
			this.id = id;
			this.barrier = barrier;
		}
		
		public void run() {
			try {
				Thread.sleep(new Random().nextInt(1000));
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			logger.info("Thread" + id + "-----is running, and ready for wait------" + barrier.getNumberWaiting());
			try {
				barrier.await();
				logger.info("Thread" + id + "-----wait end.");
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@Test
	public void test() {
		int parties = 6;
		CyclicBarrier barrier = new CyclicBarrier(parties);
		this.testBarrier1(barrier);
		barrier.reset();
		this.testBarrier1(barrier);
	}
	
	private void testBarrier1(CyclicBarrier barrier) {
		int parties = barrier.getParties();
		for (int i=0; i<parties-1; i++) {
			new BarrierThread(i+1, barrier).start();
		}
		logger.info("------------Main Thread-------Ending");
		try {
			barrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
		logger.info("--------------End--------********");
	}
	
}
