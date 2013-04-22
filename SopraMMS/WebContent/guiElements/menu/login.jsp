<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/SopraMMS/default.css">
<title>Login</title>
</head>
<body>
	<div class="menuBox">
		<form action="guiElements/menu/loginCheck.jsp" method="post">
			<h1>Login</h1>
	
			<h3>Benutzername:</h3>
			<br>
			<p class="menuContent">
				<input name="loginname" type="text" size="33" />
			</p>
	
			<h3>Passwort:</h3>
			<br>
			<p class="menuContent">
				<input name="password" type="password" size="33" />
			</p>
			<p class="menuContent">
				<input type="submit" name="Submit" />
			</p>
		</form>
	</div>
</body>
</html>