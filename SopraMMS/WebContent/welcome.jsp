<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/default.css">
</head>
<body>

	<div class="page">
		<div class="header">
			<%@ include file="/guiElements/header.jsp"%>
		</div>
		<div class="main">
			<div class="menu">
				<%@ include file="/guiElements/courseSelection.jsp"%>
				<%
					String loginname = (String) session.getAttribute("loginname");
					if (loginname == null) {
				%>
				<%@ include file="/guiElements/Login/login.jsp"%>
				<%
					} else {

						response.sendRedirect("/SopraMMS/guiElements/home.jsp");

					}
				%>
			</div>
			<div class="contentBox"></div>
		</div>
		<div class="footer">
			<%@ include file="/guiElements/footer.jsp"%>
		</div>
	</div>
</body>
</html>