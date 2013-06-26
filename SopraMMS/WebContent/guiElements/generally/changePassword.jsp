<%@page import="user.User"%>
<%
	session.setAttribute("content", "changedPw");
%>

<h1>Passwort &auml;ndern</h1>
<p>
	Sie sind angemeldet als
	<%
	User user = (User) session.getAttribute("user");
	out.println("" + user.getLastName() + "&nbsp;"
			+ user.getFirstName() + "&nbsp;&rarr;&nbsp;"
			+ user.getMail());
%>
</p>
<%
	String content = request.getParameter("changePwStatus") + "";
	if (content.equals("changedPwStatusOldPwWrong")) {
%>
<p>
	<error>Das eingegebene Passwort war falsch</error>
</p>
<%
	} else if (content.equals("changedPwStatusPw12Wrong")) {
%>
<p>
	<error>Die neuen Passw&ouml;rter stimmen nicht &uuml;ber ein</error>
</p>
<%
	} else if (content.equals("changedPwStatusdone")) {
%>
<p>
<h2>Das Passwort wurde erfolgreich ge&auml;ndert</h2>
</p>
<%
	}
%>
<form action="/SopraMMS/ChangePassword" method="get">
	<table>
		<tr>
			<td>Altes Passwort:</td>
			<td><input class="inputField" name="oldPassword" type="password" size="30"
				maxlength="30"></td>
		</tr>
		<tr>
			<td>Neues Passwort:</td>
			<td><input class="inputField" name="newPassword1" type="password" size="30"
				maxlength="40"></td>
		</tr>
		<tr>
			<td>Neues Passwort best&auml;tigen:</td>
			<td><input class="inputField" name="newPassword2" type="password" size="30"
				maxlength="40"></td>
		</tr>

	</table>
	<input type="submit" name="Submit" value="Passwort &Auml;ndern"
		class="btnChangePw" />
</form>