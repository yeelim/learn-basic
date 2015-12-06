/**
 * 
 */
package com.yeelim.learn.ehcache.sample;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Yeelim
 * @date 2014-3-16
 * @time 下午2:36:58 
 *
 */
public class CacheCRUDTest {
	
	private CacheManager cacheManager;
	
	@Before
	public void before() {
		cacheManager = CacheManager.create();
		cacheManager.addCache("cache");
	}
	
	@After
	public void after() {
		cacheManager.shutdown();
	}
	
	/**
	 * 往Cache中新增元素
	 */
	@Test
	public void create() {
		Cache cache = cacheManager.getCache("cache");
		Element ele = new Element("key", "value");
		//把ele放入缓存cache中
		cache.put(ele);
		//读该元素
		this.read();
	}
	
	/**
	 * 从Cache中读取元素
	 */
	@Test
	public void read() {
		Cache cache = cacheManager.getCache("cache");
		//通过key来获取缓存中对应的元素
		Element ele = cache.get("key");
		System.out.println(ele);
		if (ele != null) {//当缓存的元素存在时获取缓存的值
			System.out.println(ele.getObjectValue());
		}
	}
	
	/**
	 * 更新元素
	 */
	@Test
	public void update() {
		Cache cache = cacheManager.getCache("cache");
		cache.put(new Element("key", "value1"));
		System.out.println(cache.get("key"));
		//替换元素的时候只有Cache中已经存在对应key的元素时才会替换，否则不操作。
		cache.replace(new Element("key", "value2"));
		System.out.println(cache.get("key"));
	}
	
	/**
	 * 根据key来移除一个元素
	 */
	@Test
	public void delete() {
		Cache cache = cacheManager.getCache("cache");
		//根据key来移除一个元素
		cache.remove("key");
		System.out.println(cache.get("key"));
	}

}
