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
			for (Course course : courses) {
		%>
		<option value=<%=course.getDescription()+":"+course.getDegree()%>>
		<%=course.getDescription()+" "+course.getDegree()%></option>
		<%
			}
		%>
	</select><br>
	<p>Geben Sie die aktuelle Pr&uuml;fungsordnung ein:</p>
	<input name="examRegulation" type="text" size="30" maxlength="30"/>
	<br> <button type="submit" name="Submit" id="showEntries" value="Modulhandbuch erstellen" >Modulhandbuch erstellen</button>
</form>
<script>
$(document).ready(function() {
	enableButton();
	$("input[type=text]").keyup(function() {
		enableButton();
	});
	function enableButton() {
		if (!($("input[type=text]")[0].value == "")) {
			$('button[type=submit]').each(function() {
				if ($(this).attr('disabled')) {
					$(this).removeAttr('disabled');
				}
			});
		} else {
			$('button[type=submit]').each(function() {
				$(this).attr({
					'disabled' : 'disabled'
				});
			});
		}
	}
});
</script>

