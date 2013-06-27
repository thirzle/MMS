<h1>Studiengang erstellen</h1>

<div class="importantBox">Beachten Sie, dass der Studiengang
	vertraglich von der Universit&auml;t Ulm angeboten werden muss, bevor
	dieser im System erstellt werden darf.</div>
<p>Bitte tragen Sie K&uuml;rzel, Namen und Abschluss des neuen
	Studiengangs ein.</p>

<%
if(session.getAttribute("wrongDataCreateCourse") != null){
	if(session.getAttribute("wrongDataCreateCourse").equals("wrongCourseID")){
		session.removeAttribute("wrongDataCreateCourse");
		%>
		<error>Dieses Kürzel existiert bereits.</error>
		<%
	}
	else if(session.getAttribute("wrongDataCreateCourse").equals("wrongCourseDescription")){
		session.removeAttribute("wrongDataCreateCourse");
		%>
		<error>Dieser Studiengang existiert bereits.</error>
		<%
	}
}
%>
<form name="submit" action="/SopraMMS/CreateCourse" method="get">
	<table>
		<tr>
			<td>K&uuml;rzel:</td>
			<td><input class="inputField" name="courseID" type="text" /></td>
		</tr>
		<tr>
			<td>Studiengang:</td>
			<td><input class="inputField" name="description" type="text" /></td>
		</tr>
		<tr>
			<td>Abschluss:</td>
			<td><select class="inputField" name="degree" style="width: 142px">
					<option value="0">Bachelor</option>
					<option value="1">Master</option>
					<option value="2">Lehramt</option>
			</select></td>
		</tr>
	</table>
	<br>
	<button value="Neuen Studiengang anlegen">Neuen Studiengang
		anlegen</button>
</form>