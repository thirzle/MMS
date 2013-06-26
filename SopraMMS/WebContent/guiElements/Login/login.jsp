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
			<input class="inputField" name="loginname" type="text" size="29" />
		</p>

		<h3>Passwort:</h3>
		<br>
		<p class="menuContent">
			<input class="inputField" name="password" type="password" size="29" />

		</p>
		<p class="menuContent">
			<input type="submit" name="Submit" value="Anmelden" />
		</p>
	</form>
</div>
