
<%
	//Initialization
	User user = null;
	if (session.getAttribute("user") != null) {
		user = (User) session.getAttribute("user");
	}
%>

<%@ include file="/guiElements/frontend/courseSelection.jsp"%>

<%
	if (user != null) {
		boolean[] rights = user.getRights();
		if (rights[6]) {
%>
<%@ include file="/guiElements/admin/adminMenu.jsp"%>
<%
	}
		if(rights[5]){
%>
<%@include file="/guiElements/editor/editorMenu.jsp" %>
<%
		}
		if (rights[4]) {
%>
<%@ include file="/guiElements/coordinator/coordinatorMenu.jsp"%>
<%
	}
		if (rights[3]) {
%>
<%@ include file="/guiElements/dez2/dez2Menu.jsp"%>
<%
	}
		if (rights[2]) {
%>
<%@ include file="/guiElements/prof/profMenu.jsp"%>
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

