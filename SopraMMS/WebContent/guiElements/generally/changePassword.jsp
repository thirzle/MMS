<%@page import="user.User"%>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$(".btnChangePw")
								.click(
										function(e) {
											$(".contentBox")
													.load(
															"/SopraMMS/guiElements/Login/sendNewPassword.jsp");
										});
					});
</script>

<h1>Passwort &auml;ndern</h1>
<p>
	Sie sind angemeldet als
	<%
	User user = (User) session.getAttribute("user");
	out.println("" + user.getLastName() + "&nbsp;"
			+ user.getFirstName() + "&nbsp;&rarr;&nbsp;"
			+ user.getMail());
	
	
%>
</p>
<form action="/SopraMMS/ChangePassword" method="get">
	<h3>Altes Passwort</h3>
	<input name="oldPassword" type="password" size="30" maxlength="30">


	<h3>Neues Passwort</h3>
	<input name="newPassword1" type="password" size="30" maxlength="40">


	<h3>Neues Passwort best&auml;tigen</h3>
	<input name="newPassword2" type="password" size="30" maxlength="40">
	<p>
		<input type="submit" name="Submit" value="Passwort &Auml;ndern"
			class="btnChangePw" />
	</p>
</form>