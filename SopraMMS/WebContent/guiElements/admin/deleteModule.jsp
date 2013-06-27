
<%@page import="java.util.ArrayList"%>
<%@page import="management.Module"%>
<script type="text/javascript" src="/SopraMMS/js/jquery.tablesorter.js"></script>
<script type="text/javascript" src="/SopraMMS/js/tablemanager.js"></script>
<link rel="stylesheet" href="/SopraMMS/css/style.css" type="text/css"
	media="print, projection, screen" />
<%@page import="java.util.List"%>
<%@page import="user.UserAdministration"%>
<%
	ArrayList<Module> moduleList = (ArrayList<Module>) session
			.getAttribute("listOfAllModules");
%>
<h1>Module l&ouml;schen</h1>
	<div class="importantBox">Die ausgewählten Module werden
		endgültig aus dem Module Management System gelöscht und können nicht
		wieder hergestellt werden.
	</div>
<p>Wählen sie die Module aus, die gelöscht werden sollen</p>

<form action="/SopraMMS/DeleteModule">
	<table id="deleteModuleTable" class='tablesorter'>
		<thead>
			<tr>
				<th></th>
				<th>Titel</th>
				<th>Version</th>
				<th>Autor</th>
				<th>Fach</th>
				<th>Erstellungsdatum</th>
				<th>Änderungsdatum</th>
				<th>Freigabestatus</th>
			</tr>
		</thead>
		<tbody>
			<%
				int index = 0;
				for (Module module : moduleList) {
			%>
			<tr>
				<td><input type="checkbox" name="moduleDelete<%=index%>"
					value="<%=module.getModuleID() + " " + module.getVersion()%>"></td>
				<td><%=module.getName()%></td>
				<td><%=module.getVersion()%></td>
				<td><%=module.getModificationauthor()%></td>
				<td><%=module.getSubject()%></td>
				<td><%=module.getCreationDate()%></td>
				<td><%=module.getModificationDate()%></td>
				<td>
					<%
						boolean rejected = false;
							if (module.isApproved()) {
								out.println("Freigegeben");
							} else {
								out.println("Offen");
							}
					%>
				</td>
			</tr>


			<%
				index++;
				}
			%>
		</tbody>
	</table>
	<button type="submit" value="true" name="delete">L&ouml;schen</button>
	<br><br>
</form>
<script>
manageTable($("#deleteModuleTable"));
</script>
