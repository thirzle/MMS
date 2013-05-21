
<%@page import="user.User"%>
<%@page import="user.UserAdministration"%>
<%
	String loginname = request.getParameter("loginname");
	String password = request.getParameter("password");
	String sessionID = session.getId();

	UserAdministration userAdmin = new UserAdministration();

	if (session.getAttribute("loginname") == null) {
		// login
		// Here you put the check on the username and password
		User user = userAdmin.login(loginname, password, sessionID);
		if (user != null) {
			session.setAttribute("user", user);
			session.setAttribute("loginname", loginname);
			session.setAttribute("rights", user.getRights());

			response.sendRedirect("/SopraMMS/guiElements/home.jsp");
		} else {
			System.out.println("Invalid loginname and password");
			//TODO in bestehenden Content oeffnen und nicht neue Seite oeffnen
			response.sendRedirect("/SopraMMS/guiElements/error.jsp");
		}
	} else if (userAdmin.checkLogin(sessionID).getLogin()
			.equals(session.getAttribute("loginname").toString())) {
		System.out
				.println("User ist eingelogt Seite kann neu geladen werden");
		response.sendRedirect("/SopraMMS/guiElements/home.jsp");
	} else {
		System.out
				.println("User ist nicht eingeloggt oder SessionID stimmt nicht");
		response.sendRedirect("/SopraMMS/guiElements/error.jsp");
	}
%>
