<%@page import="java.util.ArrayList"%>
<%
String[] userArray = (String[]) session.getAttribute("showUserDataArray");
ArrayList<String> rights = (ArrayList<String>) session.getAttribute("rightsForShowUserData");
ArrayList<String> instituteList = (ArrayList<String>) session.getAttribute("instituteListForUserData");
%>
<h1>Benutzerdaten</h1>
<div>
	<table>
		<tr>
			<td>Benutzername: </td>
			<td><%=userArray[0]%></td>
		</tr>
		<tr>
			<td>Name: </td>
			<td><%=userArray[1]%></td>
		</tr>
		<tr>
			<td>E-Mail: </td>
			<td><%=userArray[2]%></td>
		</tr>
		<tr>
			<td>Stellvertreter: </td>
			<td><%=userArray[3]%></td>
		</tr>
		<tr>
			<td>Stellvertreter f&uuml;r: </td>
			<td><%=userArray[4]%></td>
		</tr>
		<tr>
			<td>Fakult&auml;t: </td>
			<td><%=userArray[5]%></td>
		</tr>
		<tr>
			<td>Institut(e): </td>
			<td>
				<ul>
				<%
				for(String institute : instituteList){
				%>
				<li><%=institute%></li>
				<%} %>
				</ul>
			</td>
		</tr>
		<tr>
			<td>Rechte: </td>
			<td>
				<ul>
				<%
				for(String right : rights){
				%>
				<li><%=right%></li>
				<%} %>
				</ul>
			</td>
		</tr>
	</table>
</div>