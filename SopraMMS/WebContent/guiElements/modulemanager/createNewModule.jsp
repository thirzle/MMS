<%@ page import="java.util.List, java.util.ArrayList"%>
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

	ArrayList<String[]> fieldsTypeD = new ArrayList();
	fieldsTypeD.addAll((ArrayList<String[]>) session
			.getAttribute("fieldsTypeD"));
%>
<h1>Neues Modul erstellen</h1>

<form action="/SopraMMS/CreateModule" method="get">
	<%
		for (int i = 0; i < fieldsTypeA.size(); i++) {
			String[] description = fieldsTypeA.get(i);
	%>
	<div class='moduleEntry'>
		<table>
			<tr>
				<td class='descriptionModule'><%=description[0]%></td>
				<td class='entryModule'><input name='<%=i%>ContentA'
					type="text" value="<%=description[1].trim()%>"></td>
			</tr>
		</table>
	</div>
	<%
		}
	%>
	<div class='moduleEntry'>
		<table>
			<tr>
				<td class='descriptionModule'>Zeitaufwand</td>
				<td class='entryModule'><div class='effortEntry'>
						<table>
							<tr>
								<%
									String[] pt = fieldsTypeD.get(0);
								%>
								<td class="effortEntryTitel">Pr&auml;senzzeit</td>
								<td>:</td>
								<td class="effortEntryTime"><input name='<%=0%>ContentD'
									type="number" value="<%=pt[1].toString()%>">&ensp;Stunden</td>
							</tr>

							<%
								for (int i = 1; i < fieldsTypeD.size(); i++) {
									String[] description = fieldsTypeD.get(i);
							%>
							<tr>
								<td class="effortEntryTitel"><input name='<%=i%>TitleD'
									type="text" value="<%=description[0].trim()%>"></td>
								<td>:</td>
								<td class="effortEntryTime"><input name='<%=i%>ContentD'
									type="number" value="<%=description[1].trim()%>">&ensp;Stunden</td>
								<%
									}
								%>
							
						</table>
					</div></td>
			</tr>

		</table>
	</div>

	<div class='moduleEntry'>
		<table>
			<tr>
				<td class='descriptionModule'>Institut</td>
				<td class='entryModule'>
					<%
						List<String[]> institutes = (List<String[]>) session
								.getAttribute("institutesModuleEntry");
					%> <select name='selectedInstitute' id="instituteSelect">
						<%
							for (int i = 0; i < institutes.size(); i++) {
						%>
						<option value='<%=institutes.get(i)[0]%>'><%=institutes.get(i)[1]%></option>

						<%
							}
						%>
				</select>
				</td>
			</tr>
		</table>

	</div>

	<%
		for (int i = 0; i < fieldsTypeB.size(); i++) {
			String[] description = fieldsTypeB.get(i);
	%>
	<div class='moduleEntry'>
		<table>
			<tr>
				<td class='descriptionModule'><%=description[0]%></td>
				<td class='entryModule'><textarea name='<%=i%>ContentB'><%=description[1].trim()%></textarea></td>
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
				<td class='descriptionModule'><input name="<%=i%>TitleC"
					type="text" value="<%=description[0].trim()%>"></td>
				<td class='entryModule'><textarea name="<%=i%>ContentC"><%=description[1].trim()%></textarea></td>
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

	<button type="submit" value="addRow" name="createModule">Zeile
		hinzuf&uuml;gen</button>
	<button type="submit" value="saveModule" name="createModule">Modul
		f&uuml;r Sitzung speichern</button>
	<button type="submit" value="sendModule" name="createModule">Modul
		einreichen</button>

</form>


