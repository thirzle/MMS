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
</head>
<body>

	<%
		try {
			User user = (User) session.getAttribute("user");
			UserAdministration ua = new UserAdministration();
			ua.logout(user.getLogin());
		} catch (Exception e) {
			System.err.print("User konnte nicht mehr ausgelogt werden");

		}
		session.invalidate();
	%>

	<div class="page">
		<div class="header">
			<%@ include file="/guiElements/header.jsp"%>
		</div>
		<div class="main">
			<div class="menu">
				<%@ include file="/guiElements/frontend/courseSelection.jsp"%>
			</div>
			<div class="contentBox">
				<h1>Fehler</h1>

				<h3>Es ist ein Fehler aufgetreten.</h3>
				<p>
					Bitte kehren Sie zur <a href="/SopraMMS/welcome.jsp">Startseite</a>
					zur&uuml;ck. <br />Sollte dies nicht klappen starten Sie Ihren
					Browser neu und laden Sie danach das MMS erneut.
				</p>

			</div>
		</div>
		<div class="footer">
			<%@ include file="/guiElements/footer.jsp"%>
		</div>
	</div>
</body>
</html>