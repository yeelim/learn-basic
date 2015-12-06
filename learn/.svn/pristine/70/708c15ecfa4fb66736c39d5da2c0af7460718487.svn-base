/**
 * 
 */
package com.yeelim.learn.java.concurrent;

/**
 * @author elim
 * @date 2015-2-12
 * @time 下午10:05:13 
 *
 */
public class ThreadInterruptTest {

	private static class Worker implements Runnable {
		
		public void run() {
			int n = 1;
			for (;;) {
				if (isPrime(n)) {
					System.out.printf("Number %d is prime.\n", n);
				}
				if (Thread.currentThread().isInterrupted()) {
					System.out.println("----线程中断了----" + Thread.interrupted());
					return;
				} else {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
						Thread.currentThread().interrupt();
					}
				}
				n++;
			}
		}
		
		private boolean isPrime(int n) {
			if (n<=2) {
				return true;
			} else {
				for (int i=2; i<n; i++) {
					if (n%i==0) {
						return false;
					}
				}
			}
			return true;
		}
	}
	
	public static void main(String args[]) throws InterruptedException {
		Thread t = new Thread(new Worker());
		t.start();
		Thread.sleep(3000);
		t.interrupt();
	}
	
}
