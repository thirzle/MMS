<%@page import="user.User"%>

<h1>Passwort &auml;ndern</h1>
<p>
	Sie sind angemeldet als
	<%
	User user = (User)session.getAttribute("user");
	out.println(""+user.getLastName()+"&nbsp;"+user.getFirstName()+"&nbsp;&rarr;&nbsp;"+user.getMail());
%>
</p>
	<p>
	<h3>Altes Passwort</h3>
	<input name="oldPassword" type="password" size="30" maxlength="30">
	</p>
	<p>
	<h3>Neues Passwort</h3>
	<input name="newPassword1" type="password" size="30" maxlength="40">
	</p>
	<p>
	<h3>Neues Passwort best&auml;tigen</h3>
	<input name="newPassword2" type="password" size="30" maxlength="40">
	</p>
<button>&Auml;ndern</button>