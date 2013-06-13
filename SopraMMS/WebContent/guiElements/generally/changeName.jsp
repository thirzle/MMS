<%@page import="user.User"%>

<h1>Name &auml;ndern</h1>
<p>Sie sind angemeldet als</p>
<%
	User user = (User) session.getAttribute("user");
	out.print(user.getFirstName() + " " + user.getLastName());
%>
<form action="/SopraMMS/ChangeName" method="get">
	<table>
		<tr>
			<td>Neuer Vorname:</td>
			<td><input name="newFirstname" type="text" size="30"
				maxlength="30" /></td>
		</tr>
		<tr>
			<td>Neuer Nachname:</td>
			<td><input name="newLastname" type="text" size="30"
				maxlength="30" /></td>
		</tr>
	</table>
	<input type="submit" name="newName" value="&Auml;ndern" />
</form>