/**
 * 
 */
package com.yeelim.learn.java.concurrent;

import java.util.concurrent.Phaser;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * @author elim
 * @date 2015-1-29
 * @time 下午11:27:40 
 *
 */
public class PhaserTest {

	private final static Logger logger = Logger.getLogger(PhaserTest.class);
	
	@Test
	public void test() {
		int parties = 10;
		Phaser phaser = new Phaser(1);
		Worker worker = null;
		for (int i=1; i<=parties; i++) {
			phaser.register();
			worker = new Worker(i, phaser);
			worker.start();
		}
		logger.info("主线程准备进行Await……");
		phaser.arriveAndAwaitAdvance();
		phaser.arriveAndAwaitAdvance();
		if (phaser.isTerminated()) {
			logger.info("处理失败……");
		}
		logger.info("主线程运行完毕……" + phaser.getUnarrivedParties());//0
		logger.info("参与者数量为：" + phaser.getRegisteredParties());
		phaser.arriveAndDeregister();
	}
	
	private class Worker extends Thread {
		
		private final int id;
		private final Phaser phaser;
		
		public Worker(int id, Phaser phaser) {
			this.id = id;
			this.phaser = phaser;
		}
		
		public void run() {
			logger.info("线程" + id + "已经开始运行！");
			logger.info("线程" + id + "主逻辑运行完毕，准备等待其它线程运行完毕。");
			try {
				if (phaser.getArrivedParties() == 8) {
					logger.info("模拟抛出异常信息……");
					throw new RuntimeException();
				}
			} catch (Exception e) {
				logger.info("**********forceTermination**********当程序运行错误或异常时可以通过这种方式中断phaser，已便告诉其它线程应该中断对应的线程，或者是抛出异常信息可实现对应的事务回滚。");
				phaser.forceTermination();
			}
			if (phaser.isTerminated()) {
				//因为对于phaser中断后调用arrive系列方法都将无法使当前线程在phaser上arrive了，对应的线程逻辑也会在此处中断。所以应该在arriveAndAwait之前也判断
				//一下phaser是否已经中断，如果已经中断也需要执行对应的回滚逻辑。当然了，对于Spring自动事务管理这样的也不需要抛出异常使事务回滚了，因为后续逻辑不再执行的时候对应的事务提交逻辑也不会执行。
				//具体可以试验一下。
			}
			phaser.arriveAndAwaitAdvance();//如果此时phaser已经中断了，那么在中断以后调用arrive的线程将无法再arrive了，对应的线程也将不再等待，即可以直接继续执行后续的代码。
			if (phaser.isTerminated()) {
				logger.info( id + "线程主逻辑运行完毕后可以通过检查是否phaser已经中断，如中断说明有参与者出现异常了，可在此处理需要回滚的信息，或者抛出异常以便Spring的自动事务管理进行回滚。");
//				throw new RuntimeException();
			}
			phaser.arriveAndDeregister();
			logger.info("线程" + id + "运行完毕！");
		}
		
	}
	
}
