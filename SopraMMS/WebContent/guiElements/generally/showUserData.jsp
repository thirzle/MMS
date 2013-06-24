<%@page import="java.util.LinkedList"%>
<%
String[] userArray = (String[]) session.getAttribute("showUserDataArray");
LinkedList<String> rights = (LinkedList<String>) session.getAttribute("rightsForShowUserData");
LinkedList<String> instituteList = (LinkedList) session.getAttribute("instituteListForUserData");
%>
<link rel="stylesheet" type="text/css" href="/SopraMMS/css/multiple-select.css">
<h1>Benutzerdaten</h1>
<div>
	<table>
		<tr>
			<td>Benutzername:</td>
			<td><%=userArray[0]%></td>
		</tr>
		<tr>
			<td>Name:</td>
			<td><%=userArray[1]%></td>
		</tr>
		<tr>
			<td>E-Mail:</td>
			<td><%=userArray[2]%></td>
		</tr>
		<tr>
			<td>Stellvertreter:</td>
			<td><%=userArray[3]%></td>
		</tr>
		<tr>
			<td>Stellvertreter f&uml;r</td>
			<td><%=userArray[4]%></td>
		</tr>
		<tr>
			<td>Fakult&auml;t</td>
			<td><%=userArray[5]%></td>
		</tr>
		<tr>
			<td>Institut(e)</td>
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
			<td>Rechte</td>
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

<script type="text/javascript" src="/SopraMMS/js/jquery.multiple.select.js"></script>
<script type="text/javascript" src="/SopraMMS/js/jquery.edituser.js"></script>