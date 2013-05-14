	<%@page import="user.UserAdministration"%>
<%
		String loginname=(String)session.getAttribute("loginname");
		if(loginname!=null) {
			UserAdministration uadmin = new UserAdministration();
			
			uadmin.logout(session.getAttribute("loginname").toString());
			out.println(loginname+" loged out");
			session.removeAttribute("loginname");
			session.removeAttribute("session");
			session.invalidate();  
		} else {
			out.println("You are already not login");
		}
	%>
<meta http-equiv=refresh content="0; URL=/SopraMMS/welcome.jsp">

