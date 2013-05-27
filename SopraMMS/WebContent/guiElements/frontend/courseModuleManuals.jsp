
<%@page import="java.util.List"%>
<%@page import="management.ModuleAdministration"%>
<%
	String course = (String) session.getAttribute("coursepdfs");
	session.removeAttribute("coursepdfs");

	ModuleAdministration ma = new ModuleAdministration();
	List<String[]> pdfs = ma.getModuleManualPdfByCourse(course);

	
	
	
	// 0:Bachelor; 1:Master; 2:Lehramt; 3:Diplom;
	boolean[] degree = new boolean[4];
	for (String[] s : pdfs) {
		if (s[1].equals("Bachelor")) {
			degree[0] = true;
		}
		if (s[1].equals("Master")) {
			degree[1] = true;
		}
		if (s[1].equals("Lehramt")) {
			degree[2] = true;
		}
		if (s[1].equals("Diplom")) {
			degree[3] = true;
		}
	}
%>
<h1><%=course%></h1>

<p>W&auml;hlen Sie aus den unteren Eintr&auml;gen Ihr passendes
	Modulhandbuch aus.</p>

<div class=pdfModuleManualBox>

	<%
		if (degree[0]) {
	%>
	<h2>Bachelor</h2>
	<ul>
		<%
			for (String[] s : pdfs) {
					if (s[1].equals("Bachelor")) {
						out.println("<a href='/SopraMMS/pdf/" + s[3]
								+ "' target='_blank'>");
						out.println("<li>" + s[2] + " &rarr; " + s[0] + " - "
								+ s[1] + "</li>");
						out.println("</a>");
					}
				}
		%>
	</ul>
	<%
		}
	%>
	<%
		if (degree[1]) {
	%>
	<h2>Master</h2>
	<ul>
		<%
			for (String[] s : pdfs) {
					if (s[1].equals("Master")) {
						out.println("<a href='/SopraMMS/pdf/" + s[3]
								+ "' target='_blank'>");
						out.println("<li>" + s[2] + " &rarr; " + s[0] + " - "
								+ s[1] + "</li>");
						out.println("</a>");
					}
				}
		%>
	</ul>
	<%
		}
	%>
	<%
		if (degree[2]) {
	%>
	<h2>Lehramt</h2>
	<ul>
		<%
			for (String[] s : pdfs) {
					if (s[1].equals("Lehramt")) {
						out.println("<a href='/SopraMMS/pdf/" + s[3]
								+ "' target='_blank'>");
						out.println("<li>" + s[2] + " &rarr; " + s[0] + " - "
								+ s[1] + "</li>");
						out.println("</a>");
					}
				}
		%>
	</ul>
	<%
		}
	%>
	<%
		if (degree[3]) {
	%>
	<h2>Diplom</h2>
	<ul>
		<%
			for (String[] s : pdfs) {
					if (s[1].equals("Diplom")) {
						out.println("<a href='/SopraMMS/pdf/" + s[3]
								+ "' target='_blank'>");
						out.println("<li>" + s[2] + " &rarr; " + s[0] + " - "
								+ s[1] + "</li>");
						out.println("</a>");
					}
				}
		%>
	</ul>
	<%
		}
	%>



</div>
