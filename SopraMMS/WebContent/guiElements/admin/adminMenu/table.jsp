<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css" type="text/css"
	media="print, projection, screen" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/script.js"></script>

<%@ page import="controller.UserDBController"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="user.User"%>
<%
	// TODO - In Logik auslagern
	List<User> users;
	try{
		UserDBController controller = new UserDBController();
		users = controller.getAllUsers();
	} catch(NullPointerException e) {
		users = new ArrayList<User>();
	}
%>
<table id="userTable" class="tablesorter">
	<thead>
		<tr>
			<th>Benutzername</th>
			<th>Vorname</th>
			<th>Nachname</th>
			<th>Email</th>
			<th>Recht</th>
			<th>Institut</th>
			<th>Stellvertreter</th>
		</tr>
	</thead>
	<tbody id="userTableBody">
		<%
			for(int i=0; i<users.size();i++) {
		%>
		<tr>
			<td><%=users.get(i).getLogin()%></td>
			<td><%=users.get(i).getFirstName()%></td>
			<td><%=users.get(i).getLastName()%></td>
			<td><%=users.get(i).getMail()%></td>
			<td>nicht implementiert</td>
			<td>nicht implementiert</td>
			<td><%=users.get(i).getRepresentative()%></td>
		</tr>
		<%
			}
		%>
	</tbody>
</table>
<form id="addUserForm"
	action="/SopraMMS/guiElements/admin/adminMenu/userManagement/addUser.jsp"
	method="post">
	<input type="submit" name="speichern" id="saveButton"
		style="float: right; display: none;" value="Speichern" ></input>
</form>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-latest.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.tablesorter.js"></script>
<script type="text/javascript">
	$(function() {
		$("#userTable").tablesorter({
			sortList : [ [ 0, 0 ], [ 2, 1 ] ],
			widgets : [ 'zebra' ]
		});
	});
</script>