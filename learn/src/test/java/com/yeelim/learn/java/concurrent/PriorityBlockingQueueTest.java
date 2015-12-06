/**
 * 
 */
package com.yeelim.learn.java.concurrent;

import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * @author elim
 * @date 2015-3-11
 * @time 下午10:43:08 
 *
 */
public class PriorityBlockingQueueTest {

	private final static Logger logger = Logger.getLogger(PriorityBlockingQueueTest.class);
	
	private final static class User implements Comparable<User> {

		private final int id;
		
		public User(int id) {
			this.id = id;
		}
		
		@Override
		public int compareTo(User o) {
			if (this.id == o.id) {
				return 0;
			} else if (this.id < o.id) {
				return -1;
			} else {
				return 1;
			}
		}
		
		public String toString() {
			return "id=" + this.id;
		}
		
	}
	
	/**
	 * 测试结果是PriorityBlockingQueue中的元素将按照元素compareTo()后的结果按从小到大进行排序。
	 * @throws InterruptedException
	 */
	@Test
	public void test() throws InterruptedException {
		PriorityBlockingQueue<User> queue = new PriorityBlockingQueue<>();
		Random random = new Random();
		for (int i=0; i<100; i++) {
			queue.put(new User(random.nextInt(100)));
		}
		
		for (int i=0; i<100; i++) {
			logger.info(queue.take());
		}
		
		
	}
	
}
