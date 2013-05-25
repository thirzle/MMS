<%@page import="user.User"%>


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

//TODO Parameter koennen nicht abgerufen werden
String content = session.getAttribute("content")+"";
session.setAttribute("content", null);
if(content.equals("changedPwStatusOldPwWrong")){
%>
<p>
	<error>Das eingegebene Passwort war falsch</error>
</p>
<%
	}else if(content.equals("changedPwStatusPw12Wrong")) {
%>
<p>
	<error>Die neuen Passw&ouml;rter stimmen nicht &uuml;ber ein</error>
</p>
<%
	}else if(content.equals("changedPwStatusdone")){%>
<p>
	<h2>Das Passwort wurde erfolgreich ge&auml;ndert</h2>
</p>
<%
}
%>
<form action="/SopraMMS/ChangePassword" method="get">
	<h3>Altes Passwort</h3>
	<input name="oldPassword" type="password" size="30" maxlength="30">


	<h3>Neues Passwort</h3>
	<input name="newPassword1" type="password" size="30" maxlength="40">


	<h3>Neues Passwort best&auml;tigen</h3>
	<input name="newPassword2" type="password" size="30" maxlength="40">
	<p>
		<input type="submit" name="Submit" value="Passwort &Auml;ndern"
			class="btnChangePw" />
	</p>
</form>