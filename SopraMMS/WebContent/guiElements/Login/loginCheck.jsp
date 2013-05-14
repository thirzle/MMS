
<%@page import="user.UserAdministration"%>
<%
	String loginname = request.getParameter("loginname");
	String password = request.getParameter("password");
	String sessionID = session.getId();
	
	/* out.println("Checking login<br>");
	if (loginname == null || password == null) {
		out.print("Invalid paramters ");
	} */
	
	// Create the class UserAdministrator to get a Connection to the UserDBController
	UserAdministration userAdmin = new UserAdministration();
	
	if(session.getAttribute("loginname")==null)
	{
		// login
		// Here you put the check on the username and password
		if (userAdmin.login(loginname, password, sessionID) != null) {
			session.setAttribute("loginname", loginname);
			response.sendRedirect("../admin/adminHome.jsp");
		} else {
			System.out.println("Invalid loginname and password");
			response.sendRedirect("error.jsp");
		}
	}
	else if(userAdmin.checkLogin(sessionID).getLogin().equals(session.getAttribute("loginname").toString()))
	{
		System.out.println("User ist eingelogt Seite kann neu geladen werden");
		response.sendRedirect("../admin/adminHome.jsp");
	}
	else{
		System.out.println("User ist nicht eingeloggt oder SessionID stimmt nicht");
		response.sendRedirect("error.jsp");
	}
	
%>
