/**
 * 
 */
package com.yeelim.learn.cas.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.AssertionHolder;

/**
 * @author Yeelim
 * @date 2014-8-31
 * @time 下午7:29:50 
 *
 */
@WebServlet(name="casProxyTest", urlPatterns="/cas/proxy/test")
public class CasProxyTestServlet extends HttpServlet {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7535586473955505787L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String target = "http://yeelim:8080/Learn_SpringSecurity/cas/test/sysTime.do";
		//1、获取到AttributePrincipal对象
		AttributePrincipal principal = AssertionHolder.getAssertion().getPrincipal();
		//2、获取对应的proxy ticket
		String proxyTicket = principal.getProxyTicketFor(target);
		//3、请求被代理应用时将获取到的proxy ticket以参数ticket进行传递
		URL url = new URL(target + "?ticket=" + URLEncoder.encode(proxyTicket, "UTF-8"));
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		StringBuffer content = new StringBuffer();
		String line = null;
		while ((line=br.readLine()) != null) {
			content.append(line).append("<br/>");
		}
		resp.getWriter().write(content.toString());
	}

}
