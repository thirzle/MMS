<link rel="stylesheet" href="/SopraMMS/css/style.css" type="text/css" media="print, projection, screen" />
<script type="text/javascript" src="/SopraMMS/js/jquery.tablesorter.js"></script>
<script type="text/javascript" src="/SopraMMS/js/jquery.showModulesForModulmanager.js"></script>
<%@page import="management.Module" import="java.util.LinkedList"%>
<%
	LinkedList<Module> moduleList = (LinkedList) session
			.getAttribute("moduleListForModulemanager");
%>

<h1>Modul bearbeiten</h1>
<form action="/SopraMMS/ShowEditModule" method="get">
	<table class="tablesorter informationAboutModule" id="modulesForModulmanager">
		<thead>
			<tr>
				<th></th>
				<th>Titel</th>
				<th>Autor</th>
				<th>Fach</th>
				<th>Modulhandbuch</th>
				<th>Erstellungsdatum</th>
				<th>&Auml;nderungsdatum</th>
				<th>Freigabestatus</th>
			</tr>
		</thead>
		<tbody>
			<%
				for (Module module : moduleList) {
			%>
			<tr>
				<td><input type="radio" name="selectedModuleToEdit"
					value='<%=module.getModuleID() + " " + module.getVersion()%>' /></td>
				<td><%=module.getName()%></td>
				<td><%=module.getModificationauthor()%></td>
				<td>
					<%
						if (module.getSubject() != null) {
								out.println(module.getSubject());
							}
					%>
				</td>
				<td>
					<%
						if (module.getModuleManual() != null) {
								out.println(module.getModuleManual());
							}
					%>
				</td>
				<td><%=module.getCreationDate()%></td>
				<td><%=module.getModificationDate()%></td>
				<td>
					<%
						if (module.isApproved()) {
								out.println("Freigegeben");
							} else {
								out.println("Offen");
							}
					%>
				</td>
			</tr>
			<%
				}
			%>
		</tbody>
	</table>
	<button type="submit" name="editButton" value="editButton">Modul
		bearbeiten</button>
</form>