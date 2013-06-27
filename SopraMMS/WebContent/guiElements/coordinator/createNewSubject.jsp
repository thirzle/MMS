<script src="/SopraMMS/js/inputmanager.js"></script>
<h1>Studienfach erstellen</h1>

<p>Legen Sie ein Fach an, um ihm Module zuzuweisen.</p>
<%
if(session.getAttribute("wrongDataCreateSubject") != null){
	session.removeAttribute("wrongDataCreateSubject");
	%>
	<error>Dieses Studienfach existiert bereits.</error>
	<%
}
%>
<form name="submit" action="/SopraMMS/CreateSubject" method="get">
<table>
	<tr>
		<td>Name:</td>
		<td>
			<input class="inputField" name="name" type="text"/>
		</td>
	</tr>
</table>
<br>
	<button value="Neues Studienfach anlegen">Neues Studienfach anlegen</button>
</form>
<script>
checkInput(null);
</script>