/**
 * 
 */
package com.yeelim.learn.spring.factorybean;

import org.springframework.beans.factory.FactoryBean;

/**
 * 测试Spring的FactoryBean，Spring的FactoryBean是用来创建bean的。
 * @author Yeelim
 * @date 2014-2-7
 * @time 下午8:50:12 
 *
 */
public class UserFactoryBean implements FactoryBean<User> {

	private User user;

	@Override
	public User getObject() throws Exception {
		if (user == null) {
			synchronized (this) {
				if (user == null) {
					User user = new User();
					user.setId(1);
					user.setName("张三");
					this.user = user;
				}
			}
		}
		return user;
	}

	@Override
	public Class<?> getObjectType() {
		return User.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
	
}
