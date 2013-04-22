<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>loginCheck</title>
<link rel="stylesheet" type="text/css" href="default.css">
</head>
<body>
	<%
		String loginname = request.getParameter("loginname");
		String password = request.getParameter("password");
		out.println("Checking login<br>");
		if (loginname == null || password == null) { 
			out.print("Invalid paramters ");
		}
		 
		// Here you put the check on the username and password
		if (loginname.toLowerCase().trim().equals("admin") && password.toLowerCase().trim().equals("admin")) {
			out.println("Welcome " + loginname + " <a href=\"index.jsp\">Back to main</a>");
			session.setAttribute("loginname", loginname);
			response.sendRedirect("administration.jsp");
		} else {
			out.println("Invalid loginname and password");
			response.sendRedirect("error.jsp");
		}
	%> 
</body>
</html>