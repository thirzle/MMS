<%@page import="java.util.ArrayList"%>
<%@page import="user.UserAdministration"%>
<%@page import="user.User"%>
<%@page import="java.util.LinkedList"%>
<%
	String[] userArray = (String[]) session
			.getAttribute("repUserArray");
%>
<h1>Stellvertreter</h1>
<p>Ihr aktueller Stellvertreter:</p>

<form id="removeRep" action="/SopraMMS/RemoveRepresentative"
	method="get">
	<table class="userdata">
		<tr>
			<td>Vorname:</td>
			<td><%=userArray[0]%></td>
		</tr>
		<tr>
			<td>Nachname:</td>
			<td><%=userArray[1]%></td>
		</tr>
		<tr>
			<td>E-Mail:</td>
			<td><%=userArray[2]%></td>
		</tr>
		<tr>
			<td>Fakult&auml;t:</td>
			<td><%=userArray[3]%></td>
		</tr>
		<tr>
			<%
				ArrayList<String> instituteList = (ArrayList) session
						.getAttribute("instituteListRep");
			%>
			<td>Institut(e):</td>
			<td>
				<ul>
					<li>
						<%
							out.println(instituteList.get(0));
							instituteList.remove(0);
						%>

					</li>
					<%
						for (String institute : instituteList) {
					%>
					<li>
						<%
							out.println(institute);
						%> <br>
					</li>
					<%
						}
					%>
				</ul>
			</td>
		</tr>

	</table>
	<p>
	<%
	session.setAttribute("mailRep", userArray[2]);
	session.removeAttribute("repUserArray");
	session.removeAttribute("instituteListRep");
	%>
		<input onclick="removeRep.submit()" type="submit"
			name="StellvertreterEntfernen" value="Stellvertreter entfernen" />
</form>
</p>