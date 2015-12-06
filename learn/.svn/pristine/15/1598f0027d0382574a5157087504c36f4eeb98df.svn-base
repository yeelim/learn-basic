/**
 * 
 */
package com.yeelim.learn.java.concurrent;

import java.lang.Thread.UncaughtExceptionHandler;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * @author elim
 * @date 2015-2-13
 * @time 下午6:58:04 
 *
 */
public class ThreadTest {
	
	private final static Logger logger = Logger.getLogger(ThreadTest.class);
	
	private class ExceptionWorker implements Runnable {

		@Override
		public void run() {
			int i = 0;
			i = Integer.parseInt("abc");
			logger.info("线程执行完成。i=" + i);
		}
		
	}
	
	private class ExceptionHandler implements UncaughtExceptionHandler {

		@Override
		public void uncaughtException(Thread t, Throwable e) {
			logger.info("线程【" + t + "】抛出了异常：" + e, e);
		}
		
	}
	
	/**
	 * 线程内的异常处理
	 */
	@Test
	public void testException() {
		Thread t = new Thread(new ExceptionWorker());
		t.setUncaughtExceptionHandler(new ExceptionHandler());
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			logger.info("抛出了异常", e);
		}
	}
	
	private final class LocalWorker implements Runnable {
		private int i=0;
		
		public void run() {
			logger.info("变量i的当前值是：" + ++i);
		}
		
	}
	
	/**
	 * 多个线程共用同一个Runnable对象时，多个线程将共享其中的成员变量。
	 * 在该测试中，对应的成员变量的值将最终变为5。
	 * @throws InterruptedException
	 */
	@Test
	public void test2() throws InterruptedException {
		LocalWorker worker = new LocalWorker();
		for (int i=0; i<5; i++) {
			new Thread(worker).start();
		}
		Thread.sleep(1000);
	}
	
	/**
	 * 测试一个线程中是否支持创建多个ThreadLocal变量，测试结果是可以创建多个ThreadLocal变量，且各个变量之间相互独立。
	 */
	@Test
	public void test3() {
		ThreadLocal<Integer> tl1 = new ThreadLocal<Integer>();
		tl1.set(1);
		ThreadLocal<Long> tl2 = new ThreadLocal<Long>();
		tl2.set(1000000L);
		logger.info(tl1.get());
		logger.info(tl2.get());
	}
	
	/**
	 * InheritableThreadLocal变量在子线程中修改后不会影响到父线程中对应变量的值。
	 * @throws InterruptedException
	 */
	@Test
	public void test4() throws InterruptedException {
		final InheritableThreadLocal<Integer> threadLocal = new InheritableThreadLocal<>();
		threadLocal.set(100);
		Thread t = new Thread() {
			public void run() {
				logger.info("---在子线程中访问到的变量值为：" + threadLocal.get());//100
				threadLocal.set(200);
				logger.info("---在子线程中改变线程变量的值后对应的值为：" + threadLocal.get());//200
				Thread t = new Thread() {
					public void run() {
						logger.info("---在子线程2中访问到的变量值为：" + threadLocal.get());//200
						threadLocal.set(300);
						logger.info("---在子线程2中改变线程变量的值后对应的值为：" + threadLocal.get());//300
					}
				};
				t.start();
				try {
					t.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		logger.info("------主线程访问到的线程变量的值为：" + threadLocal.get());//100
		t.start();
		t.join();
		logger.info("------子线程执行完成后主线程访问到的线程变量的值为：" + threadLocal.get());//100
	}

}
