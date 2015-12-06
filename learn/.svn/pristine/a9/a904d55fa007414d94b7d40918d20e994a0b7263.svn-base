/**
 * 
 */
package com.yeelim.learn.spring.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.cache.annotation.Cacheable;

/**
 * @author Yeelim
 * @date 2014-5-15
 * @time 下午10:28:09 
 *
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Cacheable(value="users")
public @interface MyCacheable {

}
