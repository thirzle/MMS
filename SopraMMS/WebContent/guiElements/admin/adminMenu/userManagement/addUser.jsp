<% 
String loginname = request.getParameter("loginCellText");
String firstname = request.getParameter("firstnameCellText");
String lastname = request.getParameter("lastnameCellText");
String email = request.getParameter("emailCellText");
String rights = request.getParameter("rightsSelect");
String institute = request.getParameter("instituteSelect");
String supervisor = request.getParameter("supervisorSelect");

System.out.println(rights);
response.sendRedirect("/SopraMMS/guiElements/admin/adminHome.jsp");%>