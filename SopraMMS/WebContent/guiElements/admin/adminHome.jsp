<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home</title>
<link rel="stylesheet" type="text/css" href="/SopraMMS/css/default.css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js">
</script>
<script>
$(document).ready(function(){
	$("#start").clicked(function() {
		$(document).load("adminHome.jsp");
	});
});
</script>
</head>

<body>

<div class="page">
		<div class="header">
			<%@ include file="/guiElements/header.jsp" %>
		</div>
		<div class="main">
			<div class="menu">
				<%@ include file="/guiElements/courseSelection.jsp" %>
				<%@ include file="/guiElements/admin/adminMenu/adminMenu.jsp" %>
			</div>
			<div class="contentBox" id="contentBox">
				<%@ include file="/guiElements/admin/adminMenu/startContent.jsp" %>
			</div>
		</div>
		<div class="footer" id="footer">
			<%@ include file="/guiElements/footer.jsp" %>
		</div>
</div>
</body>
</html>