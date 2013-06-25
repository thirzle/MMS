
<%@page import="management.SelfStudy"%>
<%@page import="management.EffortEntry"%>
<%@page import="management.Entry"%>
<%@page import="management.ModuleAdministration"%>
<%@page import="user.User"%>
<%@page import="java.util.LinkedList"%>
<%@ page import="java.util.List, java.util.ArrayList"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/moduleView.css">

<h1>Freizugebendes Modul</h1>

<form action="/SopraMMS/ShowApproveModuleForEditor" method="get">
	<%
		LinkedList<Entry> entryListForEditor = (LinkedList<Entry>) session.getAttribute("entryListForEditor");
		User user = (User) session.getAttribute("user");	
		String institute = (String) session.getAttribute("instituteApproveModule");
		String subject = (String) session.getAttribute("subjectApproveModule");
	%>
	<div>
		<table style="border: none;">
			<tr>
				<td style="width: 625px;"></td>
				<td><img src="/SopraMMS/images/Haken.jpg"
					style="margin-right: 4px;"> <img
					src="/SopraMMS/images/Kreuz.jpg"></td>
			</tr>
		</table>
	</div>
	<%
		for (int i = 0; i < entryListForEditor.size(); i++) {
			Entry entry = entryListForEditor.get(i);
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
				<%
				String trueChecked = "empty";
				String falseChecked = "empty";
				if(entry.isApproved()){
				%>
				<td><input type="radio" name="radioEntry<%=entry.getTitle()%>"
					checked="checked" value="true"></td>
				<td><input type="radio" name="radioEntry<%=entry.getTitle()%>"
					value="false"></td>
				<%
				}
				else if(entry.isRejected()){
				%>
				<td><input type="radio" name="radioEntry<%=entry.getTitle()%>"
					value="true"></td>
				<td><input type="radio" name="radioEntry<%=entry.getTitle()%>"
					checked="checked" value="false"></td>
				<%
				}
				else{
				%>
				<td><input type="radio" name="radioEntry<%=entry.getTitle()%>"
					value="true"></td>
				<td><input type="radio" name="radioEntry<%=entry.getTitle()%>"
					value="false"></td>
				<%
				}
				%>
			</tr>
		</table>
	</div>
	<%
		}
	%>
	<br>

	<div class='moduleEntry'>
		<table>
			<tr>
				<td class='descriptionModule'>Institut</td>
				<td class='entryModule' style="width: 500px;"><notEditable><%=institute%></notEditable></td>
			</tr>
		</table>
	</div>
	<div class='moduleEntry'>
		<table>
			<tr>
				<td class='descriptionModule'>Fach</td>
				<td class='entryModule' style="width: 500px;"><notEditable><%=subject%></notEditable></td>
			</tr>
		</table>
	</div>
	<br>
	<button type="submit" value="sendModule" name="approveModule">Einträge
		freigeben</button>

</form>
<script type="text/javascript"
	src="/SopraMMS/js/jquery.createnewmodule.js"></script>
<script>
	fieldsEmpty();
</script>