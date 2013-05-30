<%@page import="java.util.List"%>
<%@page import="user.UserAdministration"%>

<% 
	UserAdministration ua = new UserAdministration();
	List<String[]> list = ua.getNews(false);
	
	for(String [] entry: list){
		out.println("<h1>"+entry[0]+"</h1>");
		out.println("<p>"+entry[1]+"</p>");
		out.println("<author>"+entry[2]+"</author>");
	}
%>