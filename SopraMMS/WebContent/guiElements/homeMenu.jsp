<%@page import="user.User"%>
<%
	//Initialization
	User user = null;
	if (session.getAttribute("user") != null) {
		user = (User) session.getAttribute("user");
	}
%>

<jsp:include page="/guiElements/frontend/courseSelection.jsp"></jsp:include>

<%
	if (user != null) {
		boolean[] rights = user.getRights();
		if (rights[6]) {
%>
<jsp:include page="/guiElements/admin/adminMenu.jsp"></jsp:include>
<%
	}
		if (rights[5]) {
%>
<jsp:include page="/guiElements/editor/editorMenu.jsp"></jsp:include>
<%
	}
		if (rights[4]) {
%>
<jsp:include page="/guiElements/coordinator/coordinatorMenu.jsp"></jsp:include>
<%
	}
		if (rights[2]) {
%>
<jsp:include page="/guiElements/modulemanager/modulemanagerMenu.jsp"></jsp:include>
<%
	}
		if (rights[3]) {
%>
<jsp:include page="/guiElements/dez2/dez2Menu.jsp"></jsp:include>
<%
	}
		if (rights[1]) {
%>
<%@ include file="/guiElements/lecturer/lecturerMenu.jsp"%>
<%
	}
		if (rights[0]) {
%>

<jsp:include page="/guiElements/generally/generallyMenu.jsp"></jsp:include>
<%
	} else {
%>
<%@ include
	file="/guiElements/generally/generallyMenuWithoutRepresentative.jsp"%>
<%
	}
	}
%>
<%
	if (user == null) {
		if (session.getAttribute("content") == null &&  session.getAttribute("userCreatNewPassword")==null) {
			session.invalidate();
		} else if (!session.getAttribute("content").equals(
				"createNewPassword") && session.getAttribute("userCreatNewPassword")==null) {
			session.invalidate();
		}
%>
<jsp:include page="/guiElements/Login/login.jsp"></jsp:include>
<%
	} else {
%>
<jsp:include page="/guiElements/Login/logout.jsp"></jsp:include>
<%
	}
%>

