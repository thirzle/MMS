
<%@ page import="java.util.List, user.User" %>
<%
List<String> institutes = null;
List<String> instituteNames = null;
User user = null;
List<String> userInstitutes = null;
boolean[] userRights = null;
int NUMBER_OF_INSTITUTES = 0;
try {
	institutes = (List<String>) session.getAttribute("institutes");
	instituteNames = (List<String>) session.getAttribute("instituteNames");
	user = (User) session.getAttribute("userToEdit");
	NUMBER_OF_INSTITUTES = instituteNames.size();
} catch(NullPointerException e) {
	System.out.println("NullPointerException");
} finally {
if(user != null) {
	userInstitutes = user.getInstitute();
	userRights = user.getRights();
}
%>
<link rel="stylesheet" type="text/css" href="/SopraMMS/css/multiple-select.css">
<h1>Benutzer bearbeiten</h1>
<div>
	<table>
		<tr>
			<td>Benutzername:</td>
			<td><input form="newUserForm" type='text' id="loginCellText" style="cursor: default; border: none;" name="loginCellText" readonly/></td>
		</tr>
		<tr>
			<td>Vorname:</td>
			<td><input oninput="generateLoginname()" class="inputField" form="newUserForm" type='text' id="firstnameCellText" name="firstnameCellText" value='<%=user.getFirstName() %>'/></td>
		</tr>
		<tr>
			<td>Nachname:</td>
			<td><input oninput="generateLoginname()" class="inputField" form="newUserForm" type='text' id="lastnameCellText" name="lastnameCellText" value='<%=user.getLastName() %>'/></td>
		</tr>
		<tr>
			<td>Email:</td>
			<td><input oninput="setBooleans()" class="inputField" form="newUserForm" type='text' id="emailCellText" name="emailCellText" style="width: 260px" value='<%=user.getMail() %>'/></td>
		</tr>
		<tr>
			<td>Rechte:</td>
			<td>
				<select onchange="alert('change')" class="inputField" id="rightsSelect" style="width: 270px">
					<option value="0">Recht auf Stellv.</option>
					<option value="1">Dozent</option>
					<option value="2">Modulverantwortl.</option>
					<option value="3">Dezernat II</option>
					<option value="4">Koordinator</option>
					<option value="5">Freigabeberecht.</option>
					<option value="6">Administrator</option>
				</select>
			</td>
			<td>
			<%
			if(userRights != null) {
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
			if(institutes != null && userInstitutes != null) {
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
				<form id="newUserForm" action="/SopraMMS/ChangeUser" method="get">
					<input type="submit" name="Submit" id="saveButton" style="float: right;" value="Ändern"/>
					<textarea name="rightsSelect" style="display:none;" id="rightsLabel"></textarea>
					<textarea name="instituteSelect" style="display:none;" id="instituteLabel"></textarea>
				</form>
			</td>
		</tr>
	</table>
</div>
<%}//end finally %>
<script type="text/javascript" src="/SopraMMS/js/jquery.multiple.select.js"></script>
<script type="text/javascript" src="/SopraMMS/js/jquery.edituser.js"></script>
<script>
initMultiSelect();
loadDataIntoMultiSelect();
body.click(function() {
		setBooleans();
		setValues();
	});
</script>