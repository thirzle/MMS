
<%@page import="management.SelfStudy"%>
<%@page import="management.EffortEntry"%>
<%@page import="management.Entry"%>
<%@page import="java.util.LinkedList"%>
<%@page import="management.Module"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/moduleView.css">

<h1>Modul zurücksetzen</h1>
<%
	Module module = (Module) session
			.getAttribute("moduleForViewModule");
%>

<table class="informationAboutModule">
	<tr>
		<td>&Auml;nderungsdatum: <%=module.getModificationDate()%></td>
		<td>Erstellungsdatum: <%=module.getCreationDate()%></td>
		<td>Autor: <%=module.getModificationauthor()%>
	</tr>
</table>

<form action="/SopraMMS/DisableModule" method="get">
	<%
		LinkedList<Entry> entryList = (LinkedList) module.getEntryList();
		String institute = (String) session
				.getAttribute("instituteForViewModule");

		for (int i = 0; i < entryList.size() && i < 6; i++) {
			Entry entry = entryList.get(i);
	%>
	<div class='moduleEntry'>
		<table>
			<tr>
				<td class='descriptionModule'><%=entry.getTitle()%></td>

				<td class='entryModule'><notEditable> <%
 	String list[] = entry.getContent().split("\n");
 		for (String s : list) {
 			out.println("" + s + "<br>");
 		}
 %> </notEditable></td>

			</tr>
		</table>
	</div>
	<%
		}
	%>
	<div class='moduleEntry'>
		<table>
			<tr>
				<td class='descriptionModule'>Institut</td>
				<td class='entryModule'><notEditable><%=institute%></notEditable></td>
			</tr>
		</table>
	</div>
	<div class='moduleEntry'>
		<%
			if (module.getSubject() != null) {
		%>
		<table>
			<tr>
				<td class='descriptionModule'>Fach</td>
				<td class='entryModule'><notEditable><%=module.getSubject()%></notEditable></td>
			</tr>
		</table>
		<%
			}
		%>
	</div>

	<%
		for (int i = 6; i < entryList.size(); i++) {
			Entry entry = entryList.get(i);
	%>
	<div class='moduleEntry'>
		<table>
			<tr>
				<td class='descriptionModule'><%=entry.getTitle()%></td>

				<td class='entryModule'><notEditable> <%
 	String list[] = entry.getContent().split("\n");
 		for (String s : list) {
 			out.println("" + s + "<br>");
 		}
 %> </notEditable></td>

			</tr>
		</table>
	</div>
	<%
		}
	%>
	<br>
	<button name="action" value="disable">Modul zurücksetzen</button>
	<button name="action" value="ok">Modul akzeptieren</button>
</form>
