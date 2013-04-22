<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Benutzerverwaltung</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/default.css">
</head>
<body>
	<div class="page">
		<div class="header">
			<%@ include file="/guiElements/administrationHeader.jsp" %>
		</div>
		<div class="main">
			<div class="menu">
				<p>Du bist eingeloggt als:<%out.print(session.getAttribute("loginname"));%></p>
				<p>
					<a href="${pageContext.request.contextPath}/guiElements/menu/logout.jsp">Logout</a>
				</p>
			</div>
			<div class="contentBox"></div>
				<%@ include file="/guiElements/menu/administration/userManagement/userManagementContent.jsp" %>
			</div>
		<div class="footer">
			<%@ include file="/guiElements/footer.jsp" %>
		</div>
</div>
</body>
</html>