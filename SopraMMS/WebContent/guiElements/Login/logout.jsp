
	<div class="menuBox">
		<form action="/SopraMMS/guiElements/Login/doLogout.jsp" method="post">
			<h1>Logout</h1>
	
			<h3>Sie sind angemeldet als: </h3>
		<% out.println("&nbsp;"+session.getAttribute("loginname"));
		%>
			<p class="menuContent">
				<input type="submit" name="Submit" value="Abmelden"/>
			</p>
		</form>
	</div>


