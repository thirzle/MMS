<link rel="stylesheet" href="/SopraMMS/css/style.css" type="text/css" media="print, projection, screen" />
	
<%@ page import="model.UserDBController"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="user.User"%>
<%
	List<User> users;
	try {
	    users = (List<User>) session.getAttribute("users");
		String[] status = {"Modulver.","Redakteur","Administrator","Recht auf Stellv.","Dezernat II","right[5]","right[6]"};
%>
<form name="editUserForm" action="/SopraMMS/EditUser" method="get">

<table id="userTable" class="tablesorter">
	<thead>
		<tr>
			<th></th>
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
			<td><input type="radio" name="selectedRowID" value='<%=users.get(i).getLogin() %>'/></td>
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
		} catch(NullPointerException e) {
		    System.out.println("(table.jsp): ACHTUNG USER IST NULL");
		}
		%>
	</tbody>
</table>
	<input type="submit" value="Bearbeiten"/>
</form>
<%if(session.getAttribute("errormessage") != null) { %>
	<p style="color: #FF0000"><%= session.getAttribute("errormessage") %></p>
<%} %>


<script type="text/javascript" src="/SopraMMS/js/jquery.usertable.js"></script>
<script type="text/javascript" src="/SopraMMS/js/jquery.tablesorter.js"></script>
