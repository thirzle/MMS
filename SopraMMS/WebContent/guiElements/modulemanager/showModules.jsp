
<%@page import="management.Module" import="java.util.LinkedList"%>
<%
	LinkedList<Module> moduleList = (LinkedList) session
			.getAttribute("moduleListForModulemanager");
	session.removeAttribute("moduleListForModulemanager");
%>

<h1>Modul bearbeiten</h1>
<form action="/SopraMMS/ShowEditModule" method="get">
	<table class="informationAboutModule">
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
		<%
			for (Module module : moduleList) {
		%>
		<tr>
			<td><input type="radio" name="selectedModuleToEdit"
				value='<%=module.getModuleID()%>' /></td>
			<td><%=module.getName()%></td>
			<td><%=module.getModificationauthor()%></td>
			<td><%=module.getSubject()%></td>
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
	</table>
	<button type="submit" name="editButton" value="editButton">Modul bearbeiten</button>
</form>