/**
 * 
 */
package com.yeelim.learn.spring.beanfactory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

/**
 * 
 * 对于实现了BeanFactoryAware接口的bean在被初始化后会调用其setBeanFactory方法，我们
 * 只需要在setBeanFactory方法中把对应的BeanFactory保存下来，这样我们在bean中就能访问到
 * BeanFactory了，从而访问到其他bean对象。
 * @author Yeelim
 * @date 2014-2-7
 * @time 下午10:08:12 
 *
 */
@Component
public class Product implements BeanFactoryAware {

	
	private BeanFactory beanFactory;
	
	/**
	 * 通过从beanFactory中获取Computer对象并调用其listBeanNames方法展示所有的bean名称
	 */
	public void listBeanNames() {
		Computer computer = beanFactory.getBean(Computer.class);
		if (computer != null) {
			computer.listBeanNames();
		}
	}
	
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

}
