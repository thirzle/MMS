<%@page import="management.CourseEntry"%>
<%@page import="management.Entry"%>
<%@ page import="java.util.List, java.util.LinkedList, management.Course"%>
<script type="text/javascript" src="/SopraMMS/js/jquery.multiple.select.js"></script>
<script type="text/javascript" src="/SopraMMS/js/jquery.entercoursetomodule.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/moduleView.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/multiple-select.css">
<%
try {
	List<String> subjects = (LinkedList<String>) session
			.getAttribute("subjects");
	List<Course> courses = (LinkedList<Course>) session
			.getAttribute("courses");
	List<Entry> entryList = new LinkedList();
	Object list = session.getAttribute("showEntryListFromModule");
	System.out.println(list);
	if (list != null) {
		entryList.addAll((List<Entry>) list);
	}
%>
<h1>Studiengang und Fach festlegen</h1>

<div class="importantBox" style="width : 600px">
Ein Modul kann nicht gleichzeitig als Pflicht- und Wahlmodul f&uuml;r einen Studiengang ausgew&auml;hlt werden.
</div>

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


	<!-- F�cher eintragen & Studiengang + Pflicht oder nicht -->
	<div class='moduleEntry'>
		<table>
			<tr>
				<td class='descriptionModule'>Fach:</td>
				<td class='entryModule'><select class="inputField" onchange="getSelectedIndex()" id="subject" style="width: 250px;">
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
				<td class='descriptionModule'>Plichtmodul f�r:</td>
				<td>
					<select id="obligatoryModulSelect" style="width: 250px;">
						<%int i = 0;
						  for(Course course : courses) { %>
							<option value=<%=i %>><%=course %></option>
						<%i++;}%>
						<option value="empty">Keins</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class='descriptionModule'>Wahlpflichtmodul f�r:</td>
				<td width="450px">
					<select id="voteModuleSelect" style="width: 250px;">
						<%int j=0;
						  for(Course course : courses) { %>
							<option value=<%=j %>><%=course %></option>
						<%j++;}%>
						<option value="empty">Keins</option>
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
				<td class='entryModule'><notEditable>
					<%
						String[] text = entryText[1].split("\n");
								for (String row : text) {
									out.println(row + "<br/>");
								}
					%>
				</td></notEditable>
			</tr>
		</table>
	</div>
	<%
		}
	%>
	<br>
	<button type="submit" value="sendModule" name="createModule" id="submitModule" disabled>Modul
		einreichen</button>
	<textarea name="obligatoryModulSelect" style="display: none;" id="obligatoryLabel"></textarea>
	<textarea name="voteModuleSelect" style="display: none;" id="voteLabel"></textarea>
	<textarea name="subjectSelect" style="display: none;" id="subjectLabel">0</textarea>
</form>

<%
	} catch (NullPointerException e) {
		System.out.println("nullpointerexception");
	}
%>
<script type="text/javascript">
initMultiSelect();
</script>