<%@page import="java.util.LinkedList"%>
<%@page import="user.UserAdministration"%>

<%
	UserAdministration ua = new UserAdministration();
	LinkedList<String[]> list = new LinkedList();
	list.addAll(ua.getNews(true));

	for (String[] entry : list) {
		out.println("<h1>" + entry[0] + "</h1>");
		out.println("<p>" + entry[1] + "</p>");
		out.println("<author>" + entry[2] + "</author>");
	}
%>