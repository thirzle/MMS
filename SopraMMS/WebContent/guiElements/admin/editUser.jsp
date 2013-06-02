
<%@ page import="java.util.List, user.User" %>
<%
List<String> institutes = null;
List<String> instituteNames = null;
List<String> emptyInputs = null;
User user = null;
List<String> userInstitutes = null;
boolean[] userRights = null;
int NUMBER_OF_INSTITUTES = 0;
try {
	institutes = (List<String>) session.getAttribute("institutes");
	instituteNames = (List<String>) session.getAttribute("instituteNames");
	emptyInputs = (List<String>) session.getAttribute("emptyInputs");
	user = (User) session.getAttribute("userToEdit");
	NUMBER_OF_INSTITUTES = instituteNames.size();
} catch(NullPointerException e) {
	System.out.println("NullPointerException");
} finally {
if(user != null) {
	userInstitutes = user.getInstitute();
	userRights = user.getRights();
}
boolean empty_loginCellText = false;
boolean empty_firstnameCellText = false;
boolean empty_lastnameCellText = false;
boolean empty_emailCellText = false;
boolean empty_rightsSelect = false;
boolean empty_instituteSelect = false;
if(emptyInputs != null) {
	for(String string : emptyInputs) {
		empty_loginCellText = string == "loginCellText" || empty_loginCellText ;
		empty_firstnameCellText = string == "firstnameCellText" || empty_firstnameCellText;
		empty_lastnameCellText = string == "lastnameCellText" || empty_lastnameCellText;
		empty_emailCellText = string == "emailCellText" || empty_emailCellText;
		empty_rightsSelect = string == "rightsSelect"|| empty_rightsSelect;
		empty_instituteSelect = string == "instituteSelect" || empty_instituteSelect;
	}
}
%>
<script type="text/javascript" src="/SopraMMS/js/jquery.multiple.select.js"></script>
<script type="text/javascript" src="/SopraMMS/js/jquery.edituser.js"></script>
<link rel="stylesheet" type="text/css" href="/SopraMMS/css/multiple-select.css">
<h1>Benutzer bearbeiten</h1>
<div>
	<table>
		<tr>
			<td>Benutzername:</td>
			<td><input class="inputField" form="newUserForm" type='text' id="loginCellText" name="loginCellText"/></td>
			<td>
			<%
			if(empty_loginCellText) {
				out.print("<p style='color: #FF0000;'>Bitte füllen Sie alle Felder aus.</p>");
			} else if(user != null) {
				out.print("<textarea style='display: none;' id='tmpLoginname'>"+user.getLogin()+"</textarea>");
			}
			%>
			</td>
		</tr>
		<tr>
			<td>Vorname:</td>
			<td><input class="inputField" form="newUserForm" type='text' id="firstnameCellText" name="firstnameCellText"/></td>
			<td>
			<%
			if(empty_firstnameCellText) {
				out.print("<p style='color: #FF0000;'>Bitte füllen Sie alle Felder aus.</p>");
			} else if(user != null) {
				out.print("<textarea style='display: none;' id='tmpFirstname'>"+user.getFirstName()+"</textarea>");
			}
			%>
			</td>
		</tr>
		<tr>
			<td>Nachname:</td>
			<td><input class="inputField" form="newUserForm" type='text' id="lastnameCellText" name="lastnameCellText"/></td>
			<td>
			<%
			if(empty_lastnameCellText) {
				out.print("<p style='color: #FF0000;'>Bitte füllen Sie alle Felder aus.</p>");
			} else	if(user != null) {
				out.print("<textarea style='display: none;' id='tmpLastname'>"+user.getLastName()+"</textarea>");
			}
			%>
			</td>
		</tr>
		<tr>
			<td>Email:</td>
			<td><input class="inputField" form="newUserForm" type='text' id="emailCellText" name="emailCellText" style="width: 260px"/></td>
			<td>
			<%
			if(empty_emailCellText) {
				out.print("<p style='color: #FF0000;'>Bitte füllen Sie alle Felder aus.</p>");
			} else	if(user != null) {
				out.print("<textarea style='display: none;' id='tmpEmail'>"+user.getMail()+"</textarea>");
			}
			%>
			</td>
		</tr>
		<tr>
			<td>Rechte:</td>
			<td>
				<select class="inputField" id="rightsSelect" style="width: 270px">
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
			} else	if(userRights != null) {
				String text = "";
				for(int i=0;i<userRights.length;i++){
					if(userRights[i]) {
						text += i+",";
					}
				}
				out.print("<textarea style='display: none;' id='rightsSelectText'>"+text+"</textarea>");
			}
			%>
			</td>
		</tr>
		<tr>
			<td>Institut:</td>
			<td>
				<select class="inputField" id="instituteSelect" style="width: 270px">
					<% for(int i=0; i<NUMBER_OF_INSTITUTES;i++) { %>
						<option value=<%=i %>><%=instituteNames.get(i) %></option>
					<%} %>
			    </select>
			</td>
			<td>
			<%
			if(empty_instituteSelect) {
				out.print("<p style='color: #FF0000;'>Bitte füllen Sie alle Felder aus.</p>");
			} else	if(institutes != null && userInstitutes != null) {
				String text = "";
				for( String string : userInstitutes) {
					System.out.println("(editUser.jsp):string="+string);
					int index = institutes.indexOf(string);
					text += index+",";
				}
				out.print("<textarea style='display: none;' id='instituteSelectText'>"+text+"</textarea>");
			}
			%>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<form id="newUserForm" onsubmit="setValues()"action="/SopraMMS/ChangeUser" method="get">
					<input type="submit" name="Submit" id="saveButton" style="float: right;" value="Ändern"/>
					<textarea name="rightsSelect" style="display:none;" id="rightsLabel"></textarea>
					<textarea name="instituteSelect" style="display:none;" id="instituteLabel"></textarea>
				</form>
			</td>
		</tr>
	</table>
</div>
<%}//end finally %>
<script>
initMultiSelect();
loadDataIntoForm();
loadDataIntoMultiSelect();
</script>