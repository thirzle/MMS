<h1>Lebenslauf anlegen</h1>
<form id="currvitaeForm" method="get" action="/SopraMMS/CurrVitae">
	<%
		if (!session.getAttribute("currVitae").equals("")){
	%>

	<table cellpadding="10" cellspacing="10">
		<tr>
			<td>Ihr aktueller Lebenslauf: <a
				href='<%=session.getAttribute("currVitae")%>' target="_blank"><%=session.getAttribute("currVitae")%></a>

			</td>
		</tr>
	</table>

	<%
		}
	%>

	<table cellpadding="10" cellspacing="10">
		<tr>
			<td align="left" colspan="2">Bitte geben Sie eine URL ein die
				auf Ihren Lebenslauf verwei�t:</td>
		</tr>
		<tr>
			<td align="left"><input class="inputField" name="currurl" id="currurl" type="url"
				size="60" value="<%=session.getAttribute("currVitae")%>"></td>
		</tr>
		<tr>
			<td><button name="action" value="delete">Lebenslauf
					l�schen</button>
				<button name="action" value="add">Lebenslauf �ndern</button></td>
		</tr>
	</table>
</form>