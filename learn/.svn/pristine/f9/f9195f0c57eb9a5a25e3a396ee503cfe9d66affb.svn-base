/**
 * 
 */
package com.yeelim.learn.ehcache.listener;

import java.util.Properties;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.event.CacheManagerEventListener;
import net.sf.ehcache.event.CacheManagerEventListenerFactory;

/**
 * @author Yeelim
 * @date 2014-3-9
 * @time 下午12:36:22 
 *
 */
public class MyCacheManagerEventListenerFactory extends
		CacheManagerEventListenerFactory {

	@Override
	public CacheManagerEventListener createCacheManagerEventListener(
			CacheManager cacheManager, Properties properties) {
		return new MyCacheManagerEventListener(cacheManager);
	}

}
