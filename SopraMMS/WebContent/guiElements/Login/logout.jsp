	<%
		String loginname=(String)session.getAttribute("loginname");
	System.out.println("Loginname (Logoutfile): "+loginname);
		if(loginname!=null) {
			out.println(loginname+" loged out");
			session.removeAttribute("username");
			session.removeAttribute("password");
			session.invalidate();  
		} else {
			out.println("You are already not login");
		}
	%>


