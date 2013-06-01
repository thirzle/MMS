
<%@ page import="java.util.List, user.User" %>
<%
List<String> institutes = (List<String>) session.getAttribute("institutes");
User user = (User) session.getAttribute("userToEdit");
List<String> userInstitutes = null;
boolean[] userRights = null;
if(user != null) {
	userInstitutes = user.getInstitute();
	userRights = user.getRights();
}
int NUMBER_OF_INSTITUTES = institutes.size();
%>
<script type="text/javascript" src="/SopraMMS/js/jquery.multiple.select.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/multiple-select.css">
<h1>Benutzer bearbeiten</h1>
<div>
	<table>
		<tr>
			<td>Benutzername:</td>
			<td><input class="inputField" form="newUserForm" type='text' id="loginCellText" name="loginCellText"/></td>
			<td>
			<%
			if(user != null) {
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
			if(user != null) {
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
			if(user != null) {
				out.print("<textarea style='display: none;' id='tmpLastname'>"+user.getLastName()+"</textarea>");
			}
			%>
			</td>
		</tr>
		<tr>
			<td>Email:</td>
			<td><input class="inputField" form="newUserForm" type='text' id="emailCellText" name="emailCellText" style="width: 270px"/></td>
			<td>
			<%
			if(user != null) {
				out.print("<textarea style='display: none;' id='tmpEmail'>"+user.getMail()+"</textarea>");
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
				<select id="instituteSelect" style="width: 270px">
					<% for(int i=0; i<NUMBER_OF_INSTITUTES;i++) { %>
						<option value=<%=i %>><%=institutes.get(i) %></option>
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
				<form id="newUserForm" onsubmit="setValues()"action="/SopraMMS/SaveUser" method="get">
					<input type="submit" name="Submit" id="saveButton" style="float: right;" value="Speichern"/>
					<textarea name="rightsSelect" style="display:none;" id="rightsLabel"></textarea>
					<textarea name="instituteSelect" style="display:none;" id="instituteLabel"></textarea>
				</form>
			</td>
		</tr>
	</table>
</div>

<script type="text/javascript">

//$('#select2').multipleSelect('setSelects', [1, 3]);



$("#loginCellText").val($("#tmpLoginname").html());
$("#firstnameCellText").val($("#tmpFirstname").html());
$("#lastnameCellText").val($("#tmpLastname").html());
$("#emailCellText").val($("#tmpEmail").html());
var rightsText = $("#rightsSelectText").html();
var instituteText = $("#instituteSelectText").html();
alert(instituteText);
var split = rightsText.split(",");
var rights = new Array();
for ( var i = 0; i < split.length-1; i++) {
	rights[i] = split[i];
}
$("#rightsSelect").multipleSelect(); 
$("#instituteSelect").multipleSelect(); 
$('#rightsSelect').multipleSelect('setSelects', rights);
$(".expandAdministration").toggleClass("expanded");
$(".expandAdministration").children("ul:first").slideToggle("fast");
e.stopPropagation();
</script>