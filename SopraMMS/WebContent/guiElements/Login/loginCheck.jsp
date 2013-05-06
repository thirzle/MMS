	<%
		String loginname = request.getParameter("loginname");
		String password = request.getParameter("password");
		out.println("Checking login<br>");
		if (loginname == null || password == null) { 
			out.print("Invalid paramters ");
		}
		 
		// Here you put the check on the username and password
		if (loginname.toLowerCase().trim().equals("admin") && password.toLowerCase().trim().equals("admin")) {
			session.setAttribute("loginname", loginname);
			response.sendRedirect("../admin/adminHome.jsp");
		} else {
			out.println("Invalid loginname and password");
			response.sendRedirect("error.jsp");
		}
	%> 
