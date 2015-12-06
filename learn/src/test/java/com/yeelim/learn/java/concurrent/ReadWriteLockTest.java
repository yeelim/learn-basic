/**
 * 
 */
package com.yeelim.learn.java.concurrent;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * @author elim
 * @date 2015-2-14
 * @time 下午9:45:09 
 *
 */
public class ReadWriteLockTest {

	private final static Logger logger = Logger.getLogger(ReadWriteLockTest.class);
	
	/**
	 * 读写锁对于读锁是可以并发获取的，即同时允许多个线程获取读锁，但是只允许一个线程获取写锁。
	 * 在写锁或者读锁存在时获取写锁的操作将进行等待，直到对应的读写锁不存在。
	 * 在写锁存在时获取读锁将进行等待，直到对应的写锁不存在。
	 */
	@Test
	public void test1() {
		ReadWriteLock lock = new ReentrantReadWriteLock();
		logger.info("准备获取读锁。。。。");
		lock.readLock().lock();
		logger.info("获取了读锁。。。。");
		logger.info("准备获取写锁。。。。");
		lock.writeLock().lock();
		logger.info("获取了写锁。。。。");
	}
	
	@Test
	public void test2() {
		ReadWriteLock lock = new ReentrantReadWriteLock();
		for (int i=0; i<3; i++) {
			logger.info("准备获取Write锁。。。");
			lock.writeLock().lock();
			logger.info("获取了Write锁。。。");
			logger.info("准备获取Read锁。。。");
			lock.readLock().lock();
			logger.info("获取了Read锁。。。");
		}
	}
	
}
