<%
 
     String username=(String)session.getAttribute("loginName");
    if(username!=null)
        {
           out.println(username+" loged out, <a href=\"welcome.jsp\">Back</a>");
            session.removeAttribute("username");
             
        }
     else 
         {
         out.println("You are already not login <a href=\"welcome.jsp\">Back</a>");
     }
 
 
 
%>  