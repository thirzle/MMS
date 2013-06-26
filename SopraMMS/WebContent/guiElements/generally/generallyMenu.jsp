<script>
	$(document)
			.ready(
					function() {
						$(".changePassword")
								.click(
										function(e) {
											$(".contentBox")
													.load(
															"/SopraMMS/guiElements/generally/changePassword.jsp");
										});
					});
	$(document)
			.ready(
					function() {
						$(".changeName")
								.click(
										function(e) {
											$(".contentBox")
													.load(
															"/SopraMMS/guiElements/generally/changeName.jsp");
										});
					});
	//Lisa
	//$(document).ready(function() {
	//$(".newMsg").click(function(e) {
	//$(".contentBox").load("/SopraMMS/guiElements/generally/newMessage.jsp");//});});
</script>

<%
	if (session.getAttribute("generallyMenu") != null) {
		session.setAttribute("generallyMenu", null);
%>

<%
	}
	session.removeAttribute("generallyMenu");
%>



<div class="menuBox">
	<h1>Allgemein</h1>

	<ul class="nav">
		<div class="headerNavAccount">
			<li>Account verwalten</li>
		</div>
		<div class="expandAccount">
			<ul class="subNav">
				<form id="showUserData" action="/SopraMMS/ShowUserData" method="get">
					<li class=showData onclick="showUserData.submit()">Benutzerdaten
						einsehen</li>
				</form>
				<li class=changePassword>Passwort &auml;ndern</li>
				<li class=changeName>Name &auml;ndern</li>
				<form id="showR" action="/SopraMMS/ShowRepresentative" method="get">
					<li class=showRepresentative onclick="showR.submit()">Stellvertreter</li>
				</form>

			</ul>
		</div>
		<div class="headerNavMessages">
			<li>Nachrichten</li>
		</div>
		<div class="expandMessages">
			<ul class="subNav">
				<!-- <li class=newMsg><a href="/SopraMMS/NewMessage">Neue Nachricht</a></li> -->
				<li id="newMsg"><a href="/SopraMMS/LoadTable?task=mail">Neue
						Nachricht</a></li>
			</ul>
	</ul>
</div>
