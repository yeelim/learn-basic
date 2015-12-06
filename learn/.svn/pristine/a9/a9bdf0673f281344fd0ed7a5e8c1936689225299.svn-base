/**
 * 
 */
package com.yeelim.learn.java.concurrent;

import java.util.Random;
import java.util.concurrent.Phaser;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * @author elim
 * @date 2015-2-1
 * @time 上午11:38:32 
 *
 */
public class PhaserTest2 {

	private final static Logger logger = Logger.getLogger(PhaserTest2.class);
	
	@Test
	public void test() {
		Phaser phaser = new Phaser(1);
		for (int i=0; i<3; i++) {
			phaser.register();
			new Worker(phaser, 1).start();
		}
		logger.info("---main thread is ready to waiting first------");
		phaser.arriveAndAwaitAdvance();
		while (phaser.getUnarrivedParties() != 1) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			logger.info("------main thread is over---------,current phaser is : " + phaser.getPhase() + "-and " + phaser.getUnarrivedParties() + "---" + phaser.getRegisteredParties());
			phaser.arriveAndAwaitAdvance();
		}
		phaser.arriveAndDeregister();	//最后的主线程到达并取消注册。
		logger.info("------main thread is over---------,current phaser is : " + phaser.getPhase() + "-and " + phaser.getUnarrivedParties() + "---" + phaser.getRegisteredParties());
	}
	
	private class Worker extends Thread {
		
		private int times;
		private final Phaser phaser;
		public Worker(Phaser phaser, int times) {
			this.phaser = phaser;
			this.times = times;
		}
		
		public void run() {
			logger.info(times + "------------Start-------------, current phaser is : " + phaser.getPhase());
			try {
				Thread.sleep(100 + new Random().nextInt(200));	//睡眠100ms，让效果更加明显。
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			phaser.arriveAndAwaitAdvance();
			if (times < 3) {
				Phaser other = new Phaser(this.phaser);	//this.phaser作为other的父Phaser，此时other将自动注册到其父Phaser中，即this.phaser，并作为父Phaser的一个参与者
				other.register();
				new Worker(other, times+1).start();
			}
			phaser.arriveAndDeregister();
			logger.info(times + "------------End-------------, current phaser is : " + phaser.getPhase() + "---" + phaser.getRegisteredParties());
		}
		
	}
	
}
