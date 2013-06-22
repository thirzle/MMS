
<%@page import="management.SelfStudy"%>
<%@page import="management.EffortEntry"%>
<%@page import="management.Entry"%>
<%@page import="java.util.LinkedList"%>
<%@page import="management.Module"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/moduleView.css">

<h1>Module betrachten</h1>

<%
Module module = (Module) session.getAttribute("moduleForViewModule");
session.removeAttribute("moduleForViewModule");
LinkedList<Entry> entryList = (LinkedList) module.getEntryList();
String institute = (String) session.getAttribute("instituteForViewModule");
session.removeAttribute("instituteForViewModule");


	for (int i = 0; i < entryList.size(); i++) {
		Entry entry = entryList.get(i);
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
								%> <br> <%
 							}
 						%>
						</td>
				<%
					}
					else{
						%>
						<td class='entryModule'>
						<%
						String list[]=entry.getContent().split("\n");
							for(String s:list){
								out.println(""+s+"<br>");
							}
						%>
						</td>
						<%
					}
								
								
								
				%>
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

<script type="text/javascript"
	src="/SopraMMS/js/jquery.createnewmodule.js"></script>
<script>
	fieldsEmpty();
</script>