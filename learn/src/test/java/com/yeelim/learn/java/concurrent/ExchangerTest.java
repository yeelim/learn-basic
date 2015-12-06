/**
 * 
 */
package com.yeelim.learn.java.concurrent;

import java.util.concurrent.Exchanger;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * @author elim
 * @date 2015-1-31
 * @time 下午11:06:31 
 *
 */
public class ExchangerTest {

	private final static Logger logger = Logger.getLogger(ExchangerTest.class);

	@Test
	public void test() {
		Exchanger<Long> exchanger = new Exchanger<Long>();
		new ExchangerThread(exchanger).start();
		new ExchangerThread(exchanger).start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private class ExchangerThread extends Thread {
		private final Exchanger<Long> exchanger;
		
		public ExchangerThread(Exchanger<Long> exchanger) {
			this.exchanger = exchanger;
		}
		
		public void run() {
			String threadName = Thread.currentThread().getName();
			try {
				long t1 = System.currentTimeMillis();
				logger.info("线程【"+threadName+"】准备交换【"+t1+"】");
				long t2 = exchanger.exchange(t1);
				logger.info("线程【"+threadName+"】通过Exchanger与另外一个线程交换了【"+t1+"】，自己获取了【"+t2+"】");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
