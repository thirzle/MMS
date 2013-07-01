
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
%>
<meta http-equiv=refresh content="0; URL= /SopraMMS/guiElements/home.jsp?errortext=Sie haben sich bereits ausgeloggt.">
<%} %>
