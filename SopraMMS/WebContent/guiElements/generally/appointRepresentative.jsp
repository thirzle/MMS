<script>
	$(document).ready(function() {
		$(".cancelButton").click(function(e) {
		$(".contentBox").load("/SopraMMS/guiElements/generally/showNoRepresentative.jsp");});})
</script>

<form id="appointR" action="/SopraMMS/AppointRepresentative"
	method="get">
	<h1>Stellvertreter beantragen</h1>
	<%
		if ((session.getAttribute("lessDataRep") != null)) {
	%>
	<error>Alle Felder m&uuml;ssen ausgef&uuml;llt werden!</error>
	<%
		} else if (session.getAttribute("wrongDataRep") != null) {
			if(session.getAttribute("wrongDataRep").equals("wrongName")){
				%>
				<error>Name oder E-Mail ist falsch!</error>
				<%				
			}
			else if(session.getAttribute("wrongDataRep").equals("yourselfRep")){
				%>
				<error>Sie k&ouml;nnen sich nicht selbst zum Stellvertreter ernennen!</error>
				<%				
			}

		}
	%>
	<table>
		<tr>
			<td>Vorname:</td>
			<td><input name="firstNameRep" type="text" size="30"
				maxlength="30"></td>
		</tr>
		<tr>
			<td>Nachname:</td>
			<td><input name="lastNameRep" type="textx" size="30"
				maxlength="30"></td>
		</tr>
		<tr>
			<td>E-Mail:</td>
			<td><input name="mailRep" type="email" size="30" maxlength="30"></td>
		</tr>
	</table>
		
	<input onclick="appointR.submit()" type="submit"
		name="appointRepresentative" value="Zum Stellvertreter ernennen" />
</form>