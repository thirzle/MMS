
<%@page import="management.SelfStudy"%>
<%@page import="management.EffortEntry"%>
<%@page import="management.Entry"%>
<%@page import="java.util.LinkedList"%>
<%@page import="management.Module"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/moduleView.css">

<h1>Modul zurücksetzen</h1>

<form action="/SopraMMS/DisableModule" method="get">
<%
Module module = (Module) session.getAttribute("moduleForViewModule");

LinkedList<Entry> entryList = (LinkedList) module.getEntryList();
String institute = (String) session.getAttribute("instituteForViewModule");



	for (int i = 0; i < entryList.size(); i++) {
		Entry entry = entryList.get(i);
%>
<div class='moduleEntry'>
	<table>
		<tr>
			<td class='descriptionModule'><%=entry.getTitle()%></td>

			<td class='entryModule'>
				<%
					String list[]=entry.getContent().split("\n");
							for(String s:list){
								out.println(""+s+"<br>");
							}
				%>
			</td>
		
		</tr>
	</table>
</div>
<%
	}
%>

<div class='moduleEntry'>
	<table>
		<tr>
			<td class='descriptionModule'>Institut</td>
			<td class='entryModule'><%=institute%></td>
		</tr>
	</table>

</div>
<button name="action" value="disable">Modul zurücksetzen</button>
<button name="action" value="ok">Modul akzeptieren</button>
</form>
