<%@page import="management.Module"%>
<%@page import="management.Entry"%>
<%@page import="java.util.LinkedList"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/moduleView.css">
<%
	LinkedList<Entry> entryList = new LinkedList<Entry>();
	Module module = (Module) session.getAttribute("module");
	Object list = session.getAttribute("showEntryListOfModule");
	if (list != null) {
		entryList.addAll((LinkedList<Entry>) list);
	}
%>
<h1>Modul bearbeiten</h1>
<table class="moduleInformation">
	<tr>
		<td>Titel</td>
	</tr>

</table>



