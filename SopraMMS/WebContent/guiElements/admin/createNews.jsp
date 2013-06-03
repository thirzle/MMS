<h1>Neuigkeit verfassen</h1>

<%
	session.setAttribute("content", "createNews");
	if (request.getParameter("newsstatus") != null) {
		if (request.getParameter("newsstatus").equals(
				"choosevisibility")) {
%>
<error>Bitte w&auml;hlen Sie mindestens eine Sichtbarkeitsoption.</error>
<%
	}if (request.getParameter("newsstatus").equals("addtitleortext"))
	{
		%>
		<error>Bitte tragen Sie immer einen Titel und einen Text in die daf&uulm;r vorgesehenen Felder ein.</error>
		<%
	}
	}

	String text = "";
	if (request.getParameter("text") != null) {
		text = request.getParameter("text");
	}
%>

<form action="/SopraMMS/AddNews" method="get">
	<table>
		<tr>
			<td>Titel:</td>
			<td><input name="title" type="text" size="30" maxlength="50"
				value="<%if (request.getParameter("title") != null) {
				out.println(request.getParameter("title"));
			}%>"></td>
		</tr>
		<tr>
			<td>Text:</td>
			<td><textarea name="content" rows="10" cols="50" style="resize:none;"><%=text.trim()%></textarea></td>
		</tr>
		<tr>
			<td>Sichtbarkeit:</td>
			<td><input type="checkbox" name="frontend" value="true">
				Front-End <input type="checkbox" name="backend" value="true">
				Back-End
		</tr>
	</table>
	<input type="submit" value="Veröffentlichen">

</form>