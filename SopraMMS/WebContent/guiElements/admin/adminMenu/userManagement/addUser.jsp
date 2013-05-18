<%@ page import="user.User" %>
<% 
String loginname = request.getParameter("loginCellText");
String firstname = request.getParameter("firstnameCellText");
String lastname = request.getParameter("lastnameCellText");
String email = request.getParameter("emailCellText");
String rights = request.getParameter("rightsSelect");
String institute = request.getParameter("instituteSelect");
String representative = request.getParameter("representativeSelect");
User user = new User(loginname,firstname,lastname,email,representative);
session.setAttribute("task", "reloadTable");
System.out.println(user.toString());
response.sendRedirect("/SopraMMS/guiElements/home.jsp");%>