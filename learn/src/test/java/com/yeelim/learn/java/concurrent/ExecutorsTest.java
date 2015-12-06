/**
 * 
 */
package com.yeelim.learn.java.concurrent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * @author elim
 * @date 2015-2-4
 * @time 下午4:20:33 
 *
 */
public class ExecutorsTest {

	private final static Logger logger = Logger.getLogger(ExecutorsTest.class);
	
	@Test
	public void testCallable() {
		ExecutorService executorService = Executors.newCachedThreadPool();
		long t1 = System.currentTimeMillis();
		Future<Long> future = executorService.submit(new Callable<Long>() {

			@Override
			public Long call() throws Exception {
				Long sleep = new Random().nextInt(3000) + 1000L;
				Thread.sleep(sleep);
				return sleep;
			}
			
		});
		try {
			//会阻塞，直到对应的Callable或Runnable执行完成（包括正常完成和异常完成），并返回对应的结果。
			//如果执行抛出异常，也会抛出ExecutionException。
			Long result = future.get();
			long t2 = System.currentTimeMillis();
			logger.info("调度的结果是：" + result);
			logger.info("调度耗费的时间是：" + (t2-t1) + "ms");
		} catch (InterruptedException | ExecutionException e) {
			logger.error("callable调度失败", e);
		}
		
	}
	
	/**
	 * 测试ExecutorService调用Runnable接口返回Future对象。
	 */
	@Test
	public void testRunnable() {
		ExecutorService executorService = Executors.newCachedThreadPool();
		long t1 = System.currentTimeMillis();
		Future<?> future =  executorService.submit(new Runnable() {

			@Override
			public void run() {
				Long sleep = new Random().nextInt(3000) + 1000L;
				try {
					Thread.sleep(sleep);
					logger.info("Runnable接口睡眠的时间是：" + sleep);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		});
		try {
			//会阻塞，直到对应的Callable或Runnable执行完成（包括正常完成和异常完成），并返回对应的结果。
			//如果执行抛出异常，也会抛出ExecutionException。
			future.get();
			long t2 = System.currentTimeMillis();
			logger.info("调度耗费的时间是：" + (t2-t1) + "ms");
		} catch (InterruptedException | ExecutionException e) {
			logger.error("callable调度失败", e);
		}
	}
	
	/**
	 * 测试通过Future对任务进行取消
	 * 测试结果：确实可以中断，线程运行5秒后就不再运行。
	 */
	@Test
	public void testCancelTask() {
		
		ExecutorService executorService = Executors.newCachedThreadPool();
		Future<?> future = executorService.submit(new Runnable() {
			
			public void run() {
				
				int times = 30;
				for (int i=0; i<times; ) {
					long t1 = System.currentTimeMillis();
					while (System.currentTimeMillis()-t1<1000) {
						
					}
					//5秒时中断标志为true，之后清除，接着睡眠，其它的都为false。
					logger.info("睡眠了" + ++i + "秒, 还剩" + (times-i) + "秒，是否中断：" + Thread.interrupted());
				}
				
			}
			
		});
		long t1 = System.currentTimeMillis();
		while (System.currentTimeMillis() - t1 < 5*1000) {
			//空循环
		}
		boolean canceled = future.cancel(true);//参数表示如果对应的线程已经在执行了，是否需要对其进行中断，即设置中断标志。如果还没有执行，那么会将其从队列中移除，这样对应的任务将不会执行。
		logger.info("任务取消完成……，取消结果是：" + canceled);
		try {
			Thread.sleep(20 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testExecute() throws InterruptedException {
		
		class TestRunnable implements Runnable {

			@Override
			public void run() {
				logger.info("--------------Start-----------");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				logger.info("-------------End--------------");
			}
			
		};
		
		ExecutorService executorService = Executors.newCachedThreadPool();
		executorService.execute(new TestRunnable());
		executorService.execute(new TestRunnable());
		
		Thread.sleep(3000);
		ThreadPoolExecutor executor = (ThreadPoolExecutor) executorService;
		logger.info(executor.getCompletedTaskCount() + "---" + executor.getLargestPoolSize());
	}
	
	@Test
	public void test() {
		int a = -5;
		int b = ~a;//取反减1。
		logger.info(a);
		logger.info(b);
		
		logger.info(5^6);//0101 0110
		logger.info(Integer.MAX_VALUE);
		logger.info(~Integer.MAX_VALUE);
		/*for (int i=0; i<30000; i++) {
			logger.info(i + " ******** " + ~i );
		}*/
	}
	
	@Test
	public void testInvokeAll() throws InterruptedException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		//invokeAll会等待所有任务的完成后再继续往后执行。
		List<Future<Long>> results = executorService.invokeAll(Collections.singleton(new Callable<Long>() {

			@Override
			public Long call() throws Exception {
				logger.info("准备睡眠3s");
				Thread.sleep(3000);
				logger.info("睡眠完成。");
				return 3000L;
			}
			
		}));
		
		logger.info("主线程运行完毕。" + results);
	}
	
	/**
	 * 任何一个任务成功完成了或者所有的任务都执行失败了以后都将返回，否则对invokeAny()的调用将进入阻塞。
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@Test
	public void testInvokeAny() throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		class Task implements Callable<Integer> {

			@Override
			public Integer call() throws Exception {
				long s = new Random().nextInt(10) + 1;
				logger.info("准备睡眠的时间是：" + s + "s");
				TimeUnit.SECONDS.sleep(s);
				if (new Random().nextBoolean()) {
					logger.info("成功返回！" + s);
					return (int)s;
				}
				logger.info("准备抛出异常！" + s);
				throw new Exception("------" + s);
			}
			
		};
		List<Task> tasks = new ArrayList<Task>();
		for (int i=0; i<10; i++) {
			tasks.add(new Task());
		}
		long s = System.currentTimeMillis();
		executorService.invokeAny(tasks);
		long s2 = System.currentTimeMillis();
		logger.info("程序运行完成，耗费时间是：" + (s2 - s));
	}
	
	public static void main(String args[]) throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		class Task implements Callable<Integer> {

			@Override
			public Integer call() throws Exception {
				long s = new Random().nextInt(10) + 1;
				logger.info("准备睡眠的时间是：" + s + "s");
				TimeUnit.SECONDS.sleep(s);
				if (new Random().nextBoolean()) {
					logger.info("成功返回！" + s);
					return (int)s;
				}
				logger.info("准备抛出异常！" + s);
				throw new Exception("------" + s);
			}
			
		};
		List<Task> tasks = new ArrayList<Task>();
		for (int i=0; i<10; i++) {
			tasks.add(new Task());
		}
		long s = System.currentTimeMillis();
		Integer result = executorService.invokeAny(tasks);
		long s2 = System.currentTimeMillis();
		logger.info("程序运行完成，耗费时间是：" + (s2 - s) + "，结果是：" + result);
		executorService.shutdown();
	}
	
	/**
	 * 以固定的速度进行周期性的调度。当任务运行的时间比周期长的时候下一次运行时间将会推迟，比如说在本示例代码中，任务是每隔1秒周期性的执行，但是
	 * 对应的任务每次运行需要2秒，所以每个任务都将每次以2秒为间隔周期性的执行。即第一个任务在0秒执行，以后都将在2、4、6、8、10...秒执行。
	 * @throws InterruptedException 
	 */
	@Test
	public void testAtFixedRate() throws InterruptedException {
		
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(500);
		for (int i=0; i<2; i++) {
			executor.scheduleAtFixedRate(new ScheduledTask(i), 0, 1000, TimeUnit.MILLISECONDS);
		}
		TimeUnit.SECONDS.sleep(20);
		logger.info("---------OVER--------");
	}
	
	/**
	 * 以固定的速度进行周期性的调度。当任务运行的时间比周期短的时候下一次运行时间将是上一次运行时间以后固定延迟时间后的时点，比如说在本示例代码中，任务是每隔5秒周期性的执行，但是
	 * 对应的任务每次运行只需要2秒，每个任务的运行时点应该是0,5,10,15......。
	 * @throws InterruptedException 
	 */
	@Test
	public void testAtFixedRate2() throws InterruptedException {
		
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(500);
		for (int i=0; i<2; i++) {
			executor.scheduleAtFixedRate(new ScheduledTask(i), 0, 5000, TimeUnit.MILLISECONDS);
		}
		TimeUnit.SECONDS.sleep(60);
		logger.info("---------OVER--------");
	}
	
	/**
	 * scheduleWithFixedDelay()是在上一次的任务运行完成后再经过延迟的时间后再执行对应的任务。如下示例中，每次调度都延迟5s，而每个
	 * 任务的运行时间是2s，所以该示例中任务的运行时点应该是0,7,14,21.....,具体可以查看JAVA API中对scheduleWithFixedDelay()方法
	 * 中delay参数的解释。
	 * @throws InterruptedException
	 */
	@Test
	public void testWithFixedDelay() throws InterruptedException {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(100);
		for (int i=0; i<2; i++) {
			executor.scheduleWithFixedDelay(new ScheduledTask(i), 0, 5000, TimeUnit.MILLISECONDS);
		}
		TimeUnit.SECONDS.sleep(20);
		logger.info("--------OVER---------");
	}
	
	private final static class ScheduledTask implements Runnable {

		private final int id;
		
		public ScheduledTask(int id) {
			this.id = id;
		}
		
		@Override
		public void run() {
			logger.info("开始执行------------" + id);
			try {
				TimeUnit.MILLISECONDS.sleep(1000 * 2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 测试任务的取消
	 * 任务的取消是通过Future接口的cancel(boolean)方法进行的。
	 * boolean类型的参数表示如果任务已经在运行了是否需要打断，如果为true，此处只是给当前线程加入Interrupted状态。如果
	 * 为false，则只有处于未运行状态的任务才能被取消。
	 * @throws InterruptedException 
	 */
	@Test
	public void testTaskCancel() throws InterruptedException {
		
		ExecutorService executor = Executors.newCachedThreadPool();
		Future<?> future = executor.submit(new CanceldTask());
		TimeUnit.SECONDS.sleep(1);
		future.cancel(true);
		
		executor.shutdown();
		executor.awaitTermination(10, TimeUnit.SECONDS);
		
	}
	
	private static class CanceldTask implements Runnable {

		@Override
		public void run() {
			while (true) {
				try {
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	/**
	 * 对于FutureTask而言，其实现了Runnable接口。其运行完成（不管失败还是成功）后将会调用其中的done()方法。具体可参看源代码。
	 * @throws InterruptedException
	 */
	@Test
	public void testFutureTask() throws InterruptedException {
		Runnable r = new Runnable() {

			@Override
			public void run() {
				logger.info("--------任务正在运行--------");
			}
			
		};
		
		FutureTask<Void> task = new MyFutureTask(r, null);
		Thread t = new Thread(task);
		t.start();
		t.join();
		logger.info("主线程执行完成。。。。。。。");
	}
	
	private final static class MyFutureTask extends FutureTask<Void> {

		/**
		 * @param runnable
		 * @param result
		 */
		public MyFutureTask(Runnable runnable, Void result) {
			super(runnable, result);
		}

		@Override
		protected void done() {
			logger.info("=====================Over，来自回调函数done()");
		}
		
	}
	
	
}
