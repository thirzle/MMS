
<%
	String contentPage;
	Object user = session.getAttribute("user");
	System.out
			.println("-- content: " + session.getAttribute("content"));
	System.out.println("-- home: " + request.getParameter("home"));
	System.out.println("-- user: " + session.getAttribute("user"));
	System.out.println("+++++++++ Content " + session.getAttribute("userCreatNewPassword"));
	if ((session.getAttribute("content") == null || request.getParameter("home") != null) && session.getAttribute("user") == null&&session.getAttribute("userCreatNewPassword")==null) {
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
			|| contentPage.equals("ceateNewPwNotEqual")) {
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
	} else if (contentPage.equals("appointRepresentative")) {
%>
<jsp:include page="/guiElements/generally/appointRepresentative.jsp"></jsp:include>
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
	} else if (contentPage
			.equals("showApproveModulesOverviewForEditor")) {
%>
<jsp:include
	page="/guiElements/editor/showApproveModulesOverviewForEditor.jsp"></jsp:include>
<%
	} else if (contentPage.equals("editModule")) {
%>
<jsp:include page="/guiElements/modulemanager/editModule.jsp"></jsp:include>
<%
	} else if (contentPage.equals("currVitae")) {
%>
<jsp:include page="/guiElements/lecturer/curriculumvitae.jsp"></jsp:include>
<%
	} else if (contentPage.equals("showEditModulesOverviewForEditor")) {
%>
<jsp:include
	page="/guiElements/editor/showEditModulesOverviewForEditor.jsp"></jsp:include>
<%
	} else if (contentPage.equals("showApproveModuleForEditor")) {
%>
<jsp:include page="/guiElements/editor/showApproveModuleForEditor.jsp"></jsp:include>
<%
	} else if (contentPage.equals("createNewCourse")) {
%>
<jsp:include page="/guiElements/coordinator/createNewCourse.jsp"></jsp:include>
<%
	} else if (contentPage.equals("createNewSubject")) {
%>
<jsp:include page="/guiElements/coordinator/createNewSubject.jsp"></jsp:include>
<%
	} else if (contentPage.equals("showModulesForDez2")) {
%>
<jsp:include page="/guiElements/dez2/showModulesForDez2.jsp"></jsp:include>
<%
	} else if (contentPage.equals("viewModule")) {
%>
<jsp:include page="/guiElements/viewModule.jsp"></jsp:include>
<%
	} else if (contentPage.equals("disableModule")) {
%>
<jsp:include page="/guiElements/dez2/disableModule.jsp"></jsp:include>
<%
	} else if (contentPage.equals("showEditModulesForCoordinator")) {
%>
<jsp:include
	page="/guiElements/coordinator/showEditModulesForCoordinator.jsp"></jsp:include>
<%
	} else if (contentPage.equals("showUserData")) {
%>
<jsp:include page="/guiElements/generally/showUserData.jsp"></jsp:include>
<%
	} else if (contentPage.equals("deleteModule")) {
%>
<jsp:include page="/guiElements/admin/deleteModule.jsp"></jsp:include>
<%
	} else if (contentPage.equals("createNewInstitute")) {
%>
<jsp:include page="/guiElements/admin/createNewInstitute.jsp"></jsp:include>
<%
	}
%>

