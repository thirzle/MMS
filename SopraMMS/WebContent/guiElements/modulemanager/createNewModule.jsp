<%@page import="java.util.ArrayList"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/moduleView.css">


<%
	ArrayList<String> requiredFieldsTypeA = new ArrayList();
	requiredFieldsTypeA.addAll((ArrayList<String>) session
			.getAttribute("fieldsTypeA"));
	
	ArrayList<String> requiredFieldsTypeB = new ArrayList();
	requiredFieldsTypeB.addAll((ArrayList<String>) session
			.getAttribute("fieldsTypeB"));
	
%>
<h1>Neues Modul erstellen</h1>


<%
	for (String description : requiredFieldsTypeA) {
%>
<div class='moduleEntry'>
	<table>
		<tr>
			<td class='descriptionModule'><%=description%></td>
			<td class='entryModule'><textarea name='<%=description%>Content'
					rows="1" cols="60" style="resize: none;"></textarea></td>
		</tr>
	</table>
</div>
<%
	}
	for (String description : requiredFieldsTypeB) {
%>
<div class='moduleEntry'>
	<table>
		<tr>
			<td class='descriptionModule'><%=description%></td>
			<td class='entryModule'><textarea name='<%=description%>Content'
					rows="5" cols="60" style="resize: none;"></textarea></td>
		</tr>
	</table>
</div>
<%
	}
%>


