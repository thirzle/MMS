
<%
	String _loginname = (String) session.getAttribute("loginname");
	if (_loginname != null) {
		out.println(_loginname + " loged out");
		session.removeAttribute("username");
		session.removeAttribute("rights");
		session.removeAttribute("user");
		session.invalidate();
		response.sendRedirect("/SopraMMS/welcome.jsp");
	} else {
%><img src="${pageContext.request.contextPath}/images/Teambild.jpg"
	alt="Logo" align="left">
<%
	out.println("you are already not login");
	}
%>