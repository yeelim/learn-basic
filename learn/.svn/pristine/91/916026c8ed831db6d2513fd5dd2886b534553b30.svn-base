/**
 * 
 */
package com.yeelim.learn.java.concurrent;


/**
 * @author elim
 * @date 2015-2-12
 * @time 下午9:16:47 
 *
 */
public final class ThreadGroupTest {

	private class Worker implements Runnable {
		private final int id;
		private Worker (int id) {
			this.id = id;
		}
		
		public void run() {
			Thread current = Thread.currentThread();
			System.out.println(current + "-----id:" + id + "---" + current.getThreadGroup());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println(current + "线程被中断了");
			}
		}
		
	}
	
	public static void main(String args[]) {
		ThreadGroupTest test = new ThreadGroupTest();
		ThreadGroup group = new ThreadGroup("g1");
		group.setMaxPriority(8);//必须在1-10之间
		for (int i=1; i<=5; i++) {
			Worker worker = test.new Worker(i);
			Thread thread = new Thread(group, worker);
			thread.setPriority(i+1);//必须在1-10之间，如果超过了所属组的最大优先级，则取最大优先级
			thread.setName(Thread.currentThread() + "-Thread-" + i);
			thread.start();
			System.out.println(group.activeCount() + "------" + group.activeGroupCount());
		}
		group.list();
		group.interrupt();
	}
	
}
