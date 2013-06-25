
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
<h1>&Uuml;bersicht Neuigkeiten</h1>
<h3>W�hlen sie die Module aus, die gel�scht werden sollen</h3>

<form action="/SopraMMS/DeleteModule">
	<table id="deleteModuleTable" class='tablesorter'>
		<thead>
			<tr>
				<th style="width: 1%;"></th>
				<th>Titel</th>
				<th>Version</th>
				<th>Autor</th>
				<th>Fach</th>
				<th>Erstellungsdatum</th>
				<th>�nderungsdatum</th>
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
	<button value="true" name="delete">L&ouml;schen</button>
	<br><br>
	<div class="importantBox">Die ausgew�hlten Module werden
		endg�ltig aus dem Module Management System gel�scht und k�nnen nicht
		wieder hergestellt werden.</div>
</form>
<script>
manageTable($("#deleteModuleTable"));
</script>
