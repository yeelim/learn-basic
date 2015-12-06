/**
 * 
 */
package com.yeelim.learn.spring.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yeelim.learn.spring.model.User;

/**
 * @author Yeelim
 * @date 2014-5-12
 * @time 下午8:36:47 
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SpringCacheTest {

	@Autowired
	private UserService userService;
//	@Autowired
	private ConcurrentMapCache cache;
	
	@Test
	public void find() {
		User user = userService.findById(10);
		System.out.println(user);
		System.out.println(userService.findById(10));
		this.listCaches(10);
	}
	
	@Test
	public void find2() {
		User user = new User(1, "hello123");
		System.out.println(userService.find(user));
		System.out.println(userService.find(user));
		this.listCaches(1);
		User user2 = new User(1, "xxx");
		System.out.println(userService.find(user2));
		System.out.println(userService.find(user2));
		this.listCaches(1);
		User user3 = new User(2, "User2");
		System.out.println(userService.find(user3));
		System.out.println(userService.find(user3));
	}
	
	@Test
	public void delete() {
		find();
		userService.delete(10);
		find();
	}
	
	private void listCaches(Object key) {
//		System.out.println("Cache Name is : " + cache.getName());
//		System.out.println(cache.get(key) != null ? cache.get(key).get() : null);
	}
	
}
