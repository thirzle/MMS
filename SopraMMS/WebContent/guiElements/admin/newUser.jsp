
<%@ page import="java.util.List, java.io.PrintWriter" %>
<%
List<String> institutes = (List<String>) session.getAttribute("institutes");
int NUMBER_OF_INSTITUTES = institutes.size();
%>
<h1>Neuen Benutzer anlegen</h1>
<div>
	<table>
		<tr>
			<td>Benutzername:</td>
			<td><input form="newUserForm" type='text' name="loginCellText"/></td>
		</tr>
		<tr>
			<td>Vorname:</td>
			<td><input form="newUserForm" type='text' name="firstnameCellText"/></td>
		</tr>
		<tr>
			<td>Nachname:</td>
			<td><input form="newUserForm" type='text' name="lastnameCellText"/></td>
		</tr>
		<tr>
			<td>Email:</td>
			<td><input form="newUserForm" type='text' name="emailCellText" /></td>
		</tr>
		<tr>
			<td>Rechte:</td>
			<td>
				<select form="newUserForm" name="rightsSelect" id="s1">
					<option value="0">Modulver.</option>
					<option value="1">Redakteur</option>
					<option value="2">Administrator</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>Institut:</td>
			<td>
				<select form="newUserForm" name="instituteSelect">
					<% for(int i=0; i<NUMBER_OF_INSTITUTES; i++) { %>
						<option value=<%=i %>><%=institutes.get(i) %></option>
					<%} %>
				</select>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<form id="newUserForm" action="/SopraMMS/SaveUser" method="get">
					<input type="submit" name="Submit" id="saveButton" style="float: right;" value="Speichern" />
				</form>
			</td>
		</tr>
	</table>
</div>

<script type="text/javascript">
$(".expandAdministration").toggleClass("expanded");
$(".expandAdministration").children("ul:first").slideToggle("fast");
e.stopPropagation();
</script>