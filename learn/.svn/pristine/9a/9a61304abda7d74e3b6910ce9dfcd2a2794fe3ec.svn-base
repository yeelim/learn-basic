/**
 * 
 */
package com.yeelim.learn.ehcache.listener;

import org.junit.Test;

import net.sf.ehcache.CacheManager;

/**
 * @author Yeelim
 * @date 2014-4-10
 * @time 下午11:33:17 
 *
 */
public class CacheManagerEventListenerTest {

	@Test
	public void testAdd() {
		CacheManager cacheManager = CacheManager.create(this.getClass().getResource("/ehcache-listener.xml"));
		cacheManager.addCache("test1");
		cacheManager.removeCache("test1");
	}
	
}
