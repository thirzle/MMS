<%@page import="user.User"%>
<%@page import="model.UserDBController"%>

<%
	User user = (User) session.getAttribute("user");
%>
<h1>Lebenslauf ansehen</h1>
<table id="currtable" cellpadding="10" cellspacing="10">
	<tr>
		<td id="text1" align="left" colspan="2">Vorhandener Lebenslauf:</td>
		<td><a id="text2" onclick="curravail();">Lebenslauf</a></td>
	</tr>
</table>
<div id="currvitae" style="visibility: hidden">http://localhost:8080/SopraMMS/guiElements/home.jsp</div>
</body>
<!-- TODO URL aus DB auslesen-->

<script type="text/javascript" src="/SopraMMS/js/jquery.currvitae.js"></script>