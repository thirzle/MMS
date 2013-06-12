<%@page import="user.User"%>
<%@page import="model.UserDBController"%>

<%
	User user = (User) session.getAttribute("user");
	String lebenslauf = ("Lebenslauf von " + user.getFirstName() + " " + user
			.getLastName());
%>
<h1>Lebenslauf ansehen</h1>
<table id="currtable" cellpadding="10" cellspacing="10">
	<tr>
		<td id="text1" align="left" colspan="2">Vorhandener Lebenslauf:</td>
		<td><a id="text2" onclick="curravail();">
				<%
					out.print(lebenslauf);
				%>
		</a>
		</td>
	</tr>
</table>
<div id="currvitae" style="visibility: hidden">
	<!-- user.getCurr() -->
</div>
</body>


<!-- TODO URL aus DB auslesen-->
<!-- TESTURL http://localhost:8080/SopraMMS/guiElements/home.jsp -->

<script type="text/javascript" src="/SopraMMS/js/jquery.currvitae.js"></script>