/**
 * 
 */
package com.yeelim.learn.javaee6.listener;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 
 * ServletContext监听器和ServletContext属性监听器
 * 
 * @author Yeelim
 * @date 2014-2-8
 * @mail yeelim-zhang@todaytech.com.cn
 */
@WebListener
public class ContextListener implements ServletContextAttributeListener,
		ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("ServletContext destroyed");
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("ServletContext initialized");
	}

	@Override
	public void attributeAdded(ServletContextAttributeEvent event) {
		System.out.println("ServletContext attribute added");
	}

	@Override
	public void attributeRemoved(ServletContextAttributeEvent event) {
		System.out.println("ServletContext attribute removed");
	}

	@Override
	public void attributeReplaced(ServletContextAttributeEvent event) {
		System.out.println("ServletContext attribute replaced");
	}

}
