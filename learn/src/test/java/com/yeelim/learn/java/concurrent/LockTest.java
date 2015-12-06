/**
 * 
 */
package com.yeelim.learn.java.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * @author elim
 * @date 2015-1-23
 * @time 上午9:47:21 
 *
 */
public class LockTest {
	
	private final static Logger logger = Logger.getLogger(LockTest.class);

	/**
	 * 判断线程在获取到对应的Lock后执行程序代码的过程中是否可以被中断。结果为false。
	 * @throws InterruptedException
	 */
	@Test
	public void test1() throws InterruptedException {
		ReentrantLock lock = new ReentrantLock();
		Thread t1 = new Thread1(lock);
		t1.start();
		Thread.sleep(100l);
		t1.interrupt();
		try {
			t1.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 供test1()使用的线程定义类
	 * @author elim
	 * @date 2015-1-23
	 * @time 上午10:01:29 
	 *
	 */
	private class Thread1 extends Thread {
		
		private final Lock lock;
		
		public Thread1(Lock lock) {
			this.lock = lock;
		}
		
		public void run() {
			long t1 = System.currentTimeMillis();
			try {
				lock.lockInterruptibly();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				for (int i=0; i<100000; i++) {
					System.out.print(i*i*(i+1)/(i+1));
				} 
			}finally {
				lock.unlock();
			}
			System.out.println();
			System.out.println(System.currentTimeMillis()-t1);
		}
		
	}
	
	@Test
	public void test2() throws InterruptedException {
		Lock lock = new ReentrantLock();
		System.out.println(lock.newCondition() == lock.newCondition());//false
	}
	
	@Test
	public void test3() throws InterruptedException {
		int prodSize = 150;	//生产数量
		int conSize = 50;	//消费数量
		ProductFactory factory = new ProductFactory();
		Productor productor = new Productor(factory, prodSize);
		Consumer consumer = new Consumer(factory, conSize);
		productor.start();
		consumer.start();
		productor.join();	//等待生产者线程的完成
		consumer.join();	//等待消费者线程的完成
	}
	
	/**
	 * 产品工厂
	 * @author elim
	 * @date 2015-1-23
	 * @time 上午11:01:03 
	 *
	 */
	private class ProductFactory {
		
		private final Lock lock;
		private final Condition productCond;	//生产条件
		private final Condition consumerCond;	//消费条件
		private int maxSize = 0;
		private List<Object> objs;
		private int prodSize = 0;	//累计生产数量
		private int consSize = 0;	//累计消费数量
		
		/**
		 * 无容量限制
		 */
		public ProductFactory() {
			this.lock = new ReentrantLock(true);
			this.productCond = lock.newCondition();
			this.consumerCond = lock.newCondition();
			objs = new ArrayList<Object>();
		}
		
		/**
		 * 有容量限制
		 * @param maxSize
		 */
		public ProductFactory(int maxSize) {
			this();
			this.maxSize = maxSize;
		}
		
		/**
		 * 生产一个对象
		 * @return
		 */
		public Object productOne() {
			Object obj = null;
			while (obj == null) {//因为线程有可能被意外的中断
				lock.lock();
				try {
					if (this.maxSize != 0 && objs.size() == maxSize) {
						this.productCond.await();
					}
					obj = new Object();
					this.objs.add(obj);
					this.consumerCond.signal();	//通知消费者可以去取对象了。
					logger.info("生产了一个对象……，产品剩余数量为：" + this.objs.size() + "，累计生产数量为：" + ++prodSize);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
			}
			return obj;
		}
		
		/**
		 * 消费一个对象
		 * @return
		 */
		public Object consumerOne() {
			Object obj = null;
			while (obj == null) {
				lock.lock();
				try {
					if (this.objs.isEmpty()) {
						this.consumerCond.await();	//等待生产者进行生产
					}
					obj = this.objs.remove(0);	//消费第一个
					this.productCond.signal();	//通知生产者可以进行生产了
					logger.info("消费了一个对象……，产品剩余数量为：" + this.objs.size() + "，累计消费数量为：" + ++consSize);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
			}
			return obj;
		}
		
	}
	
	/**
	 * 生产者线程
	 * @author elim
	 * @date 2015-1-23
	 * @time 上午11:11:33 
	 *
	 */
	private class Productor extends Thread {
		private final ProductFactory factory;
		private int size;//生产数量
		private Random random = new Random();
		
		public Productor(ProductFactory factory, int productSize) {
			this.factory = factory;
			this.size = productSize;
		}
		
		public void run() {
			while (size-- > 0) {
				try {
					Thread.sleep(random.nextInt(500));
					factory.productOne();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}	//随机睡眠一秒以内的数字
			}
		}
		
	}
	
	/**
	 * 消费者线程
	 * @author elim
	 * @date 2015-1-23
	 * @time 上午11:11:43 
	 *
	 */
	private class Consumer extends Thread {
		
		private final ProductFactory factory;
		private int size;
		
		public Consumer(ProductFactory factory, int consumerSize) {
			this.factory = factory;
			this.size = consumerSize;
		}
		
		public void run() {
			Random random = new Random();
			while (size-- > 0) {
				try {
					Thread.sleep(random.nextInt(500 * 2));
					factory.consumerOne();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
}
