<link rel="stylesheet" href="/SopraMMS/css/style.css" type="text/css"
	media="print, projection, screen" />
<script type="text/javascript" src="/SopraMMS/js/jquery.tablesorter.js"></script>
<script type="text/javascript"
	src="/SopraMMS/js/jquery.showModulForEditor.js"></script>

<%@page import="management.Module" import="java.util.LinkedList"%>
<%
	LinkedList<Module> moduleList = (LinkedList) session
			.getAttribute("moduleListForEditor");
	session.removeAttribute("moduleListForEditor");
	for(Module module : moduleList){
		System.out.println(module.getName());
	}
%>

<h1>Freizugebende Module</h1>
<%
	//TODO
%>
<form action="/SopraMMS/ShowApproveModuleForEditor" method="get">
	<table class="tablesorter informationAboutModule" id="modulesForEditor">
		<thead>
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
		</thead>
		<tbody>
			<%
				for (Module module : moduleList) {
			%>
			<tr>
				<td><input type="radio" name="selectedModuleToApprove"
					value='<%=module.getModuleID() + " " + module.getVersion()%>' /></td>
				<td><%=module.getName()%></td>
				<td><%=module.getVersion() %></td>
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
		anschauen</button>
</form>