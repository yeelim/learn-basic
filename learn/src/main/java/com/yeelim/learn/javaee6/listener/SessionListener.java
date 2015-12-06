/**
 * 
 */
package com.yeelim.learn.javaee6.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 
 * HttpSession监听器和HttpSession属性监听器
 * 
 * @author Yeelim
 * @date 2014-2-8
 * @mail yeelim-zhang@todaytech.com.cn
 */
@WebListener
public class SessionListener implements HttpSessionAttributeListener,
		HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		System.out.println("session created");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		System.out.println("session destroyed");
	}

	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		System.out.println("session attribute added");
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		System.out.println("session attribute removed");
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		System.out.println("session attribute replaced");
	}

}
