<%@page import="org.bouncycastle.asn1.ocsp.ResponderID"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MMS</title>
<link rel="stylesheet" href="/SopraMMS/css/style.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" type="text/css" href="/SopraMMS/css/default.css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="/SopraMMS/js/menuManager.js"></script>
</head>
<%System.out.println("(home.jsp): "+request.getSession().getAttribute("content")); %>
<body>
	<div class="page">
		<div class="header">
			<%@ include file="/guiElements/header.jsp"%>
		</div>

		<div class="main">
			<div class="menu">
				<jsp:include page="/guiElements/homeMenu.jsp"></jsp:include>
			</div>
			<div class="contentBox" id="contentBox">
				<jsp:include page="/guiElements/content.jsp"></jsp:include>
			</div>
		</div>
		<div class="footer" id="footer">
			<%@ include file="/guiElements/footer.jsp"%>
		</div>
	</div>
</body>
</html>
