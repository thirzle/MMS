<%@page import="java.util.List"%>
<%@page import="user.UserAdministration"%>
<h1>Home Content</h1>

<h2>Modul Management System</h2>
<p>
	Sie befinden sich im Modul Management System.
</p>

<% 
 UserAdministration ua = new UserAdministration();
 	String[][] list = ua.getNews(true);
  for(int i =0;i<list.length;i++){
 	out.println("<h1>"+list[i][0]+"</h1>");
 	out.println("<p>"+list[i][1]+"</p>");
  	out.println("<div class='contentBoxDate'> Erstellt am "+list[i][2]+"</div>");
  } 
%>