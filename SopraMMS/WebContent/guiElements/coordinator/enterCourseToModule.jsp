<%@page import="management.CourseEntry"%>
<%@page import="management.Entry"%>
<%@ page import="java.util.List, java.util.ArrayList"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/moduleView.css">
<%
	ArrayList<Entry> entryList = new ArrayList();
	Object list = session.getAttribute("showEntryListFromModule");
	System.out.println(list);
	if (list != null) {
		entryList.addAll((List<Entry>) list);
	}
	
	
%>
<h1>Studiengang und Fach festlegen</h1>

<table class="informationAboutModule">
	<tr>
		<td>&Auml;nderungsdatum: <%=session.getAttribute("showModificationDateFromModule")%></td>
		<td>Erstellungsdatum: <%=session.getAttribute("showCreationDateFromModule")%></td>
		<td>Autor: <%=session.getAttribute("showModificationauthorFromModule")%>
	</tr>
</table>

<form action="/SopraMMS/EnterCourseToModule" method="get">
	
	
	<!-- Fächer eintragen & Studiengang + Pflicht oder nicht -->
	
	
	<%for (Entry entry : entryList) {
				String[] entryText = new String[2];
				entryText[0] = entry.getTitle();
				entryText[1] = entry.getContent();%>
	<div class='moduleEntry'>
		<table>
			<tr>
				<td class='descriptionModule'><%=entryText[0]%></td>
				<td class='entryModule'><%String[] text=entryText[1].split("\n");
				for(String row:text){
					out.println(row+"<br/>");
				}%></td>
			</tr>
		</table>
	</div>
	<%}%>
	<button type="submit" value="addRow" name="createModule">Zeile
		hinzuf&uuml;gen</button>
	<button type="submit" value="saveModule" name="createModule">Modul
		f&uuml;r Sitzung speichern</button>
	<button type="submit" value="sendModule" name="createModule">Modul
		einreichen</button>

</form>