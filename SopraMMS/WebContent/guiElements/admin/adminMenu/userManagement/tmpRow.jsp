
<%@ page import="java.util.List, java.io.PrintWriter" %>
<%
List<String> institutes = (List<String>) session.getAttribute("institutes");
int NUMBER_OF_INSTITUTES = institutes.size();
%>
<tr id="tmpRow" style="height: 30px;">
	<td class="newCell" id="loginCell">
		<input form="addUserForm" type='text' name="loginCellText"></input>
		<div>Bitte einfügen</div>
	</td>
	<td class='newCell' id='firstnameCell'>
		<input form="addUserForm" type='text' name="firstnameCellText"></input>
		<div>Bitte einfügen</div>
	</td>
	<td class='newCell' id='lastnameCell'>
		<input form="addUserForm" type='text' name="lastnameCellText"></input>
		<div>Bitte einfügen</div>
	</td>
	<td class='newCell' id='emailCell'>
		<input form="addUserForm" type='text' name="emailCellText" /> 
		<div>Bitte einfügen</div>
	</td>
	<td class='newCell' id='rightsCell'>
		<select form="addUserForm" name="rightsSelect" id="s1">
		  <option value="0">Modulver.</option>
		  <option value="1">Redakteur</option>
		  <option value="2">Administrator</option>
		</select>
	</td>
	<td class='newCell' id='instituteCell'>
		<select form="addUserForm" name="instituteSelect">
		<% for(int i=0; i<NUMBER_OF_INSTITUTES; i++) { %>
		  <option value=<%=i %>><%=institutes.get(i) %></option>
		<%} %>
		</select>
	</td>
	<td class='newCell' id='representativeCell'>
	</td>
</tr>
<script type="text/javascript" src=" ${pageContext.request.contextPath}/js/script.js"></script>
<script type="text/javascript">

$("input:text").hide();
$(".newCell").hover(
// Wenn die Maus eine Tabellenzelle mit der Klasse 'newCell' erreicht
function() {
	var inputFeld = $("input[name='"+$(this).attr('id')+"Text']");
	var thisDiv = $("div",this);
	var divText = ""+thisDiv.text();
	// Prufe ob in der Zelle schon ein Inhalt steht.
	if(divText != "") {
		inputFeld.show();
		thisDiv.hide();
	} else {
		inputFeld.show();
	}
}, 
// Wenn die Maus wieder von der Zelle austritt
function() {
	var inputFeld = $("input[name='"+$(this).attr('id')+"Text']");
	var inputText = inputFeld.attr('value');
	// Wenn das input feld sichtbar war
	if(inputFeld.is(":visible")) {
		// Wenn der Inhalt vom input feld nicht leer war
		if(inputText!="") {
			$("div",this).show();
			$("div",this).text(inputText);
			inputFeld.hide();
		// Wenn das input Feld keinen Inhalt hatte wird es einfach wieder versteckt
		} else {
			inputFeld.hide();
		}
	}
});
	
	
	
	
	
	

</script>