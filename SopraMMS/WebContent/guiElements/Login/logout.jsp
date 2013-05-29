
<%@page import="user.User"%>
<%
	String lastname = ((User) session.getAttribute("user"))
			.getLastName();
	String firstname = ((User) session.getAttribute("user"))
			.getFirstName();
%>
<div class="menuBox">
	<form action="/SopraMMS/guiElements/Login/doLogout.jsp" method="post">
		<h1>Logout</h1>
		
		<h3>Sie sind angemeldet als:</h3>
		<h3>
			<%
				out.println(lastname + " " + firstname);
			%>
		</h3>
		<div class="menuContent">
		<br />
		<input type="submit" name="Submit" value="Abmelden" />
		</div>
	</form>
</div>


