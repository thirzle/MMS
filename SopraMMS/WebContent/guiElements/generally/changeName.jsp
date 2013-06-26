<%@page import="user.User"%>
<%
	User user = (User) session.getAttribute("user");
%>
<h1>Name &auml;ndern</h1>
<p>Sie sind angemeldet als<%=" "+user.getFirstName()+" "+user.getLastName()+"." %></p>

<form action="/SopraMMS/ChangeName" method="get">
	<table>
		<tr>
			<td>Neuer Vorname:</td>
			<td><input class="inputField" name="newFirstname" type="text" size="30"
				maxlength="30" /></td>
		</tr>
		<tr>
			<td>Neuer Nachname:</td>
			<td><input class="inputField" name="newLastname" type="text" size="30"
				maxlength="30" /></td>
		</tr>
	</table>
	<input type="submit" name="newName" value="&Auml;ndern" />
</form>