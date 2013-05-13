	<%
		String loginname=(String)session.getAttribute("loginname");
		if(loginname!=null) {
			
			out.println(loginname+" loged out");
			session.removeAttribute("username");
			session.removeAttribute("session");
			session.invalidate();  
		} else {
			out.println("You are already not login");
		}
	%>
<meta http-equiv=refresh content="0; URL=/SopraMMS/welcome.jsp">

