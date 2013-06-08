<link rel="stylesheet" href="/SopraMMS/css/style.css" type="text/css"
	media="print, projection, screen" />


<%@page import="management.Module" import="java.util.LinkedList"%>
<%
	LinkedList<Module> moduleList = (LinkedList) session
			.getAttribute("unfinishedModuleListForCoordinator");
%>

<h1>Studieng&auml;nge und F&auml;cher hinzuf&uuml:gen</h1>

<%
	if (request.getParameter("info") != null) {
%>
	<error>Bitte w&auml;hlen Sie ein Modul aus.</error>
<%
	}
%>

<form action="/SopraMMS/ShowModulListForCoordinator" method="get">
	<table class='tablesorter'>
		<tr>
			<th></th>
			<th>Titel</th>
			<th>Version</th>
			<th>Autor</th>
			<th>Fach</th>
			<th>Modulhandbuch</th>
			<th>Erstellungsdatum</th>
			<th>&Auml;nderungsdatum</th>
			<th>Freigabestatus</th>
		</tr>
		<%
			for (Module module : moduleList) {
		%>
		<tr>
			<td><input type="radio" name="selectedModule"
				value='<%=module.getModuleID()%>%<%=module.getVersion()%>' /></td>
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
			<td><%=module.getModuleManual()%></td>
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
	</table>
	<button name="action" value="editModule">Modul bearbeiten</button>
	<button name="action" value="enterCourse">Stundiengang und
		Fach eintragen</button>
</form>