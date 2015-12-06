/**
 * 
 */
package com.yeelim.learn.spring.cache;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;

/**
 * @author Yeelim
 * @date 2014-5-12
 * @time 下午9:20:00 
 *
 */
public class UserKeyGenerator implements KeyGenerator {

	public Object generate(Object target, Method method, Object... params) {
		for (int i=0; i<10; i++) {
			System.out.println("*********************************************************");
		}
		return 1;
	}

}
