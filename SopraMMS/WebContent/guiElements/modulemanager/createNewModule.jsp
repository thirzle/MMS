<%@page import="java.util.ArrayList"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/moduleView.css">


<%
	ArrayList<String[]> fieldsTypeA = new ArrayList();
	fieldsTypeA.addAll((ArrayList<String[]>) session
			.getAttribute("fieldsTypeA"));

	ArrayList<String[]> fieldsTypeB = new ArrayList();
	fieldsTypeB.addAll((ArrayList<String[]>) session
			.getAttribute("fieldsTypeB"));

	ArrayList<String[]> fieldsTypeC = new ArrayList();
	fieldsTypeC.addAll((ArrayList<String[]>) session
			.getAttribute("fieldsTypeC"));
%>
<h1>Neues Modul erstellen</h1>

<form action="/SopraMMS/CreateModule?newModule=newRow" method="get"
	onsubmit='return checkForm();'>
	<%
		for (String[] description : fieldsTypeA) {
	%>
	<div class='moduleEntry'>
		<table>
			<tr>
				<td class='descriptionModule'><%=description[0]%></td>
				<td class='entryModule'><input
					name='<%=description[0]%>Content' type="text"
					value="<%=description[1].trim()%>"></td>
			</tr>
		</table>
	</div>
	<%
		}
		for (String[] description : fieldsTypeB) {
	%>
	<div class='moduleEntry'>
		<table>
			<tr>
				<td class='descriptionModule'><%=description[0]%></td>
				<td class='entryModule'><textarea
						name='<%=description[0]%>Content'><%=description[1].trim()%></textarea></td>
			</tr>
		</table>
	</div>
	<%
		}
		for (int i = 0; i < fieldsTypeC.size(); i++) {
			String[] description = fieldsTypeC.get(i);
	%>
	<div class='moduleEntry'>
		<table>
			<tr>
				<td class='descriptionModule'><input name="<%=i%>Title"
					type="text" value="<%=description[0].trim()%>"></td>
				<td class='entryModule'><textarea name="<%=i%>Content"><%=description[1].trim()%></textarea></td>
				<td class='buttonDeleteRow'>
					<button type="submit" value="<%=i%>Delete" name="deleteRow">
						<img src="/SopraMMS/images/deleteModuleEntry.png" />
					</button>
				</td>
			</tr>
		</table>
	</div>
	<%
			}
%>

	<input type="submit" value="Zeile hinzuf&uuml;gen" name="addRow">
	<input type="submit" value="Modul einreichen" name="saveModule">
</form>
