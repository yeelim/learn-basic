/**
 * 
 */
package com.yeelim.learn.ehcache.sample;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;

import org.junit.Test;

/**
 * @author Yeelim
 * @date 2014-2-25
 * @time 下午11:08:51 
 *
 */
public class CacheTest {

	@Test
	public void test() {
		CacheManager cacheManager = CacheManager.create();
		//以默认配置添加一个名叫cacheName的Cache。
		cacheManager.addCache("cacheName");
		Cache cache = cacheManager.getCache("cacheName");
		Element ele = new Element("key", "value");
		//把ele放入缓存cache中
		cache.put(ele);
	}
	
	@Test
	public void testDefaultCache() {
		CacheManager cacheManager = CacheManager.create();
		//cache1将使用默认配置
		cacheManager.addCache("cache1");
		Cache cache1 = cacheManager.getCache("cache1");
		Element ele = new Element("key", "value");
		cache1.put(ele);
		Element ele2 = cache1.get("key");
		System.out.println(ele == ele2);	//false
		
		Cache test2 = cacheManager.getCache("test2");
		test2.put(ele);
		Element ele3 = test2.get("key");
		System.out.println(ele == ele3);	//true
	}
	
	@Test
	public void cache() {
		//内存中保存的Element的最大数量
		int maxEntriesLocalHeap = 10000;
		CacheConfiguration cacheConfiguration = new CacheConfiguration("cacheName", maxEntriesLocalHeap);
		cacheConfiguration.overflowToOffHeap(false);
		Cache cache = new Cache(cacheConfiguration);
		//使用默认配置创建CacheManager
		CacheManager cacheManager = CacheManager.create();
//		Configuration config = new Configuration();
		//只有添加到CacheManager中的Cache才是有用的
		cacheManager.addCache(cache);
		cache.put(new Element("key", "value"));
		System.out.println(cache.get("key"));
	}
	
	@Test
	public void cache2() {
		CacheConfiguration cacheConfiguration = new CacheConfiguration();
		cacheConfiguration.setName("test");	//指定cache名称
		cacheConfiguration.setMaxBytesLocalHeap("10M");	//指定最大可用堆内存
		Configuration config = new Configuration();	//构建一个空配置
		//添加Cache配置信息到CacheManager的配置信息中
		config.addCache(cacheConfiguration);
		CacheManager cacheManager = CacheManager.create(config);
		System.out.println(cacheManager.getOriginalConfigurationText());
		Cache cache = cacheManager.getCache("test");
		cache.put(new Element("key", "value"));
	}
	
	@Test
	public void testDiskStoreSize() {
		CacheManager cacheManager = CacheManager.create();
		Cache cache = cacheManager.getCache("ttt");
		System.out.println(cache.get("12345"));
		cache.put(new Element("12345", "12345"));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testOverflowToDisk() {
		CacheManager cacheManager = CacheManager.create();
		cacheManager.addCache("cache1");
		Cache cache = cacheManager.getCache("cache1");
		cache.getCacheConfiguration().maxEntriesLocalDisk(1).maxEntriesLocalHeap(2);
		int num = 6;
		for (int i=1; i<=num; i++) {
			Element ele = new Element("key" + i, i);
			if (i<2) {
				ele.setEternal(true);
			}
			cache.put(ele);
			try {
				Thread.sleep(100l);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(cache.getKeys() + "--------put--------" + cache.get("key"+i));
			System.out.println("---------diskStoreSize----------" + cache.getDiskStoreSize());
			System.out.println("---------memoryStoreSize---------" + cache.getMemoryStoreSize());
			System.out.println("----------memoryStoreEvictionPolicy-----" + cache.getMemoryStoreEvictionPolicy().getName());
			
		}
		for (int i=1; i<=num; i++) {
			try {
				Thread.sleep(100l);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(cache.getKeys() + "--------get--------" + cache.get("key"+i));
		}
		
		System.out.println(cache.getKeys());
	}
}
