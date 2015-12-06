/**
 * 
 */
package com.yeelim.learn.javaee6.servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Yeelim
 * @date 2014-2-8
 * @mail yeelim-zhang@todaytech.com.cn
 * 
 */
@WebServlet("/servlet/context-session")
public class ContextSessionServlet extends HttpServlet {

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
		ServletContext context = req.getServletContext();
		context.setAttribute("context", "ServletContext");
		context.setAttribute("context", "abc");
		context.removeAttribute("context");
		HttpSession session = req.getSession();
		session.setAttribute("session", "HttpSession");
		session.setAttribute("session", "abc");
		session.removeAttribute("session");
	}
	
}
