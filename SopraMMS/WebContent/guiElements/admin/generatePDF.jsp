<%@page import="management.Course"%>
<%@page import="java.util.LinkedList"%>
<%@page import="user.UserAdministration,java.util.List"%>
<%
	String faculty = (String) session.getAttribute("faculty");
	LinkedList<Course> courses = (LinkedList<Course>) session
	.getAttribute("courses");
%>

<form id="showEntries" onsubmit="setValues()"
	action="/SopraMMS/GeneratePDF" method="get">
	<h1>Neue PDF erstellen</h1>
	<p>
		<%
			out.println(faculty);
		%>
	</p>
	<p>W&auml;hlen Sie einen Studiengang aus:</p>
	<select name="course" id="course" style="width: 270px">
		<%
			for (int i = 0; i < courses.size(); i++) {
		%>
		<option value=<%=i%>><%="Bachelor-"+courses.get(i).getDescription()%></option>
		<option value=<%=courses.size()+i%>><%="Master-"+courses.get(i).getDescription()%></option>
		<%
			}
		%>
	</select><br>
	<p>Geben Sie die aktuelle Pr&uuml;fungsordnung ein:</p>
	<input name="examRegulation" type="text" size="30" maxlength="30"/>
	<br> <input type="submit" name="Submit" id="showEntries"
	<%//TODO PDF automatisieren %>
		value="Modulhandbuch erstellen" />
</form>

<script>
	$(".expandAdministration").toggleClass("expanded");
	$(".expandAdministration").children("ul:first").slideToggle("fast");
	e.stopPropagation();
</script>
