/**
 * 
 */
package com.yeelim.learn.java.concurrent;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.log4j.Logger;

/**
 * @author elim
 * @date 2015-3-16
 * @time 下午9:10:52 
 *
 */
public class ThreadLocalRandomTest {
	
	private final static Logger logger = Logger.getLogger(ThreadLocalRandomTest.class);

	public static void main(String args[]) {
		logger.info(ThreadLocalRandom.current().nextBoolean());
		logger.info(ThreadLocalRandom.current().nextDouble());
		logger.info(ThreadLocalRandom.current().nextDouble(100));
		logger.info(ThreadLocalRandom.current().nextDouble(1000, 2000));
		logger.info(ThreadLocalRandom.current().nextFloat());
		logger.info(ThreadLocalRandom.current().nextInt());
		logger.info(ThreadLocalRandom.current().nextInt(50, 100));
		logger.info(ThreadLocalRandom.current().nextLong());
	}
	
}
