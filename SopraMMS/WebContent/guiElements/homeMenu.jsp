
<%
	User user = (User) session.getAttribute("user");
%>

<%@ include file="/guiElements/frontend/courseSelection.jsp"%>


<%
	if (user != null) {
		boolean[] rights = user.getRights();
		if (rights[3]) {
%>
<%@ include file="/guiElements/admin/adminMenu.jsp"%>
<%
	}
		if (rights[1]) {
%>
<%@ include file="/guiElements/modulmanager/modulmanagerMenu.jsp"%>
<%
	}
		if (rights[2]) {
%>
<%@ include file="/guiElements/editor/editorMenu.jsp"%>
<%
	}
		if (rights[4]) {
%>
<%@ include file="/guiElements/dez2/dez2Menu.jsp"%>
<%
	}
%>
<%@ include file="/guiElements/generally/generallyMenu.jsp"%>
<%
	}
%>

<%
	if (user == null) {
%>
<%@ include file="/guiElements/Login/login.jsp"%>
<%
	} else {
%>
<%@ include file="/guiElements/Login/logout.jsp"%>
<%
	}
%>

