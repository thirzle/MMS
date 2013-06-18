
<%@page import="management.ModuleAdministration"%>
<%@page import="user.User"%>
<%@page import="java.util.LinkedList"%>
<%@ page import="java.util.List, java.util.ArrayList"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/moduleView.css">

<h1>Freizugebende Module</h1>

<form action="/SopraMMS/ShowEditModuleForEditor" method="get">
	<%
		ArrayList<String[]> fieldsTypeA = (ArrayList) session.getAttribute("fieldsTypeAApprove");

			ArrayList<String[]> fieldsTypeB = (ArrayList) session.getAttribute("fieldsTypeBApprove");

			ArrayList<String[]> fieldsTypeC = (ArrayList) session.getAttribute("fieldsTypeCApprove");
			
			System.out.println("fieldsTypeC is empty: "+fieldsTypeC.isEmpty());

			ArrayList<String[]> fieldsTypeD = (ArrayList) session.getAttribute("fieldsTypeDApprove");
			
			String institute = (String) session.getAttribute("instituteListForApproveModule");
		
			User user = (User) session.getAttribute("user");
			LinkedList<String> institutes = (LinkedList<String>) user.getInstitute();

			for (int i = 0; i < fieldsTypeA.size(); i++) {
		String[] description = fieldsTypeA.get(i);
	%>
	<div class='moduleEntry'>
		<table>
			<tr>
				<td class='descriptionModule'><%=description[0]%></td>
				<td class='entryModule'><%=description[1].trim()%></td>
				<td class='radioModule'><input type="radio"></td>
				<button type="submit" value="approveEntry"+<%=description[0] %> name="approveModule">Freigeben</button>
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
										System.out.println("pt: "+pt[0] + " " + pt[1]);
									}
									else{
									    pt[0] = "empty";
									    pt[1] = "empty";
									}
								%>
								<td class="effortEntryTitel">Präsenzzeit</td>
								<td>:</td>
								<td class="effortEntryTime"><%=pt[1].toString()%>&ensp;Stunden</td>
							</tr>

							<%
								System.out.println("nach <tr>");
								for (int i = 1; i < fieldsTypeD.size(); i++) {
									String[] description = fieldsTypeD.get(i);
							%>
							<tr>
								<td class="effortEntryTitel"><%=description[0].trim()%></td>
								<td>:</td>
								<td class="effortEntryTime"><%=description[1].trim()%>&ensp;Stunden</td>
								<%
									}
								%>
							
						</table>
					</div></td>
				<td class="radioModule"><input type="radio"></td>
				<button type="submit" value="approveEffortEntry" name="approveModule">Freigeben</button>
			</tr>

		</table>
	</div>

	<div class='moduleEntry'>
		<table>
			<tr>
				<td class='descriptionModule'>Institut</td>
				<td class='entryModule'><%=institute %></td>
				<td class="radioModule"><input type="radio"></td>
				<button type="submit" value="approveInstitute" name="approveModule">Freigeben</button>
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
				<td class='entryModule'><%=description[1].trim()%></td>
				<td class="radioModule"><input type="radio"></td>
				<button type="submit" value="approveEntry"+<%=description[0] %> name="approveModule">Freigeben</button>
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

	<button type="submit" value="addRow" name="approveModule">Zeile
		hinzuf&uuml;gen</button>
	<button type="submit" value="saveModule" name="approveModule">Modul
		f&uuml;r Sitzung speichern</button>
	<button type="submit" value="sendModule" name="approveModule" disabled>Gesamtes Modul freigeben</button>

</form>
<script type="text/javascript"
	src="/SopraMMS/js/jquery.createnewmodule.js"></script>
<script>
	fieldsEmpty();
</script>
