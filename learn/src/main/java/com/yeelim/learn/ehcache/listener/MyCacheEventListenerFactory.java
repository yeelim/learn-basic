/**
 * 
 */
package com.yeelim.learn.ehcache.listener;

import java.util.Properties;

import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.event.CacheEventListenerFactory;

/**
 * @author Yeelim
 * @date 2014-4-16
 * @time 上午12:00:02 
 *
 */
public class MyCacheEventListenerFactory extends CacheEventListenerFactory {

	@Override
	public CacheEventListener createCacheEventListener(Properties properties) {
		return new MyCacheEventListener();
	}

}
