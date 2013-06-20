<%@page import="management.ModuleAdministration"%>
<%@page import="user.User"%>
<%@page import="java.util.LinkedList"%>
<%@ page import="java.util.List, java.util.ArrayList"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/moduleView.css">



<h1>Modul bearbeiten</h1>

<form action="/SopraMMS/EditModule" method="get">
	<%
		ArrayList<String[]> fieldsTypeA = (ArrayList) session.getAttribute("fieldsTypeAEdit");

		ArrayList<String[]> fieldsTypeB = (ArrayList) session.getAttribute("fieldsTypeBEdit");

		ArrayList<String[]> fieldsTypeC = (ArrayList) session.getAttribute("fieldsTypeCEdit");

		ArrayList<String[]> fieldsTypeD = (ArrayList) session.getAttribute("fieldsTypeDEdit");
		
		ArrayList<String[]> fieldsTypeE = (ArrayList) session.getAttribute("fieldsTypeEEdit");
		
		User user = (User) session.getAttribute("user");
		LinkedList<String> institutes = (LinkedList<String>) user.getInstitute();

		for (int i = 0; i < fieldsTypeA.size(); i++) {
			String[] description = fieldsTypeA.get(i);
	%>
	<div class='moduleEntry'>
		<table>
			<tr>
				<td class='descriptionModule'><%=description[0]%></td>
				<td class='entryModule'><input class="mustNotBeEmpty"
					name='<%=i%>ContentA' type="text"
					value="<%=description[1].trim()%>"></td>
			</tr>
		</table>
	</div>
	<%
		}
	%>

	<%
		for (int i =0;i<fieldsTypeE.size();i++){ 
		String[] description = fieldsTypeE.get(i);
	%>

	<div class='moduleEntry'>
		<table>
			<tr>
				<td class='descriptionModule'><%=description[0]%></td>
				<td class='entryModule'><notEditable> <%
 	String list[]=description[1].split("\n");
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
				<td class='descriptionModule'>Zeitaufwand</td>
				<td class='entryModule'><div class='effortEntry'>
						<table>
							<tr>
								<%
									String[] pt = new String[2];
								if(fieldsTypeD.size() > 0){
								pt = fieldsTypeD.get(0);
								}else
								{
								pt[0] = "empty";
								pt[1] = "empty";
								}
								%>
								<td class="effortEntryTitel">Präsenzzeit</td>
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
				<td class='entryModule'><select name='selectedInstitute'
					id="instituteSelect">
						<%
							ModuleAdministration mAdmin = new ModuleAdministration();	
																																																						for (int i = 0; i < institutes.size(); i++) {
						%>
						<option value="<%=institutes.get(i)%>"><%=mAdmin.getInstituteName(institutes.get(i))%></option>

						<%
							}
						%>
				</select></td>
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
				<td class='entryModule'><textarea class="mustNotBeEmpty"
						name='<%=i%>ContentB'><%=description[1].trim()%></textarea></td>
			</tr>
		</table>
	</div>
	<%
		}
			for (int i = 0; i < fieldsTypeC.size(); i++) {
		String[] description = new String[2];
		description = fieldsTypeC.get(i);
		System.out.println("description");
		System.out.println(description[0]+" "+description[1]);
	%>
	<div class='moduleEntry'>
		<table>
			<tr>
				<td class='descriptionModule'><input name="<%=i%>TitleC"
					type="text" value="<%=description[0].trim()%>"></td>
				<td class='entryModule'><textarea class="mustNotBeEmpty"
						name="<%=i%>ContentC"><%=description[1].trim()%></textarea></td>
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

	<button type="submit" value="addRow" name="editModule">Zeile
		hinzuf&uuml;gen</button>
	<button type="submit" value="saveModule" name="editModule">Modul
		f&uuml;r Sitzung speichern</button>
	<button type="submit" value="sendModule" name="editModule"
		id="editModuleButton" disabled>Bearbeitetes Modul einreichen</button>

</form>
<script type="text/javascript"
	src="/SopraMMS/js/jquery.editnewmodule.js"></script>
<script>
	enableSubmitButton();
</script>
