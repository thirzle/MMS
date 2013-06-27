
<%@page import="java.text.SimpleDateFormat"%>
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script src="/SopraMMS/js/jquery.datepicker.translate.js"></script>
<script src="/SopraMMS/js/inputmanager.js"></script>
<h1>Stichtag</h1>
<%
	String content = request.getParameter("submitDeadline") + "";
	if (content.equals("deadBeforeRemem")) {
%>
<p>Folgender Stichtag ist momentan f&uuml;r ihre Fakul&auml;t
	festgelegt.</p>
<p>
	<error>Der Stichtag darf nicht vor dem Erinnerungsbeginn liegen</error>
</p>
<%
	} else if (content.equals("deadBeforeToday")) {
%>
<p>Folgender Stichtag ist momentan f&uuml;r ihre Fakul&auml;t
	festgelegt.</p>
<p>
	<error>Der Stichtag darf nicht in der Vergangenheit liegen</error>
</p>
<%
	} else if (content.equals("done")) {
%>
<p>Der Stichtag wurde erfolgreich geändert</p>
<%
	}
%>
<form name="change" action="/SopraMMS/Deadline" method="get">
	<table>
		<tr>
			<td>Stichtag:</td>
			<td><input class="inputField" name="deadline" type="text"
				id="datepicker1"
				value="<%=new SimpleDateFormat("dd.MM.yyyy").format(session
					.getAttribute("deadline"))%>" />
			</td>
		</tr>
		<tr>
			<td>Erinnerungsbeginn:</td>
			<td><input class="inputField" name="beginremember" type="text"
				id="datepicker2"
				value="<%=new SimpleDateFormat("dd.MM.yyyy").format(session
					.getAttribute("beginremember"))%>" />
			</td>
		</tr>
	</table>
	<br>
	<button type="submit" value="Stichtag ändern">Stichtag ändern</button>
</form>
<script>
	$(function() {
		$("#datepicker1").datepicker();

	});
	$(function() {
		$("#datepicker2").datepicker();
	});
	checkInput(null);
</script>