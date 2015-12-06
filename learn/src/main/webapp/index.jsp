<%@page pageEncoding="UTF-8" %>
<%@page import="org.jasig.cas.client.util.AssertionHolder" %>
<html>
	<body>
		<h2>Hello World!</h2>
		<div>
			<form method="post" action="servlet/upload" enctype="multipart/form-data">
				<input type="file" name="upload"/>
				<input type="submit" value="upload"/>
			</form>
		</div>
		<div>
			<ul>
				<li>java ee 6.0</li>
				<li>ehcache</li>
				<li>poi</li>
				<li>spring</li>
				<li>${application.contextPath }</li>
				<li><%="Hello World" %></li>
				<li><%=request.getRemoteAddr() %></li>
				<li><%=request.getRemoteHost() %></li>
				<li><%=request.getRemotePort() %></li>
				<li><%=request.getRemoteUser() %></li>
				<li><%=AssertionHolder.getAssertion().getPrincipal() %></li>
				<li><%=AssertionHolder.getAssertion().getValidFromDate() %></li>
				<li><%=AssertionHolder.getAssertion().getValidUntilDate() %></li>
			</ul>
		</div>
		<%=new java.util.Date().toLocaleString() %>
	</body>
</html>
