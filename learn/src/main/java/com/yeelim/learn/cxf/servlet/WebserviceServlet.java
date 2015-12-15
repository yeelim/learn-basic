package com.yeelim.learn.cxf.servlet;

import javax.servlet.ServletConfig;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;

import com.yeelim.learn.cxf.interceptor.TestInterceptor;
import com.yeelim.learn.cxf.service.HelloWorldService;
import com.yeelim.learn.cxf.service.HelloWorldServiceImpl;

public class WebserviceServlet extends CXFNonSpringServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3919868434043901738L;

	@Override
	protected void loadBus(ServletConfig sc) {
		super.loadBus(sc);
		//获取当前CXFNonSpringServlet使用的Bus，然后利用该Bus来发布服务
		Bus bus = this.getBus();
		bus.getInInterceptors().add(new TestInterceptor());
		JaxWsServerFactoryBean jsFactoryBean = new JaxWsServerFactoryBean();
		jsFactoryBean.setBus(bus);
		//该路径是相对于当前CXFNonSpringServlet匹配的路径的，也可以加斜杠
		jsFactoryBean.setAddress("helloWorld");
		jsFactoryBean.setServiceClass(HelloWorldService.class);
		jsFactoryBean.setServiceBean(new HelloWorldServiceImpl());
		jsFactoryBean.create();
	}
	
}
