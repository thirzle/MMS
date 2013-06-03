<%@page import="user.User"%>

	<h1>Neue Nachricht</h1>
	<form id="sendMailForm" method="get" action="/SopraMMS/Mail"></form>
	<table cellpadding="10" cellspacing="10">
		<tr>
			<th align="left" colspan="2">Absender:</th>
			<th align="left"><%User user = (User) session.getAttribute("user");
				out.print(user.getMail());%></th>
		</tr>
		<tr>
			<th align="left" colspan="2">Empfänger:</th>
			<th align="left" colspan="4">
				<input form="sendMailForm" type="email" name="mailto" size="60">
			</th>
		</tr>
		<tr>
			<th align="left" colspan="2">Betreff:</th>
			<th align="left" colspan="4"><input form="sendMailForm" type="text" name="subject"
				size="60"></th>
		</tr>
		<tr>
			<th align="left" colspan="2">Nachricht:</th>
			<th align="left" colspan="4"><textarea form="sendMailForm" cols="77" rows="20"
					name="message"></textarea></th>
		</tr>
	</table>
	<table cellpadding="10" cellspacing="10" align=right>
		<tr>
			<td> 
				<input form="sendMailForm" type="submit" value="Senden">
			</td>
			<td>
				<input type="button" name="Cancel" value="Abbrechen" onclick="window.location = '/SopraMMS/guiElements/home.jsp' " />
			</td>
	</table>