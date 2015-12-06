/**
 * 
 */
package com.yeelim.learn.spring.factorybean;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Yeelim
 * @date 2014-2-7
 * @time 下午9:02:42 
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class Test {

	//可以直接这样注入UserFactoryBean生成的User对象
	@Autowired
	private User user;
	//可以直接这样注入UserFactoryBean
	@Autowired
	private UserFactoryBean userFactoryBean;
	@Autowired
	private ApplicationContext context;
	
	@org.junit.Test
	public void userFactoryBeanTest() throws Exception {
		System.out.println(user);	//User [id=1, name=张三]
		user = userFactoryBean.getObject();
		System.out.println(user);	//User [id=1, name=张三]
		User user2 = userFactoryBean.getObject();
		System.out.println(user2);	//User [id=1, name=张三]
		System.out.println(user == user2);	//true
		System.out.println(userFactoryBean.isSingleton());	//true
	}
	
	@org.junit.Test
	public void userFactoryBeanTest2() throws Exception {
		User user = context.getBean(User.class); 
		System.out.println(user);
		//可以直接这样从bean容器中获取UserFactoryBean生成的User对象
		user = context.getBean("userFactoryBean", User.class);
		System.out.println(user);	//User [id=1, name=张三]
		UserFactoryBean userFactoryBean = context.getBean(UserFactoryBean.class);
		User user2 = userFactoryBean.getObject();
		System.out.println(user2);	//User [id=1, name=张三]
		System.out.println(user == user2);	//true
		//这样获取UserFactoryBean会抛出异常，因为bean容器中id为userFactoryBean的对象是UserFactoryBean对应的类型即User类型
		//userFactoryBean = context.getBean("userFactoryBean", UserFactoryBean.class);
		//当需要访问到真正的FactoryBean对象时，我们需要在id名称前加上”&“。
		userFactoryBean = context.getBean("&userFactoryBean", UserFactoryBean.class);
		System.out.println(userFactoryBean.getObject());	//User [id=1, name=张三]
	}
	
}
