<%@page import="java.util.LinkedList"%>
<%@page import="user.UserAdministration,java.util.List"%>
<%
	String faculty = (String) session.getAttribute("faculty");
	LinkedList<String> courses = (LinkedList) session
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
		<option value=<%=i%>><%="Bachelor-"+courses.get(i)%></option>
		<option value=<%=courses.size()+i%>><%="Master-"+courses.get(i)%></option>
		<%
			}
		%>
	</select><br>
	<br> <input type="submit" name="Submit" id="showEntries"
	<%//TODO PDF automatisieren %>
		value="Modulhandbuch erstellen" />
</form>

<script>
	$(".expandAdministration").toggleClass("expanded");
	$(".expandAdministration").children("ul:first").slideToggle("fast");
	e.stopPropagation();
</script>
