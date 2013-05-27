<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/default.css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>

<%@ include file="/guiElements/fillContent.jsp"%>
<%@page import="user.User"%>
</head>



<body>
	<%
		User user = (User) session.getAttribute("user");
		if (user != null) {
	%>
	<div class="page">
		<div class="header">
			<%@ include file="/guiElements/header.jsp"%>
		</div>
		
		<div class="main">
			<div class="menu">
				<%
					boolean[] rights = user.getRights();
						if (rights[3]) {
				%>
				<%@ include file="/guiElements/admin/adminMenu/adminMenu.jsp"%>
				<%
					}
						if (rights[1]) {
				%>
				<%@ include
					file="/guiElements/modulmanager/modulmanagerMenu.jsp"%>
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
				<%@ include file="/guiElements/Login/logout.jsp" %>
			</div>
			<div class="contentBox" id="contentBox">
				<%if(session.getAttribute("task")=="usertable") { %>
					<%@ include file="/guiElements/admin/adminMenu/userManagementContent.jsp" %>
				<%} else if(session.getAttribute("task")=="home") { %>
					<%@ include file="/guiElements/homeContent.jsp"%>
				<%} %>
			</div>
		</div>
		<div class="footer" id="footer">
			<%@ include file="/guiElements/footer.jsp"%>
		</div>
		<%
			} else {
				response.sendRedirect("/SopraMMS/guiElements/error.jsp");
			}
		%>
	</div>
</body>
<script type="text/javascript">
$(document).ready(function() {
	
});
</script>
</html>