<%@page import="user.UserAdministration"%>
<%@page import="user.User"%>


<h1>Stellvertreter</h1>
<p>Ihr aktueller Stellvertreter:</p>
<table>
	<tr>
		<td>Vorname: <%
			//String representativeName = (String) session.getAttribute("representative");
			String representativeName = "lehrd";
			User user = new UserAdministration().getUser(representativeName);
			out.println(user.getFirstName());
		%>
		</td>
	</tr>
</table>