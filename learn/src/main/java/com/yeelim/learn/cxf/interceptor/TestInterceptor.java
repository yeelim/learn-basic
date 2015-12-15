package com.yeelim.learn.cxf.interceptor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.handler.MessageContext;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination;

public class TestInterceptor extends AbstractPhaseInterceptor<Message> {

	public TestInterceptor() {
		super(Phase.PRE_INVOKE);
	}

	public void handleMessage(Message message) throws Fault {
		HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) message.get(AbstractHTTPDestination.HTTP_RESPONSE);
		ServletContext servletContext = (ServletContext) message.get(AbstractHTTPDestination.HTTP_CONTEXT);
		System.out.println(request + "--" + response + servletContext);
	}

}
