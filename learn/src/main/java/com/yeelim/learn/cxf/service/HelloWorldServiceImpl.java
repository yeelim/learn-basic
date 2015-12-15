package com.yeelim.learn.cxf.service;

import javax.jws.WebService;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.transport.http.AbstractHTTPDestination;

@WebService
public class HelloWorldServiceImpl implements HelloWorldService {

	public void sayHello(String who) {
		Message message = PhaseInterceptorChain.getCurrentMessage();
		//获取request
		HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
		//获取response
		HttpServletResponse response = (HttpServletResponse) message.get(AbstractHTTPDestination.HTTP_RESPONSE);
		//获取ServletContext
		ServletContext servletContext = (ServletContext) message.get(AbstractHTTPDestination.HTTP_CONTEXT);
		System.out.println(request.getRemoteAddr());
		System.out.println("======================================");
		System.out.println(response);
		System.out.println("=======================================");
		System.out.println(servletContext);
	}

}
