/**
 * 
 */
package com.yeelim.learn.spring.cache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.yeelim.learn.spring.model.User;

/**
 * @author Yeelim
 * @date 2014-5-12
 * @time 下午8:32:09
 * 
 */
@Service
public class UserServiceImpl implements UserService {

//	@Cacheable(value = "users", key = "#p0")
	@MyCacheable
	public User findById(Integer id) {
		System.out.println("find user by id: " + id);
		User user = new User();
		user.setId(id);
		user.setName("Name" + id);
		return user;
	}

	@CacheEvict(value = "users", beforeInvocation = true)
	public void delete(Integer id) {
		System.out.println("delete user by id: " + id);
	}

	@Cacheable(value = { "users" }, key = "#user.id", condition = "#user.id%2==0")
	public User find(User user) {
		System.out.println("find user by user " + user);
		return user;
	}

	@Caching(cacheable = @Cacheable("users"), evict = { @CacheEvict("cache2"),
			@CacheEvict(value = "cache3", allEntries = true) })
	public User find(Integer id) {
		return null;
	}

}
