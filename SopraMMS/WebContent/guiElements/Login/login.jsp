<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$(".lostPassword")
								.click(
				function(e) {$(".contentBox").load("/SopraMMS/guiElements/Login/sendNewPassword.jsp");
						});});
</script>

<div class="menuBox">
	<form action="/SopraMMS/Login" method="get">
		<h1>Login</h1>

		<%
			String loginStatus = request.getParameter("loginStatus");
			if (loginStatus != null) {
		%>
		<p class="loginFailed">
			Anmeldung fehlgeschlagen<br /> &rarr;&emsp;<a class=lostPassword>Passwort
				vergessen</a>
		</p>
		<%
			}
		%>
		<h3>Benutzername:</h3>
		<br>
		<p class="menuContent">
			<input class="inputField" id="loginPw" name="loginname" type="text" size="29" />
		</p>
			<h3>Passwort:</h3>
		<br>
		<p class="menuContent">
			<input class="inputField" id="loginPw" name="password" type="password" size="29" />

		</p>
		<p class="menuContent">
			<input type="submit" name="Submit" value="Anmelden" />
		</p>
	</form>
</div>
<div class="importantBox" style="width : 200px">
Falls Sie sich im MMS registrieren m&ouml;chten, kontaktieren Sie bitte einen der Administratoren.
</div>
