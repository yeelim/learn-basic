/**
 * 
 */
package com.yeelim.learn.javaee6.filter;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.WebSocketContainer;

/**
 * 
 * 过滤器示例
 * @author Yeelim
 * @date 2014-2-8
 * @mail yeelim-zhang@todaytech.com.cn
 * 
 * Filter的配置信息和支持的属性和Servlet几乎是一样的，除了dispatcherTypes和servletNames外。
 * dispatcherTypes表示要过滤的dispatcher类型。
 * servletNames表示要过滤的servlet名称。
 * 
 */
//@WebFilter(urlPatterns="*", asyncSupported=true, dispatcherTypes={DispatcherType.REQUEST})
public class ExampleFilter implements Filter {

	@Override
	public void destroy() {
		System.out.println("destory filter……");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		System.out.println("filter……" + req.getRequestURL());
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("filter init……");
	}

}
