<h1>Neuigkeit verfassen</h1>
<p>
	Bitte tragen Sie Titel, Inhalt und Sichtbarkeit der Neuigkeit ein.<br>
	Front-End bedeutet hierbei, dass jeder die Nachricht lesen kann, wobei
	Nachrichten für das Back-End nur eingeloggten Benutzen angezeigt
	werden.
</p>
<%
	// ask for Parameter
	// print status if it is available
	session.setAttribute("content", "createNews");
	if (request.getParameter("newsstatus") != null) {
		// no option selected
		if (request.getParameter("newsstatus").equals(
				"choosevisibility")) {
%>
			<error>Bitte w&auml;hlen Sie mindestens eine
			Sichtbarkeitsoption.</error>
<%
	}
		// no text written
		if (request.getParameter("newsstatus").equals("addtitleortext")) {
%>
			<error>Bitte tragen Sie immer einen Titel und einen Text in die
			dafür vorgesehenen Felder ein.</error>
<%
		}
	}
	// fill variable for textbox
	String text = "";
	if (request.getParameter("text") != null) {
		text = request.getParameter("text");
	}
%>

<form action="/SopraMMS/AddNews" method="get">
	<table>
		<tr>
			<td>Titel:</td>
			<td><input class="inputField" name="title" type="text" size="30"
				maxlength="50"
				value="<%
					// load title if available
					if (request.getParameter("title") != null) {
						out.println(request.getParameter("title"));
				}%>">
			</td>
		</tr>
		<tr>
			<td>Text:</td>
			<td><textarea name="content" rows="10" cols="50"
					style="resize: none;"><%=text.trim()%></textarea>
			</td>
		</tr>
		<tr>
			<td>Sichtbarkeit:</td>
			<td>
				<input type="checkbox" name="frontend" value="true">
				Front-End
				<input type="checkbox" name="backend" value="true">
				Back-End
		</tr>
	</table>
	<br>
	<button value="Veröffentlichen">Veröffentlichen</button>
</form>
