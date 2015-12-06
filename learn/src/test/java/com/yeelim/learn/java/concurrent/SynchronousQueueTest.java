/**
 * 
 */
package com.yeelim.learn.java.concurrent;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * 
 * SynchronousQueue的用处在于在两个线程之间同步某个对象。其虽然实现了BlockingQueue接口，但是其内部并不储存对应的对象(是指通过size()等方法获取的内容都是0，
 * 由于多线程的关系其内部肯定还是会保存对应的信息的)，调用其put()方法会进行阻塞，直到有线程通过take()或poll()方法获取对应的对象，或者之前已经有线程在等待获取对象了。
 * 调用take()方法也会进行阻塞，直到有线程调用了put()或offer()方法来存放对应的对象，或者之前已经有线程调用了put()方法进入了阻塞状态。
 * @author elim
 * @date 2015-2-1
 * @time 下午5:20:38 
 *
 */
public class SynchronousQueueTest {

	private final static Logger logger = Logger.getLogger(SynchronousQueueTest.class);
	private final static AtomicLong  number = new AtomicLong();
	
	@Test
	public void test() {
		SynchronousQueue<Long> syncQueue = new SynchronousQueue<>();
		for (int i=0; i<10; i++) {
			new Worker(syncQueue, i%2==0).start();
		}
		logger.info("主线程准备sleep。");
		try {
			Thread.sleep(1000 * 5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		logger.info("主线程sleep完成。");
	}
	
	private final class Worker extends Thread {
		
		private final SynchronousQueue<Long> syncQueue;
		private boolean take;	//true为获取，false为存放。
		
		public Worker(SynchronousQueue<Long> syncQueue, boolean take) {
			this.syncQueue = syncQueue;
			this.take = take;
		}
		
		public void run() {
			try {
				if (take) {
					logger.info("准备从SynchronousQueue中获取一个对象。");
					Long takeObj = this.syncQueue.take();
					logger.info("从SynchronousQueue中获取到了一个对象： " + takeObj);
				} else {
					logger.info("准备存放一个对象到SynchronousQueue中。");
					Long putObj = number.incrementAndGet();
					this.syncQueue.put(putObj);
					logger.info("存放了一个对象到SynchronousQueue中。" + putObj);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
