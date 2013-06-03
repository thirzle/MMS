<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/moduleView.css">
<%
	session.setAttribute("content", "createNewModule");

	String[] requiredFieldsTypeA = { "K&uuml;rzel", "Titel", "Dauer",
			"LP", "Turnus", "Sprache", "Pr&uuml;fungsform",
			"Notenbildung" };
	String[] requiredFieldsTypeB = { "Inhalt", "Lernziele", "Literatur" };
%>
<h1>Neues Modul erstellen</h1>


<%
	for (String description : requiredFieldsTypeA) {
%>
<div class='moduleEntry'>
	<table>
		<tr>
			<td class='descriptionModule'><%=description%></td>
			<td class='entryModule'><textarea name='<%=description%>Content' rows="1" cols="50"
					style="resize: none;"></textarea></td>
		</tr>
	</table>
</div>
<%
	}
	for (String description : requiredFieldsTypeB) {
%>
<div class='moduleEntry'>
	<table>
		<tr>
			<td class='descriptionModule'><%=description%></td>
			<td class='entryModule'><textarea name='<%=description%>Content' rows="5" cols="50"
					style="resize: none;"></textarea></td>
		</tr>
	</table>
</div>
<%
	}
%>


