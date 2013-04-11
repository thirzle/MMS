<%
            String username = request.getParameter("loginName");
            String password = request.getParameter("password");
           out.println("Checking login<br>");
            if (username == null || password == null) {
 
                out.print("Invalid paramters ");
            }
 
            // Here you put the check on the username and password
            if (username.toLowerCase().trim().equals("admin") && password.toLowerCase().trim().equals("admin")) {
                out.println("Welcome " + username + " <a href=\"index.jsp\">Back to main</a>");
                session.setAttribute("username", username);
            }
           else 
               {
                out.println("Invalid username and password");
           }
 
%> 