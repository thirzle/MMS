
<%@page import="management.SelfStudy"%>
<%@page import="management.EffortEntry"%>
<%@page import="management.Entry"%>
<%@page import="java.util.LinkedList"%>
<%@page import="management.Module"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/moduleView.css">

<h1>Modul betrachten</h1>

<%
	Module module = (Module) session.getAttribute("moduleForViewModule");
LinkedList<Entry> entryList = (LinkedList) module.getEntryList();
String institute = (String) session.getAttribute("instituteForViewModule");
%><table class="informationAboutModule">
	<tr>
		<td>&Auml;nderungsdatum: <%=module.getModificationDate()%></td>
		<td>Erstellungsdatum: <%=module.getCreationDate()%></td>
		<td>Autor: <%=module.getModificationauthor()%>
	</tr>
</table>
<%
	for (int i = 0; i < 6&&i<entryList.size(); i++) {
		Entry entry = entryList.get(i);
%>

<div class='moduleEntry'>
	<table>
		<tr>
			<td class='descriptionModule'><%=entry.getTitle()%></td>

			<td class='entryModule'><notEditable> <%
 	String list[]=entry.getContent().split("\n");
     									for(String s:list){
     										out.println(""+s+"<br>");
     									}
 %> </notEditable></td>

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

			<td class='entryModule'><notEditable><%=institute%></notEditable></td>

		</tr>
	</table>
</div>
<div class='moduleEntry'>
	<table>
		<tr>
			<td class='descriptionModule'>Fach</td>

			<td class='entryModule'><notEditable><%=module.getSubject()%></notEditable></td>

		</tr>
	</table>
</div>

<%
	for (int i = 6; i < entryList.size(); i++) {
	Entry entry = entryList.get(i);
%>
<div class='moduleEntry'>
	<table>
		<tr>
			<td class='descriptionModule'><%=entry.getTitle()%></td>

			<td class='entryModule'><notEditable> <%
 	String list[]=entry.getContent().split("\n");
    							for(String s:list){
    								out.println(""+s+"<br>");
    							}
 %> </notEditable></td>

		</tr>
	</table>
</div>
<%
	}
%>


<script type="text/javascript"
	src="/SopraMMS/js/jquery.createnewmodule.js"></script>
<script>
	fieldsEmpty();
</script>