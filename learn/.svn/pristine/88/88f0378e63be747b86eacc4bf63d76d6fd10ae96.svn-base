/**
 * 
 */
package com.yeelim.learn.java.concurrent;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * @author elim
 * @date 2015-1-12
 * @time 下午9:22:03 
 *
 */
public class CountDownLatchTest {

	private final static Logger logger = Logger.getLogger(CountDownLatchTest.class);
	
	private class LatchThread extends Thread {

		private final int id;
		private CountDownLatch latch;
		
		public LatchThread(int id, CountDownLatch latch) {
			this.id = id;
			this.latch = latch;
		}
		
		public void run() {
			try {
				Thread.sleep(new Random().nextInt(1000));
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			logger.info("--------------Thread" + id + " is running--------");
			latch.countDown();
			try {
				latch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			logger.info("--------------Thread" + id + " run over----------");
		}
		
	}
	
	@Test
	public void test() {
		for (int i=0; i<2; i++) {
			this.testLatch();
		}
	}
	
	private void testLatch() {
		CountDownLatch latch = new CountDownLatch(10);
		for (int i=0; i<10; i++) {
			new LatchThread(i+1, latch).start();
		}
		logger.info("------ready for waiting all LatchThread run over---------");
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		logger.info("---------------------------Run Over------------------------------");
		System.out.println();
	}
}
