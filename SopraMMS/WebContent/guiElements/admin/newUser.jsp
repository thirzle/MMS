
<%@ page import="java.util.List, java.util.ArrayList" %>
<%
List<String> institutes = (List<String>) session.getAttribute("institutes");
int NUMBER_OF_INSTITUTES = institutes.size();
%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/multiple-select.css">
<h1>Neuen Benutzer anlegen</h1>
<div>
	<table>
		<tr>
			<td>Benutzername:</td>
			<td><input form="newUserForm" type='text' id="loginCellText" style="cursor: default; border: none;" name="loginCellText" readonly/></td>
			<td id="loginnameTableCell"></td>
		</tr>
		<tr>
			<td>Vorname:</td>
			<td><input oninput="generateLoginname()" class="inputField" form="newUserForm" type='text' id="firstnameCellText" name="firstnameCellText"/></td>
		</tr>
		<tr>
			<td>Nachname:</td>
			<td><input oninput="generateLoginname()" class="inputField" form="newUserForm" type='text' id="lastnameCellText" name="lastnameCellText"/></td>
		</tr>
		<tr>
			<td>Email:</td>
			<td><input oninput="setBooleans()" class="inputField" form="newUserForm" type='email' id="emailCellText" name="emailCellText" style="width: 270px"/></td>
		</tr>
		<tr>
			<td>Rechte:</td>
			<td>
				<select  id="rightsSelect" style="width: 270px">
					<option value="0">Recht auf Stellv.</option>
					<option value="1">Dozent</option>
					<option value="2">Modulverantwortlicher</option>
					<option value="3">Dezernat II</option>
					<option value="4">Koordinator</option>
					<option value="5">Freigabeberechtigter</option>
					<option value="6">Administrator</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>Institut:</td>
			<td>
				<select id="instituteSelect" style="width: 270px">
					<% for(int i=0; i<NUMBER_OF_INSTITUTES;i++) { %>
						<option value=<%=i %>><%=institutes.get(i) %></option>
					<%} %>
			    </select>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<form id="newUserForm" action="/SopraMMS/SaveUser" method="get">
					<input type="submit" name="Submit" id="saveButton" style="float: right;" value="Speichern" disabled/>
					<textarea name="rightsSelect" style="display:none;" id="rightsLabel"></textarea>
					<textarea name="instituteSelect" style="display:none;" id="instituteLabel"></textarea>
				</form>
			</td>
		</tr>
	</table>
</div>
<script type="text/javascript" src="/SopraMMS/js/jquery.newuser.js"></script>
<script type="text/javascript" src="/SopraMMS/js/jquery.multiple.select.js"></script>
<script type="text/javascript">
initMultiSelect();
</script>