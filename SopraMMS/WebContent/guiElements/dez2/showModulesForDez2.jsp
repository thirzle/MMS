<link rel="stylesheet" href="/SopraMMS/css/style.css" type="text/css" media="print, projection, screen" />
<script type="text/javascript" src="/SopraMMS/js/jquery.tablesorter.js"></script>
<script type="text/javascript" src="/SopraMMS/js/tablemanager.js"></script>
<%@page import="management.Module" import="management.Entry" import="java.util.LinkedList"%>
<%
	LinkedList<Module> moduleList = (LinkedList) session
			.getAttribute("moduleListForDez2");
%>

<h1>Module</h1>
<form action="/SopraMMS/DisableModule" method="get">
	<table class="tablesorter informationAboutModule" id="modulesForDez2">
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
					value='<%=module.getModuleID() + " " + module.getVersion()%>' /></td>
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
						boolean rejected = false;
						if (module.isApproved()) {
								out.println("Freigegeben");
							} else {
								for(Entry entry: module.getEntryList()){
									if(entry.isRejected()){
										rejected = true;
										break;
									}
								}
								if(rejected){
									out.println("Abgelehnt");
								} else {
									out.println("Offen");
								}
							}
					%>
				</td>
			</tr>
			<%
				}
			%>
		</tbody>
	</table>
	<%if(session.getAttribute("showVersions")!=null){
		session.removeAttribute("showVersions");
	%>
	<button type="submit" name="showButton" value="showButton">Modul
		kontrollieren</button>
	<%} else { %>
	<button type="submit" name="showButton" value="showButton">Modul
		kontrollieren</button>
	
	<%} %>
</form>
<script>
manageTable($('#modulesForDez2'));
</script>