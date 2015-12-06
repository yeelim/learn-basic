/**
 * 
 */
package com.yeelim.learn.java.concurrent;

/**
 * @author elim
 * @date 2015-2-12
 * @time 下午7:55:25 
 *
 */
public class Caculator implements Runnable {

	private final int num;
	
	public Caculator(int num) {
		this.num = num;
	}
	
	public static void main(String args[]) {
		for (int i=1; i<10; i++) {
			new Thread(new Caculator(i)).start();
		}
	}
	
	@Override
	public void run() {
		for (int i=1; i<10; i++) {
			System.out.printf("%s:%d * %d = %d\n", Thread.currentThread().getName(), this.num, i, this.num * i);
		}
	}

}
