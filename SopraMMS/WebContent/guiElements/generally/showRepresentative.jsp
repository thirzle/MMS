<%@page import="user.UserAdministration"%>
<%@page import="user.User"%>
<%@page import="java.util.LinkedList"%>


<h1>Stellvertreter</h1>
<p>Ihr aktueller Stellvertreter:</p>
<table>
	<tr>
		<td>Vorname:</td>
		<td>
			<%
				String representativeName = (String) session.getAttribute("representative");
				//String representativeName = "lehrd";
				User user = new UserAdministration().getUser(representativeName);
				out.println(user.getFirstName());
			%>
		</td>
	</tr>
	<tr>
		<td>Nachname:</td>
		<td>
			<%
				out.println(user.getLastName());
			%>
		</td>
	</tr>
	<tr>
		<td>E-Mail:</td>
		<td>
			<%
				out.println(user.getMail());
			%>
		</td>
	</tr>
	<tr>
		<td>Fakult&auml;t:</td>
		<td>
			<%
				out.println(new UserAdministration().getFacultyName(user));
			%>
		</td>
	</tr>
	<tr>
		<td>Institut:</td>
		<%
			LinkedList<String> instituteList = (LinkedList) new UserAdministration().getInstituteNames(user);
		%>
		<td>
			<%
				out.println(instituteList.getFirst());
				instituteList.removeFirst();
			%>
		</td>
		<%
			for (String institute : instituteList) {
		%>

	<tr>
		<td></td>
		<td><%
			out.println(institute);
		%>
		</td>
	</tr>
	<%
		}
	%>
	</tr>
	
</table>