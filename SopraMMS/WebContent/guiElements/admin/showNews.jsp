<%@page import="java.util.List"%>
<%@page import="user.UserAdministration"%>
<%
	UserAdministration ua = new UserAdministration();
	List<String[]> newsList = ua.getNewsByType(0);
	
%>
<h1>&Uuml;bersicht Neuichkeiten</h1>

<table class='showNewsTable'>
  <tr>
    <th>Titel</th>
    <th>Text</th>
     <th>Sichtbarkeit</th>
      <th>Erstellungsdatum</th>
  </tr>
  <%for(String[] entry:newsList){
	  String[] text = entry[1].split(" ");
	    StringBuilder prev = new StringBuilder();
	    for(int i=0;i<6&&i<text.length;i++)
	    {
	    	prev.append(text[i]+" ");
	    }
	    prev.append("...");
	    
	  String type="";
	  if(entry[3].equals("0"))
	  {
		  type="Front-End Back-End";
	  }
	  else if(entry[3].equals("1"))
	  {
		  type="Front-End";
	  }else if(entry[3].equals("2"))
	  {
		  type="Back-End";
	  }
  %>
  
  <tr>
    <td><%=entry[0]%></td>
    <td><%=prev.toString()%></td>
    <td><%=type %></td>
    <td><%=entry[2]%></td>
  </tr>
  
  <%} %>
</table>


