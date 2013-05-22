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
	String[] status = {"Modulver.","Redakteur","Administrator","right[3]","right[4]","right[5]"};
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
			<%	boolean[] rights = users.get(i).getRights();
				String right = "";
				for(int j=0; j<rights.length; j++) {
				  if(rights[j]) {
					  if(right != "") {
				  		right += ";"+status[j];
					  }else{
						  right = status[j];
					  }
				  }
			  	}%>
			<td><%=right %></td>
			<%List<String> institutes = users.get(i).getInstitute(); 
			  String institute = "";
			  for(int j=0; j<institutes.size(); j++) {
				  if(institute != "") {
					  	institute += ";"+institutes.get(j);;
					  }else{
						  institute = institutes.get(j);;
					  }
			  }%>
			<td><%=institute %></td>
			<td><%=users.get(i).getRepresentative()%></td>
		</tr>
		<%
			}
		%>
	</tbody>
</table>
<form id="addUserForm" action="SaveUser" method="get">
	<input type="submit" name="Submit" id="saveButton"
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