
<%
	String contentPage;
	Object user = session.getAttribute("user");
	if (session.getAttribute("content") == null
			|| request.getParameter("home") != null) {
		contentPage = "start";
		if (request.getParameter("contentPdf") != null) {
			contentPage = "contentPdf";
		}
	} else {
		contentPage = session.getAttribute("content").toString();
	}

	System.out.println("(content.jsp): Seiteninhalt: " + contentPage);
	System.out.println("(content.jsp): Task: "+ session.getAttribute("task"));
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
	}
%>
