<link rel="stylesheet" href="/SopraMMS/css/jquery.tablescroll.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="/SopraMMS/css/style.css" type="text/css" media="print, projection, screen" />

<%@page import="java.util.LinkedList"%>
<%
	LinkedList<String[]> userHistory = (LinkedList) session.getAttribute("history");
%>
<h1>Historie</h1>
<p>Aktivit&auml;ten aller Benutzer:</p>
<table id="showHistoryTable" cellspacing="0" width="95%">
	<thead>
		<tr>
			<th>Benutzername:</th>
			<th>Datum:</th>
			<th>Aktivit&auml;t:</th>
		</tr>
	</thead>
	<tbody>
		<%
			for(int i=userHistory.size()-1; i > 0; i--) {
		%>
		<tr>
			<td><% out.println(userHistory.get(i)[0]);%></td>
			<td><%out.println(userHistory.get(i)[1]);%></td>
			<td><%out.println(userHistory.get(i)[2]);%></td>
		</tr>
		<%
			}
		%>
	</tbody>
</table>

<script type="text/javascript" src="/SopraMMS/js/jquery.showHistory.js"></script>
<script type="text/javascript" src="/SopraMMS/js/jquery.tablescroll.js"></script>