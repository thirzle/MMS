	<%
		String loginname=(String)session.getAttribute("loginname");
		if(loginname!=null) {
			out.println(loginname+" loged out");
			session.removeAttribute("username");
			session.removeAttribute("password");
			session.invalidate();  
			response.sendRedirect("/SopraMMS/welcome.jsp");
		} else {
			%><img src="${pageContext.request.contextPath}/images/Teambild.jpg" alt="Logo" align="left"><%
			out.println("you are already not login");
		}
	%>
	

