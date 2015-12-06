/**
 * 
 */
package com.yeelim.learn.java.concurrent;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * @author elim
 * @date 2015-2-6
 * @time 下午11:55:47 
 *
 */
public class ForkJoinTest {

	private final static Logger logger = Logger.getLogger(ForkJoinTest.class);
	
	@Test
	public void test() {
		for (int i=0; i<20; i++) {
			logger.info(i>>>1);//右移1位
		}
	}
	
	@Test
	public void test2() {
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		int[] array = this.getRandomIntArray(100000);
		logger.info("准备通过forkJoin计算最大值。");
		long t1 = System.currentTimeMillis();
		int max = forkJoinPool.invoke(new MaxNumTask(array, 0, array.length, 1100));
		long t2 = System.currentTimeMillis();
		logger.info((t2-t1) + "通过forkJoin算出来的最大值是：" + max);
	}

	@Test
	public void test3() {
		long t_1 = System.currentTimeMillis();
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		int[] array = this.getRandomIntArray(1000000);
		logger.info("准备通过forkJoin计算最大值。");
		long minTime = Long.MAX_VALUE;
		int suitableMaxLen = 0;
		int maxVal = 0;
		for (int i=100; i<=array.length; ) {
			i += 100;
			long t1 = System.currentTimeMillis();
			int max = forkJoinPool.invoke(new MaxNumTask(array, 0, array.length, i));
			long t2 = System.currentTimeMillis();
			if (t2-t1 < minTime) {
				minTime = t2-t1;
				suitableMaxLen = i;
			}
			if (max > maxVal) {
				maxVal = max;
			}
		}
		logger.info(maxVal + "对于长度为：" + array.length + "的数组使用ForkJoin进行排序，最合适的maxLen是：" + suitableMaxLen + "，耗费时间是：" + minTime);
		logger.info("整个过程耗费时间是：" + (System.currentTimeMillis()-t_1));
	}
	
	/**
	 * 不能直接调用ForkJoinTask的invoke方法来进行对应的分治算法运算，其必须通过对应的ForkJoinPool进行调用。
	 */
	@Test
	@Deprecated
	public void test4() {
		long t_1 = System.currentTimeMillis();
		int[] array = this.getRandomIntArray(1000);
		logger.info("准备通过forkJoin计算最大值。");
		long minTime = Long.MAX_VALUE;
		int suitableMaxLen = 0;
		int maxVal = 0;
		for (int i=100; i<=array.length; ) {
			i += 100;
			long t1 = System.currentTimeMillis();
			int max = new MaxNumTask(array, 0, array.length, i).invoke();
			long t2 = System.currentTimeMillis();
			if (t2-t1 < minTime) {
				minTime = t2-t1;
				suitableMaxLen = i;
			}
			if (max > maxVal) {
				maxVal = max;
			}
		}
		logger.info(maxVal + "对于长度为：" + array.length + "的数组使用ForkJoin进行排序，最合适的maxLen是：" + suitableMaxLen + "，耗费时间是：" + minTime);
		logger.info("整个过程耗费时间是：" + (System.currentTimeMillis()-t_1));
	}
	
	private int[] getRandomIntArray(int len) {
		Random random = new Random();
		int[] result = new int[len];
		while (len-->0) {
			result[len] = random.nextInt(result.length);
		}
		int max = 0;
		long t1 = System.currentTimeMillis();
		for (int i=0; i<result.length; i++) {
			if (result[i]>max) {
				max = result[i];
			}
		}
		long t2 = System.currentTimeMillis();
		logger.info((t2-t1) + "最大值是：" + max);
		return result;
	}
	
	private final static class MaxNumTask extends RecursiveTask<Integer> {

		/**
		 * serialVersionUID
		 */
		private static final long serialVersionUID = 8874756590665075248L;
		private final int[] array;
		private final int start;
		private final int end;
		private final int maxLen;
		
		public MaxNumTask(int[] array, int start, int end, int maxLen) {
			this.array = array;
			this.start = start;
			this.end = end;
			this.maxLen = maxLen;
		}
		
		@Override
		protected Integer compute() {
			int max = Integer.MIN_VALUE;
			if (end-start > maxLen) {
				MaxNumTask task1 = new MaxNumTask(array, start, start+maxLen, maxLen);
				MaxNumTask task2 = new MaxNumTask(array, start+maxLen, end, maxLen);
				task1.fork();
				task2.fork();
				max = Math.max(max, task1.join());
				max = Math.max(max, task2.join());
			} else {
				for (int i=start; i<end; i++) {
					if (array[i] > max) {
						max = array[i];
					}
				}
			}
			return max;
		}
		
	}
	
}
