<link rel="stylesheet" type="text/css" href="/MMS/default.css">
<div class="menuBox">

	<%
		String name = (String) session.getAttribute("loginname");

		if (name != null) {
			out.println("Welcome  " + name
					+ "  , <a href=\"logout.jsp\" >Logout</a>");
		} else {
	%>
	<form action="loginCheck.jsp">
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
			<input type="submit" name="loginButton" value="Login" />
		</p>
	</form>
	<%
		}
	%>
</div>