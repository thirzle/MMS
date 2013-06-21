<h1>Studiengang erstellen</h1>
<%if(session.getAttribute("courseCreated")!=null) { 
	session.removeAttribute("courseCreated");
%>
<p> Der Studiengang wurde erfolgreich erstellt <p>
<%} else { %>
<p> Bitte tragen sie K&uuml;rzel, Namen und Abschluss des neuen Studiengangs ein.</p>
<%} %>
 <form name="submit" action="/SopraMMS/CreateCourse" method="get">
<table>
	<tr>
			<td>K&uuml;rzel:</td>
			<td>
				<input name="courseID" type="text"/>
			</td>
	</tr>
	<tr>		
			<td>Studiengang:</td>
			<td>
				<input name="description" type="text"/>
			</td>
	</tr>
		<tr>		
			<td>Abschluss:</td>
			<td>
				<select name="degree" style="width: 142px">
						<option value="0">Bachelor</option>
						<option value="1">Master</option>
						<option value="2">Lehramt</option>
			    </select>
			</td>
	</tr>
</table>
	<input type="submit" value="Neuen Studiengang anlegen">
</form>