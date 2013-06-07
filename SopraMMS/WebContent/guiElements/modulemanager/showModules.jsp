
<%@page import="management.Module" import="java.util.LinkedList"%>
<%
	LinkedList<Module> moduleList = (LinkedList) session
			.getAttribute("moduleList");
%>

<h1>Modul bearbeiten</h1>
<form action="/SopraMMS/CreateModule" method="get">
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
			<td><input type="radio" name="selectedModule"
				value='<%=module.getModuleID()%>' /></td>
			<td><%=module.getName()%></td>
			<td><%=module.getModificationauthor()%></td>
			<td><%=module.getSubject()%></td>
			<td><%=module.getModuleManual()%></td>
			<td><%=module.getCreationDate()%></td>
			<td><%=module.getModificationDate()%></td>
			<td><%=module.isApproved()%></td>
		</tr>
		<%
			}
		%>
	</table>
	<%
	session.setAttribute("edit", "true");
	%>
	<input type="submit" name="editButton" value="Modul bearbeiten" />
</form>