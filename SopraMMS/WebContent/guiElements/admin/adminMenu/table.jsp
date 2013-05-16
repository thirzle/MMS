<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" media="print, projection, screen" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/script.js"></script>

<%@ page import ="testsuite.TempKlasse" %>
<%@ page import ="user.User" %>
<%
	TempKlasse tmp = new TempKlasse();
	User[]	users = tmp.getUsers();
%>
	<table id="userTable" class="tablesorter" >
		<thead>
			<tr>
				<th>Benutzername</th>
				<th>Vorname</th>
				<th>Nachname</th>
				<th>Email</th>
				<th>Recht</th>
				<th>Institut</th>
				<th>Supervisor</th>
			</tr>
		</thead>
		<tbody id="userTableBody">
		<% for(int i=0; i<users.length;i++) { %>
			<tr>
				<td><%=users[i].getLogin() %></td>
				<td><%=users[i].getFirstName() %></td>
				<td><%=users[i].getLastName() %></td>
				<td><%=users[i].getMail() %></td>
				<td>nicht implementiert</td>
				<td>nicht implementiert</td>
				<td><%=users[i].getSupervisor() %></td>
			</tr>
		<%} %>
		</tbody>
	</table>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-latest.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.tablesorter.js"></script>
<script type="text/javascript">
$(function() {	
	$("#userTable").tablesorter({sortList:[[0,0],[2,1]], widgets: ['zebra']});
});	
</script>