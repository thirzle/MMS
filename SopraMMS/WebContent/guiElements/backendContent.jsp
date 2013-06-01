<%@page import="java.util.List"%>
<%@page import="user.UserAdministration"%>
<%
	UserAdministration ua = new UserAdministration();
	List<String[]> list = ua.getNewsByType(2);
	for (String[] entry : list) {
		out.println("<h1>" + entry[0] + "</h1>");
		out.println("<p>" + entry[1] + "</p>");
		out.println("<div class='contentBoxDate'> Erstellt am "
				+ entry[2] + "</div>");
	}
%>