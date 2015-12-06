/**
 * 
 */
package com.yeelim.learn.java.concurrent;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * @author elim
 * @date 2015-2-5
 * @time 下午5:03:13 
 *
 */
public class CompletionServiceTest {

	private final static Logger logger = Logger.getLogger(CompletionServiceTest.class);
	private final static AtomicInteger number = new AtomicInteger();
	private final static Random random = new Random();
	
	@Test
	public void test() {
		ExecutorService executor = Executors.newFixedThreadPool(3);
		CompletionService<Integer> completionService = new ExecutorCompletionService<>(executor);
		new Thread(new Producer(completionService)).start();
		new Thread(new Consumer(completionService)).start();
		try {
			Thread.sleep(3 * 60*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private final static class Producer implements Runnable {

		private CompletionService<Integer> completionService;
		
		public Producer(CompletionService<Integer> completionService) {
			this.completionService = completionService;
		}
		
		public void run() {
			for (int i=0; i<100; i++) {
				try {
					Thread.sleep(random.nextInt(1000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				completionService.submit(new Callable<Integer>() {

					@Override
					public Integer call() throws Exception {
						int num = number.incrementAndGet();
						logger.info("-----生产者产生了一个Number：" + num);
						return num;
					}
					
				});
				try {
					Thread.sleep(random.nextInt(1000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private final static class Consumer implements Runnable {
		
		private CompletionService<Integer> completionService;
		
		public Consumer(CompletionService<Integer> completionService) {
			this.completionService = completionService;
		}

		@Override
		public void run() {
			int i=0;
			while (i<100) {
				try {
					Thread.sleep(random.nextInt(1000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Future<Integer> future = this.completionService.poll();
				logger.info("*****获取到了一个future:" + future);
				if (future != null) {
					i++;
					try {
						logger.info("=====获取到的future的值为：" + future.get());
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}
				}
				try {
					Thread.sleep(random.nextInt(1000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
}
