<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/MMS/default.css">
<title>Logout</title>
</head>
<body>
	<%
		String loginname=(String)session.getAttribute("loginname");
		if(loginname!=null) {
			out.println(loginname+" loged out");
			session.removeAttribute("username");
			session.removeAttribute("password");
			session.invalidate();  
		} else {
			out.println("You are already not login");
		}
	%>
<meta http-equiv=refresh content="0; URL=/SopraMMS/welcome.jsp">
</body>
</html>
