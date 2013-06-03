
<%@ page import="java.util.List, java.util.ArrayList" %>
<%
List<String> institutes = (List<String>) session.getAttribute("institutes");
String instituteIndex = "";
String rightsIndex = "";
List<String> emptyInputs = (List<String>) session.getAttribute("emptyInputs");
List<String[]> notEmptyInputs = (List<String[]>) session.getAttribute("notEmptyInputs");
int NUMBER_OF_INSTITUTES = institutes.size();
boolean empty_firstnameCellText = false;
boolean empty_lastnameCellText = false;
boolean empty_emailCellText = false;
boolean empty_rightsSelect = false;
boolean empty_instituteSelect = false;
if(emptyInputs != null) {
	for(String string : emptyInputs) {
		empty_firstnameCellText = string == "firstnameCellText" || empty_firstnameCellText;
		empty_lastnameCellText = string == "lastnameCellText" || empty_lastnameCellText;
		empty_emailCellText = string == "emailCellText" || empty_emailCellText;
		empty_rightsSelect = string == "rightsSelect"|| empty_rightsSelect;
		empty_instituteSelect = string == "instituteSelect" || empty_instituteSelect;
	}
	if(!empty_instituteSelect) {
		for ( String[] string : notEmptyInputs ) {
			if(string[0] == "instituteSelect" ) {
				instituteIndex = string[1];
			}
		}
	}
	if(!empty_rightsSelect) {
		for ( String[] string : notEmptyInputs ) {
			if(string[0] == "rightsSelect" ) {
				rightsIndex = string[1];
			}
		}
	}
}
%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/multiple-select.css">
<h1>Neuen Benutzer anlegen</h1>
<div>
	<table>
		<tr>
			<td>Benutzername:</td>
			<td><input class="inputField" form="newUserForm" type='text' id="loginCellText" name="loginCellText" readonly/></td>
			<td id="loginnameTableCell"></td>
		</tr>
		<tr>
			<td>Vorname:</td>
			<td><input oninput="generateLoginname()" class="inputField" form="newUserForm" type='text' id="firstnameCellText" name="firstnameCellText"/></td>
			<td>
			<%
			if(empty_firstnameCellText) {
				out.print("<p style='color: #FF0000;'>Bitte füllen Sie alle Felder aus.</p>");
			} else if(notEmptyInputs != null) {
				for(String[] string : notEmptyInputs) {
					if(string[0] == "firstnameCellText") {
						out.print("<textarea style='display: none;' id='tmpFirstname'>"+string[1]+"</textarea>");
					}
				}
			}
			%>
			</td>
		</tr>
		<tr>
			<td>Nachname:</td>
			<td><input oninput="generateLoginname()" class="inputField" form="newUserForm" type='text' id="lastnameCellText" name="lastnameCellText"/></td>
			<td>
			<%
			if(empty_lastnameCellText) {
				out.print("<p style='color: #FF0000;'>Bitte füllen Sie alle Felder aus.</p>");
			} else if(notEmptyInputs != null) {
				for(String[] string : notEmptyInputs) {
					if(string[0] == "lastnameCellText") {
						out.print("<textarea style='display: none;' id='tmpLastname'>"+string[1]+"</textarea>");
					}
				}
			}
			%>
			</td>
		</tr>
		<tr>
			<td>Email:</td>
			<td><input class="inputField" form="newUserForm" type='text' id="emailCellText" name="emailCellText" style="width: 270px"/></td>
			<td>
			<%
			if(empty_emailCellText) {
				out.print("<p style='color: #FF0000;'>Bitte füllen Sie alle Felder aus.</p>");
			} else if(notEmptyInputs != null) {
				for(String[] string : notEmptyInputs) {
					if(string[0] == "emailCellText") {
						out.print("<textarea style='display: none;' id='tmpEmail'>"+string[1]+"</textarea>");
					}
				}
			}
			%>
			</td>
		</tr>
		<tr>
			<td>Rechte:</td>
			<td>
				<select  id="rightsSelect" style="width: 270px">
					<option value="0">Modulver.</option>
					<option value="1">Redakteur</option>
					<option value="2">Administrator</option>
					<option value="3">Recht auf Stellv.</option>
					<option value="4">Dezernat II</option>
				</select>
			</td>
			<td>
			<%
			if(empty_rightsSelect) {
				out.print("<p style='color: #FF0000;'>Bitte füllen Sie alle Felder aus.</p>");
			} else	if(rightsIndex != "") {
				System.out.println("(newUser.jsp): index: "+rightsIndex);
				out.print("<textarea style='display: none;' id='rightsSelectText'>"+rightsIndex+"</textarea>");
			}
			%>
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
			<td>
			<%
			if(empty_instituteSelect) {
				out.print("<p style='color: #FF0000;'>Bitte füllen Sie alle Felder aus.</p>");
			} else	if(instituteIndex != "") {
				System.out.println("(newUser.jsp): index: "+instituteIndex);
				out.print("<textarea style='display: none;' id='instituteSelectText'>"+instituteIndex+"</textarea>");
			}
			%>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<form id="newUserForm" onsubmit="checkValues()"action="/SopraMMS/SaveUser" method="get">
					<input type="submit" name="Submit" id="saveButton" style="float: right;" value="Speichern"/>
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
loadDataIntoForm();
loadDataIntoMultiSelect();
</script>