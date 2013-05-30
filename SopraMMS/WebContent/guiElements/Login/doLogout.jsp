
<%@page import="user.UserAdministration"%>
<%
	String _loginname = (String) session.getAttribute("loginname");
	if (_loginname != null) {
		UserAdministration ua = new UserAdministration();
		ua.logout(_loginname);
		out.println(_loginname + " loged out");
		session.removeAttribute("user");
		session.removeAttribute("loginname");
		session.removeAttribute("rights");
		session.removeAttribute("email");
		session.removeAttribute("task");
		session.removeAttribute("representative");
		session.invalidate();
		response.sendRedirect("/SopraMMS/guiElements/home.jsp");
	} else {
%><img src="${pageContext.request.contextPath}/images/Teambild.jpg"
	alt="Logo" align="left">
<%
	out.println("you are already not login");
	}
%>