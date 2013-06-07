
<%@page import="management.Module" import="java.util.LinkedList"%>
<%
	LinkedList<Module> moduleList = (LinkedList) session
			.getAttribute("moduleList");
%>

<h1>Modul bearbeiten</h1>
<form action="ShowModule" method="get">
	<table class="informationAboutModule">
		<tr>
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
			<input type="radio" name="selectedModule" value='<%=module.getModuleID()%>'/>
			<td>Titel: <%out.print(module.getName());%></td>
			<td>Autor: <%=module.getModificationauthor()%></td>
			<td>Fach: <%=module.getSubject()%></td>
			<td>Modulhandbuch: <%=module.getModuleManual()%></td>
			<td>Erstellungsdatum: <%=module.getCreationDate()%></td>
			<td>&Auml;nderungsdatum: <%=module.getModificationDate()%></td>
			<td>Freigabestatus: <%=module.isApproved()%></td>
		</tr>
		<%
			}
		%>
	</table>
	<input type="submit" name="editButton" value="Modul bearbeiten"/>
</form>