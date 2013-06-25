<%@page import="java.util.List"%>
<%@page import="user.UserAdministration"%>

<%
	String infoText = request.getParameter("infotext");
	if (infoText != null) {
%>
<div class="infoBox"><%=infoText%></div>
<%
	}
%>

<%
	String infoError = request.getParameter("errortext");
	if (infoError != null) {
%>
<div class="errorBox"><%=infoError%></div>
<%
	}
%>

<h1>Home Content</h1>

<h2>Modul Management System</h2>
<p>Sie befinden sich im Modul Management System.</p>
<%
	UserAdministration ua = new UserAdministration();
	List<String[]> list = ua.getNewsByType(1);
	for (String[] entry : list) {
		out.println("<h1>" + entry[0] + "</h1>");
		out.println("<div class='contentTextBox'><p>" + entry[1] + "</p></div>");
		out.println("<div class='contentBoxDate'> Erstellt am "
				+ entry[2] + "</div>");
	}
%>
