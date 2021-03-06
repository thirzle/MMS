<%@page import="user.UserAdministration"%>
<%@page import="user.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Error Page</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/default.css">
<link rel="icon"
	href="${pageContext.request.contextPath}/images/favicon.ico"
	type="image/x-icon" />
</head>
<body>

	<%
		try {
			User user = (User) session.getAttribute("user");
			UserAdministration ua = new UserAdministration();
			ua.logout(user.getLogin());
		} catch (Exception e) {
			System.err.println("User konnte nicht mehr ausgelogt werden");

		}
		session.invalidate();
	%>

	<div class="page">
		<div class="header">
			<%@ include file="/guiElements/header.jsp"%>
		</div>
		<div class="main">
			<div class="menu">
				<div class="menuBox">
					<h1>MMS</h1>
					<div class="errorNav">
						<ul>
							<li><a href="/SopraMMS/guiElements/home.jsp">Startseite</a></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="contentBox">
				<h1>Fehler</h1>

				<h3>Es ist ein Fehler aufgetreten.</h3>
				<p>
					Bitte kehren Sie zur <a href="/SopraMMS/guiElements/home.jsp">Startseite</a>
					zur&uuml;ck. <br />Sollte dies nicht klappen starten Sie Ihren
					Browser neu und laden Sie danach das Modul Management System
					erneut.
				</p>
				<img src="${pageContext.request.contextPath}/images/Teambild.jpg"
					alt="Logo" align="right">

			</div>
		</div>
		<div class="footer">
			<%@ include file="/guiElements/footer.jsp"%>
		</div>
	</div>
</body>
</html>