<h1>Lebenslauf anlegen</h1>
<form id="currvitaeForm" method="get" action="/SopraMMS/CurrVitae">
	<table cellpadding="10" cellspacing="10">
		<tr>
			<td align="left" colspan="2">Bitte geben Sie eine URL ein die
				auf Ihren Lebenslauf verweißt:</td>
		</tr>
		<tr>
			<td align="left"><input name="currurl" id="currurl" type="url"
				size="60" value="<%=session.getAttribute("currVitae")%>"></td>
		</tr>
		<tr>
			<td><button name="action" value="delete">Lebenslauf
					löschen</button>
				<button name="action" value="add">Lebenslauf ändern</button></td>
		</tr>
	</table>
</form>