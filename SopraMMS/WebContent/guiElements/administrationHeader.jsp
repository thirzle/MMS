<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<img src="/SopraMMS/images/textHeader.bmp" alt="Logo" align="left">
	<img src="/SopraMMS/images/imgHeader.bmp" alt="Logo" align="right">
	<br>
	<div class="headerNavigation">
		<navigation>
			<a href="${pageContext.request.contextPath}/guiElements/menu/administration.jsp" style="color: #FFFFFF; text-decoration: none;">Startseite</a>
		</navigation>
		<navigation>
			<a href="${pageContext.request.contextPath}/guiElements/menu/administration/userManagement/userManagement.jsp" style="color: #FFFFFF; text-decoration: none;">Benutzerverwaltung</a>
		</navigation>
		<navigation>
			<a href="${pageContext.request.contextPath}/guiElements/menu/administration/messages/messages.jsp" style="color: #FFFFFF; text-decoration: none;">Nachrichten</a>
		</navigation>
		<navigation>
			<a href="${pageContext.request.contextPath}/guiElements/menu/administration/modulManagement/modulManagement.jsp" style="color: #FFFFFF; text-decoration: none;">Modulverwaltung</a>
		</navigation>
		<navigation>Du bist eingeloggt als:<%out.print(session.getAttribute("loginname"));%></navigation>
		<navigation>
			<a href="${pageContext.request.contextPath}/guiElements/menu/logout.jsp" style="color: #FFFFFF; text-decoration: none;">Logout</a>
		</navigation>
		
	</div>
</body>
</html>