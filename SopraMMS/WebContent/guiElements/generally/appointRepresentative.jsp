<h1>Stellvertreter beantragen</h1>
<form id="appointR" action="/SopraMMS/AppointRepresentative" method="get">
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
			<td><input name="mailRep" type="text" size="30" maxlength="30"></td>
		</tr>
	</table>
<input type="submit" name="cancel" value="Abbrechen" />
<input class=".appointRepresentative" onclick="appointR.submit()" type="submit"
	name="appointRepresentative" value="Zum Stellvertreter ernennen" />
</form>