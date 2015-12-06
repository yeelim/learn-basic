/**
 * 
 */
package com.yeelim.learn.java.concurrent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * @author elim
 * @date 2015-2-15
 * @time 下午8:19:18 
 *
 */
public class SemaphoreTest2 {
	
	private final static Logger logger = Logger.getLogger(SemaphoreTest2.class);

	private final static class Printer {
		
		private int id;
		public Printer(int id) {
			this.id = id;
		}
		
		public void print() {
			logger.info("id为" + id + "的打印机正在打印。。。。。。");
			try {
				TimeUnit.MILLISECONDS.sleep(1000);	//每次打印需要花费的时间为1s
				logger.info("id为" + id + "的打印机打印完毕。");
			} catch (InterruptedException e) {     
				logger.warn("id为" + id + "的打印机进行打印时线程被中断。。。。。。");
			}
		}
		
	}
	
	private final static class PrinterManager {
		
		private final Semaphore semaphore;
		private final BlockingQueue<Printer> printerQueue;
		private final Map<Printer, Integer> printerUseTimes;
		
		public PrinterManager(Collection<Printer> printers) {
			this.semaphore = new Semaphore(printers.size(), false);
			this.printerQueue = new ArrayBlockingQueue<>(printers.size());
			this.printerUseTimes = new ConcurrentHashMap<Printer, Integer>(printers.size());
			for (Printer printer : printers) {
				this.printerQueue.offer(printer);
			}
		}
		
		/**
		 * 获取打印机
		 * @return
		 */
		public Printer getPrinter() {
			while (true) {
				if (semaphore.tryAcquire()) {
					try {
						Printer printer = printerQueue.take();
						if (printerUseTimes.containsKey(printer)) {
							printerUseTimes.put(printer, printerUseTimes.get(printer) + 1);
						} else {
							printerUseTimes.put(printer, 1);
						}
						return printer;
					} catch (InterruptedException e) {
						e.printStackTrace();
						semaphore.release();
					}
				}
			}
		}
		
		/**
		 * 释放打印机
		 * @param printer
		 */
		public void release(Printer printer) {
			while (true) {
				if (printerQueue.offer(printer)) {
					semaphore.release();
					break;
				}
			}
		}

		
		/**
		 * @return
		 */
		public Map<Printer, Integer> getPrinterUseTimes() {
			return this.printerUseTimes;
		}
		
	}
	
	private final static class Worker implements Runnable {
		
		private final PrinterManager printerManager;
		
		public Worker(PrinterManager printerManager) {
			this.printerManager = printerManager;
		}
		
		public void run() {
			Printer printer = this.printerManager.getPrinter();
			printer.print();
			this.printerManager.release(printer);
		}
		
	}
	
	@Test
	public void test() {
		int printerSize = 6;	//打印机的数量
		List<Printer> printers = new ArrayList<Printer>(printerSize);
		for (int i=0; i<printerSize; i++) {
			printers.add(new Printer(i+1));
		}
		PrinterManager printerManager = new PrinterManager(printers);
		for (int i=0; i<30; i++) {
			try {
				TimeUnit.MILLISECONDS.sleep(200);	//每隔200ms发起一次打印请求。
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			new Thread(new Worker(printerManager)).start();
		}
		try {
			TimeUnit.SECONDS.sleep(40);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		logger.info(printerManager.getPrinterUseTimes());
	}
	
	public static void main(String args[]) {
		int printerSize = 6;
		List<Printer> printers = new ArrayList<Printer>(printerSize);
		for (int i=0; i<printerSize; i++) {
			printers.add(new Printer(i+1));
		}
		PrinterManager printerManager = new PrinterManager(printers);
		for (int i=0; i<30; i++) {
			try {
				TimeUnit.MILLISECONDS.sleep(200);	//每隔200ms发起一次打印请求。
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			new Thread(new Worker(printerManager)).start();
		}
	}
	
}
