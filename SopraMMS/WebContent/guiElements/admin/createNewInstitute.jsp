<%@page import="user.User"%>
<%@page import="java.util.LinkedList"%>
<h1>Neues Institut erstellen</h1>
<p>Bitte tragen sie K&uuml;rzel, Namen und den Verantwortlichen ein.</p>
<%
	if (session.getAttribute("wrongDataNewInst") != null) {
		if (session.getAttribute("wrongDataNewInst").equals("wrongID")) {
			session.removeAttribute("wrongDataNewInst");
%>
<error>Diese InstitutsID existiert bereits!</error>
<%
	} else if (session.getAttribute("wrongDataNewInst").equals(
				"wrongName")) {
			session.removeAttribute("wrongDataNewInst");
%>
<error>Dieser Institutsname existiert bereits!</error>
<%
	}
	}
%>

<form name="submit" action="/SopraMMS/CreateInstitute" method="get">
	<table>
		<tr>
			<td>K&uuml;rzel:</td>
			<td><input class="inputField" type="text" name="instituteID"></td>
		</tr>
		<tr>
			<td>Institutsname:</td>
			<td><input class="inputField" type="text" name="instituteName"></td>
		</tr>
	</table>
	<br>
	<button value="Neues Institut anlegen">Neues Institut anlegen</button>
</form>
