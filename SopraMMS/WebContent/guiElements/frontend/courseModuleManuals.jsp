
<%@page import="management.ModuleAdministration"%>
<%
	String course = (String) session.getAttribute("coursepdfs");
	session.removeAttribute("coursepdfs");


	ModuleAdministration ma = new ModuleAdministration();
	ma.getModuleManualPdfByCourse(course)
%>

<h1><%=course%></h1>

<p>
W&auml;hlen Sie aus den unteren Eintr&auml;gen ihr passendes Modulhandbuch aus. 
</p>

<div class=pdfModuleManualBox>
	<h2>Master</h2>
	
		
		<ul>
			<li>SoSe 2012</li>
			<li>WiSe 2012/2011</li>
			<li>SoSe 2011</li>
			<li>WiSe 2011/2010</li>
		</ul>
	
</div>