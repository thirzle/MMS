<%@page import="management.Entry"%>
<%@ page import="java.util.List, java.util.ArrayList"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/moduleView.css">
<%
	ArrayList<Entry> entryList = new ArrayList();
	entryList.addAll((List<Entry>) session
			.getAttribute("showEntryListFromModule"));
%>
<h1>Studiengang und Fach festlegen</h1>

<form action="/SopraMMS/EnterCourseToModule" method="get">
	<%
		for (Entry entry : entryList) {
			String[] entryText = new String[2];
			entryText[0] = entry.getTitle();
			entryText[1] = entry.getContent();
	%>
	<div class='moduleEntry'>
		<table>
			<tr>
				<td class='descriptionModule'><%=entryText[0]%></td>
				<td class='entryModule'><%=entryText[1]%></td>
			</tr>
		</table>
	</div>
	<%
		}
	%>
	<button type="submit" value="addRow" name="createModule">Zeile
		hinzuf&uuml;gen</button>
	<button type="submit" value="saveModule" name="createModule">Modul
		f&uuml;r Sitzung speichern</button>
	<button type="submit" value="sendModule" name="createModule">Modul
		einreichen</button>

</form>


