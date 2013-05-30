<%@page import="user.User"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/default.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Neue Nachricht</title>
</head>
<div class="menuBox">
<body>
	<h1>Neue Nachricht</h1>
	<form method="POST" action="/SopraMMS/Mail">
	<table cellpadding="10" cellspacing="10">
		<tr>
			<th align="left" colspan="2">Absender:</th>
			<th align="left"><%User user = (User) session.getAttribute("user");
				out.print(user.getMail());%></th>
		</tr>
		<tr>
			<th align="left" colspan="2">Empfänger:</th>
			<th align="left" colspan="4"><input type="text" name="mailto"
				size="60"></th>
		</tr>
		<tr>
			<th align="left" colspan="2">Betreff:</th>
			<th align="left" colspan="4"><input type="text" name="subject"
				size="60"></th>
		</tr>
		<tr>
			<th align="left" colspan="2">Nachricht:</th>
			<th align="left" colspan="4"><textarea cols="90" rows="20"
					name="message"></textarea></th>
		</tr>
	</table>
	<table cellpadding="10" cellspacing="10" align=right>
		<tr>
			<td><input type="hidden" name="action" value="send"> <input
				type="SUBMIT" value="Senden">
			</td>
			<td><input type="hidden" name="action" value="cancel"> <input
				type="SUBMIT" value="Abbrechen"></td>
	</table>
	</form>
	</body>
</div>
</html>