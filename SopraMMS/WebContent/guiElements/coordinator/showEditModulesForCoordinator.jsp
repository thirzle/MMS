


<%@page import="management.Module" import="java.util.LinkedList"%>
<%
	LinkedList<Module> moduleList = (LinkedList) session
	.getAttribute("editModuleListForCoordinator");


boolean editUnapprovedModule=false;

if(request.getParameter("edit")!=null){
	editUnapprovedModule=true;
}
%>

<h1>Module &uuml;berarbeiten</h1>

<%
	if (request.getParameter("info") != null) {
%>
<error>Bitte w&auml;hlen Sie ein Modul aus.</error>
<%
	}
%>

<form action="/SopraMMS/ShowEditModuleOverviewForCoordinator"
	method="get">
	<table class='tablesorter' id="modulesForCoordinator">
		<thead>
			<tr>
				<th></th>
				<th>Titel</th>
				<th>Version</th>
				<th>Autor</th>
				<th>Fach</th>
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
					value='<%=module.getModuleID()%> <%=module.getVersion()%>' /></td>
				<td><%=module.getName()%></td>
				<td><%=module.getVersion()%></td>
				<td><%=module.getModificationauthor()%></td>
				<td>
					<%
						if (module.getSubject() != null) {
																out.println(module.getSubject());
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

	<%
		if(editUnapprovedModule){
	%>
	<button type="submit" name="editButton" value="editUnapprovedModule">Modul
		bearbeiten</button>
	<%
		}else {
	%><button type="submit" name="editButton" value="editButton">Modul
		bearbeiten</button>
	<%
		}
	%>


	<!-- <button type="submit" name="action" value="editModule">Modul
		bearbeiten</button> -->
	<button type="submit" name="showVersionsButton"
		value="showVersionsButton">Alle Versionen anzeigen</button>
</form>

<script type="text/javascript" src="/SopraMMS/js/tablemanager.js"></script>
<script type="text/javascript" src="/SopraMMS/js/jquery.tablesorter.js"></script>
<script>
	manageTable($('#modulesForCoordinator'));
</script>
