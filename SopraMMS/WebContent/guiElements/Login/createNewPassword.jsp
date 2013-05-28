<%@page import="user.User"%>
<h1>Neues Passwort festlegen</h1>


<%
	if (session.getAttribute("contentWelcome").equals(
			"createNewPassword")
			|| session.getAttribute("contentWelcome").equals(
					"ceateNewPwNotEqual")) {
%>
<p>
	Sie legen ein neues Passwort
	<%
	User user = (User) session.getAttribute("user");
		if (user != null) {
			out.println(" f&uuml;r " + user.getLastName() + "&nbsp;"
					+ user.getFirstName());
		}
%>fest.
</p>

<%
	if (session.getAttribute("contentWelcome").equals(
				"ceateNewPwNotEqual")) {
%>

<error>Die beiden Passw&ouml;rter stimmen nicht &uuml;berein</error>

<%
	}
		session.removeAttribute("contentWelcome");
%>

<form action="/SopraMMS/AcceptNewPassword" method="get">
	<table>
		<tr>
			<td>Neues Passwort:</td>
			<td><input name="newPassword1" type="password" size="30"
				maxlength="40"></td>
		</tr>
		<tr>
			<td>Neues Passwort best&auml;tigen:</td>
			<td><input name="newPassword2" type="password" size="30"
				maxlength="40"></td>
		</tr>

	</table>
	<input type="submit" name="Submit" value="Passwort &Auml;ndern"
		class="btnChangePw" />
</form>

<%
	} else if (session.getAttribute("contentWelcome").equals(
			"ceateNewPwDone")) {
		session.removeAttribute("contentWelcome");
%>

<h3>Ihr Passwort wurde erfolgreich ge&auml;ndert</h3>

<%
	}
%>