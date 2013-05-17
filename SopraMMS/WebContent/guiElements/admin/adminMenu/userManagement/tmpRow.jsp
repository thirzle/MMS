
<tr id="tmpRow" style="height: 30px;">
	<td class="newCell" id="loginCell">
		<input style='width: 100%; height: 100%;' type='text' name="loginCellText"></input>
	</td>
	<td class='newCell' id='firstnameCell'>
		<input style='width: 100%; height: 100%;' type='text' name="firstnameCellText"></input>
	</td>
	<td class='newCell' id='lastnameCell'>
		<input style='width: 100%; height: 100%;' type='text' name="lastnameCellText"></input>
	</td>
	<td class='newCell' id='emailCell'>
		<input style='width: 100%; height: 100%;' type='text' name="emailCellText"></input>
	</td>
	<td class='newCell' id='rightsCell'>
		<input style='width: 100%; height: 100%;' type='text' name="rightsCellText"></input>
	</td>
	<td class='newCell' id='instituteCell'>
		<input style='width: 100%; height: 100%;' type='text' name="instituteCellText"></input>
	</td>
	<td class='newCell' id='supervisorCell'>
		<input style='width: 100%; height: 100%;' type='text' name="supervisorCellText"></input>
	</td>
</tr>
<script type="text/javascript" src=" ${pageContext.request.contextPath}/js/script.js"></script>
<script type="text/javascript">

$("input:text").hide();
$(".newCell").hover(
// Wenn die Maus eine Tabellenzelle mit der Klasse 'newCell' erreicht
function() {
	var inputFeld = $("input[name='"+$(this).attr('id')+"Text']");
	// Prufe ob in der Zelle schon ein Inhalt steht.
	if(""+$(this).text().trim() == "") {
		$(this).children().show();
	} else {
		// Wenn die Zelle bereits einen Inhalt hat: warte auf ein Klick
		$(this).click(function() {
			// Zeige das input Feld und trage den Inhalt von der Zelle ein.
			$(inputFeld).show();
			$(inputFeld).val(""+$(this).text().trim());
			$("div",this).remove();
		});
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
			var div = document.createElement("div");
			$(div).text(inputText);
			$(this).append(div);
			$(inputFeld).hide();
		// Wenn das input Feld keinen Inhalt hatte wird es einfach wieder versteckt
		} else {
			$(this).children().hide();
		}
	}
});
	
	
	
	
	
	

</script>