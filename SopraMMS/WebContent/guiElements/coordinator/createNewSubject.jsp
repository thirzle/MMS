<h1>Studienfach erstellen</h1>
<%if(session.getAttribute("subjectCreated")!=null) { 
	session.removeAttribute("subjectCreated");
%>
<p> Das Studienfach wurde erfolgreich erstellt <p>
<%} else { %>
<p> Bitte tragen sie das neue Studienfach ein.</p>
<%} %>
 <form name="submit" action="/SopraMMS/CreateSubject" method="get">
<table>
	<tr>
		<td>Name:</td>
		<td>
			<input name="name" type="text"/>
		</td>
	</tr>
</table>
	<input type="submit" value="Neues Studienfach anlegen">
</form>