<script type="text/javascript" src="/SopraMMS/js/tablemanager.js"></script>
<script type="text/javascript" src="/SopraMMS/js/jquery.tablesorter.js"></script>
<%@ page import="model.UserDBController"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="user.User"%>
<%
	List<User> users = null;
	String task = "";
	try {
	    users = (List<User>) session.getAttribute("users");
		task = session.getAttribute("task").toString().trim();
		String[] status = {"Recht auf Stellv.","Dozent","Modulverantwortl.","Dezernat II","Koordinator","Freigabeberecht.","Administrator"};
%>
<form id="edit" action="/SopraMMS/EditUser" method="get"></form>
<form id="delete" action="/SopraMMS/DeleteUser" method="get"></form>

<table id="userTable" class="tablesorter">
	<thead>
		<tr>
			<th></th>
			<th class="final s">Login</th>
			<th class="final s">Vorn.</th>
			<th class="final s">Nachn.</th>
			<th class="final xxl">Email</th>
			<th class="final xxl">Recht</th>
			<th>Institut</th>
			<th class="final s">Stell.</th>
		</tr>
	</thead>
	<tbody id="userTableBody">
		<%	
			for(int i=0; i<users.size();i++) {
		%>
		<tr>
			<td><input form='<%=task %>' type="radio" name="selectedRowID" value='<%=users.get(i).getLogin() %>'/></td>
			<td><%=users.get(i).getLogin()%></td>
			<td><%=users.get(i).getFirstName()%></td>
			<td><%=users.get(i).getLastName()%></td>
			<td><%=users.get(i).getMail()%></td>
			<%	boolean[] rights = users.get(i).getRights();
				String right = "";
				for(int j=0; j<rights.length; j++) {
				  if(rights[j]) {
					  if(right != "") {
				  		right += ";"+System.getProperty("line.separator")+status[j];
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
					  	institute += ";"+System.getProperty("line.separator")+institutes.get(j);;
					  }else{
						  institute = institutes.get(j);;
					  }
			  }%>
			<td><%=institute %></td>
			<td><% String rep = users.get(i).getRepresentative();if(rep == null){rep="-";};%><%=rep %></td>
		</tr>
		<%
			}
		} catch(NullPointerException e) {
		    System.out.println("(table.jsp): ACHTUNG USER IST NULL");
		}
		%>
	</tbody>
</table>
<%
if(task.equals("edit")) { %>
	<button form="edit" type="submit" value="Bearbeiten">Bearbeiten</button>
<%} else if(task.equals("delete")) { %>
	<button form="delete" type="submit" value="L�schen">L�schen</button>
<%}
if(session.getAttribute("errormessage") != null) { %>
	<p style="color: #FF0000"><%= session.getAttribute("errormessage") %></p>
<%} %>
<script>
manageTable($('#userTable'));
</script>