
<%@page import="management.SelfStudy"%>
<%@page import="management.EffortEntry"%>
<%@page import="management.Entry"%>
<%@page import="management.ModuleAdministration"%>
<%@page import="user.User"%>
<%@page import="java.util.LinkedList"%>
<%@ page import="java.util.List, java.util.ArrayList"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/moduleView.css">

<h1>Freizugebende Module</h1>

<form action="/SopraMMS/ShowEditModuleForEditor" method="get">
	<%
		LinkedList<Entry> entryListForEditor = (LinkedList<Entry>) session.getAttribute("entryListForEditor");
		User user = (User) session.getAttribute("user");	
		String institute = (String) session.getAttribute("instituteApproveModule");

		for (int i = 0; i < entryListForEditor.size(); i++) {
			Entry entry = entryListForEditor.get(i);
	%>
	<div class='moduleEntry'>
		<table>
			<tr>
				<td class='descriptionModule'><%=entry.getTitle()%></td>
				<%
					if(entry.getClass().equals(EffortEntry.class)){
						%>
				<td class='entryModule'>
					<%
						EffortEntry effortEntry = (EffortEntry) entry;
						for(SelfStudy selfstudy : effortEntry.getSelfStudyList()){
							out.println(selfstudy.getTitle()+" "+selfstudy.getTime()+" Stunden");
							%>
							<br>
							<%
						}
					%>
				</td>
				<%
					}
					else{
				%>
				<td class='entryModule'><%=entry.getContent().trim()%></td>
				<%
					}
				String trueChecked = "empty";
				String falseChecked = "empty";
				if(entry.isApproved()){
					trueChecked = "checked";
					falseChecked = "empty";
				}
				else if(entry.isRejected()){
					trueChecked = "empty";
					falseChecked = "checked";
				}
				else{
					trueChecked = "empty";
					falseChecked = "empty";
				}
				System.out.println(entry.getTitle()+" approved: "+entry.isApproved()+" trueChecked: "+trueChecked+" falseChecked: "+falseChecked);
				%>
				<td><input type="radio" name="radioEntry+<%=entry.getTitle()%>"
					checked=<%=trueChecked %> value="true"></td>
				<td><input type="radio"
					name="radioEntry+<%=entry.getTitle()%>" checked=<%=falseChecked %> value="false"></td>
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

	<button type="submit" value="addRow" name="approveModule">Zeile
		hinzuf&uuml;gen</button>
	<button type="submit" value="saveModule" name="approveModule">Modul
		f&uuml;r Sitzung speichern</button>

	<button type="submit" value="sendModule" name="approveModule">Einträge
		freigeben</button>

</form>
<script type="text/javascript"
	src="/SopraMMS/js/jquery.createnewmodule.js"></script>
<script>
	fieldsEmpty();
</script>