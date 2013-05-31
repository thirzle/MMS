<%@page import="java.util.List"%>
<%@page import="user.UserAdministration"%>
<h1>Home Content</h1>

<h2>Modul Management System</h2>
<p>
	Sie befinden sich im Modul Management System.
</p>

<% 
 UserAdministration ua = new UserAdministration();
 	List<String[]>list = ua.getNewsByType(true);
  for(String[] entry : list){
 	out.println("<h1>"+entry[0]+"</h1>");
 	out.println("<p>"+entry[1]+"</p>");
  	out.println("<div class='contentBoxDate'> Erstellt am "+entry[2]+"</div>");
  } 
%>