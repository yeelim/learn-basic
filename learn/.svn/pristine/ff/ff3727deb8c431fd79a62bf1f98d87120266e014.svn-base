/**
 * 
 */
package com.yeelim.learn.javaee6.servlet;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 带初始化参数的Servlet
 * WebServlet的属性initParams可以用来指定当前Servlet的初始化参数，它是一个数组，
 * 里面每一个@WebInitParam表示一个参数。
 */
@WebServlet(value="/servlet/init-param", initParams={@WebInitParam(name="param1", value="value1")})
public class WebInitParamServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Enumeration<String> paramNames = this.getServletConfig().getInitParameterNames();
		String paramName;
		while (paramNames.hasMoreElements()) {
			paramName = paramNames.nextElement();
			resp.getWriter().append(paramName + " = " + this.getServletConfig().getInitParameter(paramName));
		}
		resp.getWriter().close();
	}
	
}
