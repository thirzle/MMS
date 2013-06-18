<%@page import="user.User"%>
<%@page import="model.UserDBController"%>
<%@page import="user.UserAdministration"%>

<%
	User user = (User) session.getAttribute("user");
	String lebenslauf = ("Lebenslauf von " + user.getFirstName() + " " + user
			.getLastName());
	String loginname = user.getLogin();
	UserAdministration uAdmin = new UserAdministration();
	//read the url from the database
	String url = uAdmin.getCurriculum(loginname);
%>
<h1>Lebenslauf ansehen</h1>
<table id="currtable" cellpadding="10" cellspacing="10">
	<tr>
		<td id="text1" align="left" colspan="2">Vorhandener Lebenslauf:</td>
		<td>
			<%
				if (url == null || url == "") {
			%>Es ist kein Lebenslauf vorhanden, bitte legen Sie einen Lebenslauf
			an.<%
				} else {
			%><a href='<%=url%>'> <%
 	out.print(lebenslauf);
 	}
 %> </a></td>
	</tr>
</table>