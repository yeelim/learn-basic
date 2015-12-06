/**
 * 
 */
package com.yeelim.learn.spring.beanfactory;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 
 * ApplicationContext是间接的继承自BeanFactory的。对于实现了ApplicationContextAware接口的
 * bean对象在被初始化后会调用其setApplicationContext方法，我们只需在setApplicationContext方法
 * 体里面把对应的ApplicationContext保存下来，这样我们在bean中就能访问到ApplicationContext了，从
 * 而访问到其他的bean对象。
 * 
 * @author Yeelim
 * @date 2014-2-7
 * @time 下午10:14:10 
 *
 */
@Component
public class Computer implements ApplicationContextAware {

	private ApplicationContext context;
	
	/**
	 * 列出所有的Bean名称
	 */
	public void listBeanNames() {
		String[] beanNames = context.getBeanDefinitionNames();
		for (String name : beanNames) {
			System.out.println(name);
		}
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.context = applicationContext;
	}

}
