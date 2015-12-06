/**
 * 
 */
package com.yeelim.learn.spring.beanfactory;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Yeelim
 * @date 2014-2-7
 * @time 下午10:23:11 
 *
 */
@ContextConfiguration("classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class Test {

	@Autowired
	private Computer computer;	//实现了ApplicationContextAware接口
	
	@Autowired
	private Product product;	//实现了BeanFactoryAware接口
	
	@org.junit.Test
	public void test() {
		computer.listBeanNames();
		product.listBeanNames();
	}
	
}
