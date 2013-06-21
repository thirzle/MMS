
<%
	String contentPage;
	Object user = session.getAttribute("user");
	System.out
			.println("-- content: " + session.getAttribute("content"));
	System.out.println("-- home: " + request.getParameter("home"));
	System.out.println("-- user: " + session.getAttribute("user"));
	if ((session.getAttribute("content") == null || request
			.getParameter("home") != null)
			&& session.getAttribute("user") == null) {

		contentPage = "start";
		if (request.getParameter("contentPdf") != null) {
			contentPage = "contentPdf";
		}
	} else if ((session.getAttribute("content") == null || request
			.getParameter("home") != null)
			&& session.getAttribute("user") != null) {
		contentPage = "home";
		if (request.getParameter("contentPdf") != null) {
			contentPage = "contentPdf";
		}
	} else {
		contentPage = session.getAttribute("content").toString();
	}

	System.out.println("(content.jsp): Seiteninhalt: " + contentPage);
	if (contentPage.equals("start")) {
%>
<jsp:include page="/guiElements/frontend/frontendContent.jsp"></jsp:include>
<%
	} else if (contentPage.equals("createNewPassword")
			|| contentPage.equals("ceateNewPwNotEqual")
			|| contentPage.equals("ceateNewPwDone")) {
%>
<jsp:include page="/guiElements/Login/createNewPassword.jsp"></jsp:include>
<%
	} else if (contentPage.equals("contentPdf")) {
%>
<jsp:include page="/guiElements/frontend/courseModuleManuals.jsp"></jsp:include>
<%
	} else if (contentPage.equals("changedPw")) {
%>
<jsp:include page="/guiElements/generally/changePassword.jsp"></jsp:include>
<%
	} else if (contentPage.equals("applyRepresentative")) {
%>
<jsp:include page="/guiElements/generally/applyRepresentative.jsp"></jsp:include>
<%
	} else if (contentPage.equals("appointRepresentative")) {
%>
<jsp:include page="/guiElements/generally/appointRepresentative.jsp"></jsp:include>
<%
	} else if (contentPage.equals("removeRepresentative")) {
%>
<jsp:include page="/guiElements/generally/removeRepresentative.jsp"></jsp:include>
<%
	} else if (contentPage.equals("showRepresentative")) {
%>
<jsp:include page="/guiElements/generally/showRepresentative.jsp"></jsp:include>
<%
	} else if (contentPage.equals("noRepresentative")) {
%>
<jsp:include page="/guiElements/generally/showNoRepresentative.jsp"></jsp:include>
<%
	} else if (contentPage.equals("createdRepresentative")) {
%>
<jsp:include page="/guiElements/generally/createdRepresentative.jsp"></jsp:include>
<%
	} else if (contentPage.equals("loadTable")) {
%>
<jsp:include page="/guiElements/admin/userManagementContent.jsp"></jsp:include>
<%
	} else if (contentPage.equals("home")) {
%>
<jsp:include page="/guiElements/backendContent.jsp"></jsp:include>
<%
	} else if (contentPage.equals("newUser")) {
%>
<jsp:include page="/guiElements/admin/newUser.jsp"></jsp:include>
<%
	} else if (contentPage.equals("generatePDF")) {
%>
<jsp:include page="/guiElements/admin/generatePDF.jsp"></jsp:include>
<%
	} else if (contentPage.equals("newDeadline")) {
%>
<jsp:include page="/guiElements/admin/newDeadline.jsp"></jsp:include>
<%
	} else if (contentPage.equals("showDeadline")) {
%>
<jsp:include page="/guiElements/admin/showDeadline.jsp"></jsp:include>
<%
	} else if (contentPage.equals("editUser")) {
%>
<jsp:include page="/guiElements/admin/editUser.jsp"></jsp:include>
<%
	} else if (contentPage.equals("createNews")) {
%>
<jsp:include page="/guiElements/admin/createNews.jsp"></jsp:include>
<%
	} else if (contentPage.equals("showNews")) {
%>
<jsp:include page="/guiElements/admin/showNews.jsp"></jsp:include>
<%
	} else if (contentPage.equals("newMessage")) {
%>
<jsp:include page="/guiElements/generally/newMessage.jsp"></jsp:include>
<%
	} else if (contentPage.equals("createNewModule")) {
%>
<jsp:include page="/guiElements/modulemanager/createNewModule.jsp"></jsp:include>
<%
	} else if (contentPage.equals("history")) {
%>
<jsp:include page="/guiElements/admin/showHistory.jsp"></jsp:include>
<%
	} else if (contentPage.equals("enterCourseToModule")) {
%>
<jsp:include page="/guiElements/coordinator/enterCourseToModule.jsp"></jsp:include>
<%
	} else if (contentPage.equals("changedName")) {
%>
<jsp:include page="/guiElements/generally/changedName.jsp"></jsp:include>
<%
	} else if (contentPage.equals("showModulesForModulemanager")) {
%>
<jsp:include
	page="/guiElements/modulemanager/showModulesForModulemanager.jsp"></jsp:include>
<%
	} else if (contentPage.equals("showModulesForCoordinator")) {
%>
<jsp:include
	page="/guiElements/coordinator/showModulesForCoordinator.jsp"></jsp:include>
<%
	} else if (contentPage.equals("showModulesForEditor")) {
%>
<jsp:include page="/guiElements/editor/showModulesForEditor.jsp"></jsp:include>
<%
	} else if (contentPage.equals("didEditModule")) {
%>
<jsp:include page="/guiElements/modulemanager/didEditModule.jsp"></jsp:include>
<%
	} else if (contentPage.equals("editModule")) {
%>
<jsp:include page="/guiElements/modulemanager/editModule.jsp"></jsp:include>
<%
	} else if (contentPage.equals("CurrVitae")) {
%>
<jsp:include page="/guiElements/lecturer/seecurriculumvitae.jsp"></jsp:include>
<%
	}  else if (contentPage.equals("showModulesForEditor")) {
%>
<jsp:include page="/guiElements/editor/showModulesForEditor.jsp"></jsp:include>
<%
	} else if (contentPage.equals("showEditModuleForEditor")) {
%>
<jsp:include page="/guiElements/editor/showEditModuleForEditor.jsp"></jsp:include>
<%
	} else if (contentPage.equals("createNewCourse")) {
%>
<jsp:include page="/guiElements/coordinator/createNewCourse.jsp"></jsp:include>
<%
	} else if (contentPage.equals("createNewSubject")) {
%>
<jsp:include page="/guiElements/coordinator/createNewSubject.jsp"></jsp:include>
<%
}
%>
