<%@page pageEncoding="UTF-8" %>
<%@page import="java.net.*" %>
<%!
public String getDataFromApp() throws Exception {
	URL url = new URL("http://yeelim:8081/app/getData.jsp");
	HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	String message = conn.getResponseMessage();
	return message;
}
%>
<%
	out.write(this.getDataFromApp());
%>