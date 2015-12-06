/**
 * 
 */
package com.yeelim.learn.java.concurrent;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * 线程交互执行测试
 * @author elim
 * @date 2014-11-14
 * @time 下午11:16:40 
 *
 */
public class ThreadCrossTest {

	private final static Logger logger = Logger.getLogger(ThreadCrossTest.class);
	
	@Test
	public void test() {
		int num = 5;
		long t1 = System.currentTimeMillis();
		for (int i=0; i<num; i++) {
			Thread t = new Thread(new NumThread("线程：" + i, i+1, num));
			t.start();
			try {
				t.join();
			} catch (InterruptedException e) {
				logger.warn("线程被打断了", e);
				e.printStackTrace();
			}
		}
		logger.info("方法test的执行时间为：" + (System.currentTimeMillis()-t1));//25390
	}
	
	@Test
	public void test1() {
		int num = 5;
		long t1 = System.currentTimeMillis();
		List<Thread> threadList = new ArrayList<Thread>(num);
		for (int i=0; i<num; i++) {
			Thread t = new Thread(new NumThread("线程（A）：" + i, i+1, num));
			threadList.add(t);
			t.start();
			
		}
		for (Thread t : threadList) {
			t.interrupt();
//			Thread.currentThread().interrupt();
			try {
				t.join();
			} catch (InterruptedException e) {
				logger.warn("线程A被打断了", e);
				e.printStackTrace();
			}
		}
		logger.info("方法test1的执行时间为：" + (System.currentTimeMillis()-t1));//5057
	}
	
	private static class NumThread implements Runnable {

		private String name;
		private int start;
		private int count;
		
		public NumThread(String name, int start, int count) {
			this.name = name;
			this.start = start;
			this.count = count;
		}
		
		@Override
		public void run() {
			for (int i=0; i<count; i++) {
				logger.info(this.name + "===" + (start + i*count));
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					logger.warn(this.name + "的睡眠被打断了", e);
					e.printStackTrace();
				}
			}
		}
		
	}
	
}
