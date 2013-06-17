<%@page import="management.CourseEntry"%>
<%@page import="management.Entry"%>
<%@ page import="java.util.List, java.util.ArrayList"%>
<script type="text/javascript" src="/SopraMMS/js/jquery.multiple.select.js"></script>
<script type="text/javascript" src="/SopraMMS/js/jquery.entercoursetomodule.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/moduleView.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/multiple-select.css">
<%
	List<String> subjects = (ArrayList<String>) session
			.getAttribute("subjects");
	List<String[]> courses = (ArrayList<String[]>) session
			.getAttribute("courses");
	ArrayList<Entry> entryList = new ArrayList();
	Object list = session.getAttribute("showEntryListFromModule");
	System.out.println(list);
	if (list != null) {
		entryList.addAll((List<Entry>) list);
	}
	try {
		System.out.println("(enterCourseToModule.jsp): subjects="+subjects);
		System.out.println("(enterCourseToModule.jsp): courses="+courses);
%>
<h1>Studiengang und Fach festlegen</h1>

<table class="informationAboutModule">
	<tr>
		<td>&Auml;nderungsdatum: <%=session
						.getAttribute("showModificationDateFromModule")%></td>
		<td>Erstellungsdatum: <%=session.getAttribute("showCreationDateFromModule")%></td>
		<td>Autor: <%=session
						.getAttribute("showModificationauthorFromModule")%>
	</tr>
</table>

<form action="/SopraMMS/EnterCourseToModule" method="get">


	<!-- Fächer eintragen & Studiengang + Pflicht oder nicht -->
	<div class='moduleEntry'>
		<table>
			<tr>
				<td class='descriptionModule'>Fächer:</td>
				<td class='entryModule'><select style="width: 250px;">
						<%int a=0;
							for (String subject : subjects) {
						%>
						<option value=<%=a %>><%=subject%></option>
						<%
							a++;}
						%>
				</select></td>
			</tr>
		</table>
	</div>
	<div class='moduleEntry'>
		<table>
			<tr>
				<td class='descriptionModule'>Plichtmodul für:</td>
				<td>
					<select id="obligatoryModulSelect" style="width: 250px;">
						<%int i = 0;
						  for(String[] course : courses) { %>
							<option value=<%=i %>><%=course[1] %></option>
						<%i++;} %>
					</select>
				</td>
			</tr>
			<tr>
				<td class='descriptionModule'>Wahlpflichtmodul für:</td>
				<td width="450px">
					<select id="voteModuleSelect" style="width: 250px;">
						<%int j=0;
						  for(String[] course : courses) { %>
							<option value=<%=j %>><%=course[1] %></option>
						<%j++;} %>
					</select>
				</td>
			</tr>
		</table>
	</div>


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
				<td class='entryModule'>
					<%
						String[] text = entryText[1].split("\n");
								for (String row : text) {
									out.println(row + "<br/>");
								}
					%>
				</td>
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
	<button type="submit" value="sendModule" name="createModule" id="submitModule" disabled>Modul
		einreichen</button>
	<textarea name="obligatoryModulSelect" style="display: none;" id="obligatoryLabel"></textarea>
	<textarea name="voteModuleSelect" style="display: none;" id="voteLabel"></textarea>
</form>
<%
	} catch (NullPointerException e) {
		System.out.println("nullpointerexception");
	}
%>
<script type="text/javascript">
initMultiSelect();
</script>