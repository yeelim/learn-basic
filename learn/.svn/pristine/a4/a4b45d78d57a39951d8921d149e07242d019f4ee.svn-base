/**
 * 
 */
package com.yeelim.learn.ehcache.listener;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.junit.Test;

/**
 * @author Yeelim
 * @date 2014-4-16
 * @time 上午12:23:43 
 *
 */
public class CacheEventListenerTest {

	@Test
	public void test() {
		CacheManager cacheManager = CacheManager.create(this.getClass().getResource("/ehcache-listener.xml"));
		Cache test = cacheManager.getCache("test");
		test.put(new Element("tt", "tt"));
	}
	
}
