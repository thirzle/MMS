<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@page import="user.UserAdministration"%>

<%
	String infoText = request.getParameter("infotext");
	if (infoText != null) {
%>
<div class="infoBox"><%=infoText%></div>
<%
	}
%>

<%
	String infoError = request.getParameter("errortext");
	if (infoError != null) {
%>
<div class="errorBox"><%=infoError%></div>
<%
	}
%>

<%
	Date deadline = (java.sql.Date) session.getAttribute("deadline");
	Date beginremember = (java.sql.Date) session
			.getAttribute("beginremember");
	if (deadline != null && beginremember != null) {
		//new SimpleDateFormat("dd.MM.yyyy").format();
		Date today = new Date();
		if (today.after(deadline)) {
%>
<h1>Keine &Auml;nderungen am Modulhandbuch mehr m&ouml;glich</h1>
<p>
	Seit dem
	<%=new SimpleDateFormat("dd.MM.yyyy")
							.format(deadline)%>
	sind keine &Auml;nderungen an Modulen f&uuml;r das kommende Semester
	mehr m&ouml;glich.
</p>
<%
	} else if (beginremember.before(today)) {
%>
<h1>Abgabe neuer und ge&auml;nderter Module</h1>
<p>
	Bis zum
	<%=new SimpleDateFormat("dd.MM.yyyy")
							.format(deadline)%>
	m&uuml;ssen &Auml;nderungen oder neue Module im MMS eingereicht werden,
	im kommenden Semester wirksam zu sein.
</p>
<p>Falls Sie diesen Termin nicht einhalten können, treten Sie bitte
	mit dem Administrator in Kontakt.</p>
<%
	}
	}
%>
<%
	UserAdministration ua = new UserAdministration();
	List<String[]> list = ua.getNewsByType(2);
	for (String[] entry : list) {
		out.println("<h1>" + entry[0] + "</h1>");
		String[] text =entry[1].split("\n");
		out.println("<div class='contentTextBox'><p>");
		for(String s:text){
			out.println(s + "<br>");
		}
		out.println("</p></div>");
		out.println("<div class='contentBoxDate'> Erstellt am "
				+ entry[2] + "</div>");
	}
%>