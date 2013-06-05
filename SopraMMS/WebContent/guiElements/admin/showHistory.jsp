<link rel="stylesheet" href="/SopraMMS/css/style.css" type="text/css" media="print, projection, screen" />
<%@page import="java.util.LinkedList"%>
<%
	LinkedList<String[]> userHistory = (LinkedList) session.getAttribute("history");
%>
<h1>Historie</h1>
<p>Aktivit&auml;ten aller Benutzer:</p>
	<table id="showHistoryTable" class='tablesorter'>
	<tr>
		<th>Benutzername:</th>
		<th>Datum:</th>
		<th>Aktivit&auml;t:</th>
	</tr>
	<%
		for(String[] string : userHistory){
	%>
	<tr>
		<td><% out.println(string[0]);%></td>
		<td><%out.println(string[1]);%></td>
		<td><%out.println(string[2]);%></td>
	</tr>

	<%
		}
	%>
</table>