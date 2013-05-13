	<%@page import="user.UserAdministration"%>
<%
		String loginname = request.getParameter("loginname");
		String password = request.getParameter("password");
		out.println("Checking login<br>");
		if (loginname == null || password == null) { 
			out.print("Invalid paramters ");
		}
		
		// Create the class UserAdministrator to get a Connection to the UserDBController
		UserAdministration userAdmin = new UserAdministration();
		
		String sessionID = session.getId();
		
		// Here you put the check on the username and password
		if (userAdmin.login(loginname, password, sessionID)!=null) {
			
			session.setAttribute("loginname", loginname);
			session.setAttribute("session", sessionID);
			response.sendRedirect("../admin/adminHome.jsp");
		} else {
			out.println("Invalid loginname and password");
			response.sendRedirect("error.jsp");
		}
	%> 
