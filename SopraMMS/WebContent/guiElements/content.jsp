<% 
String contentPage = (String) session.getAttribute("content");
if(contentPage.equals("changedPwStatusPw12Wrong")||contentPage.equals("changedPwStatusOldPwWrong")||contentPage.equals("changedPwStatusdone")){
%>
	<jsp:include page="/guiElements/generally/changePassword.jsp"></jsp:include>
<%
} else if(contentPage.equals("applyRepresentative")){
%>
	<jsp:include page="/guiElements/generally/applyRepresentative.jsp"></jsp:include>
<%
} else if(contentPage.equals("appointRepresentative")){
%>
	<jsp:include page="/guiElements/generally/appointRepresentative.jsp"></jsp:include>
<%
} else if(contentPage.equals("removeRepresentative")){
%>
	<jsp:include page="/guiElements/generally/removeRepresentative.jsp"></jsp:include>
<%
} else if(contentPage.equals("showRepresentative")){
%>
	<jsp:include page="/guiElements/generally/showRepresentative.jsp"></jsp:include>
<%
} else if(contentPage.equals("noRepresentative")){
%>
	<jsp:include page="/guiElements/generally/showNoRepresentative.jsp"></jsp:include>
<%
} else if(contentPage.equals("createdRepresentative")){
%>
	<jsp:include page="/guiElements/generally/createdRepresentative.jsp"></jsp:include>
<%
} else if(contentPage.equals("user")) {
%>
	<jsp:include page="/guiElements/admin/adminMenu/userManagementContent.jsp"></jsp:include>
<%
} else if(contentPage.equals("home")) {
%>
	<jsp:include page="/guiElements/homeContent.jsp"></jsp:include>
<%
} else if(contentPage.equals("addUser")) {
%>
	<jsp:include page="/guiElements/admin/adminMenu/userManagementContent.jsp"></jsp:include>
<%
}
%>
