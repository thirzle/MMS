<h1>Lebenslauf anlegen</h1>
<form id="currvitaeForm" method="get" action="/SopraMMS/CurrVitae">
	<%
		if (!session.getAttribute("currVitae").equals("")) {
	%>

	<table>
		<tr>
			<td>Ihr aktueller Lebenslauf: <a
				href='<%=session.getAttribute("currVitae")%>' target="_blank"><%=session.getAttribute("currVitae")%></a>

			</td>
		</tr>
	</table>

	<%
		}
	%>
	<p>Bitte geben Sie eine URL ein die auf Ihren Lebenslauf verweist:</p>
	<table>

		<tr>
			<td><input class="inputField" name="currurl" id="currurl"
				type="url" size="60" value="<%=session.getAttribute("currVitae")%>"></td>
		</tr>
	</table>
	<br>
	<button name="action" value="delete">Lebenslauf löschen</button>
	<button name="action" value="add">Lebenslauf ändern</button>

</form>