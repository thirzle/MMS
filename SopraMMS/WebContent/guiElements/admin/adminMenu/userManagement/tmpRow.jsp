
<tr id="tmpRow" style="height: 30px;">
	<td class="newCell" id="loginCell"></td>
	<td class='newCell' id='firstnameCell'></td>
	<td class='newCell' id='lastnameCell'></td>
	<td class='newCell' id='emailCell'></td>
	<td class='newCell' id='rightsCell'></td>
	<td class='newCell' id='instituteCell'></td>
	<td class='newCell' id='supervisorCell'></td>
</tr>
<script type="text/javascript" src=" ${pageContext.request.contextPath}/js/script.js"></script>
<script type="text/javascript">

// weiter machen: bug: nachdem etwas eingetragen wurde wird beim naechsten hover
// nochmal append input gemacht. fixen!

$(".newCell").hover(
function() {
	if($(this).children().size() > 0) {
		if($(this).text()=="") {
			$(this).children().show();
		} else {
						
		}
	} else {
		$(this).append("<input style='width: 100%; height: 100%;' type='text' name="+$(this).attr('id')+"></input>");
	}
}, 
function() {
	if($(this).children().size() > 0) {
		if($("input[name='"+$(this).attr('id')+"']").attr('value')=="") {
			$(this).children().hide();
		} else {
			$(this).text(""+$("input[name='"+$(this).attr('id')+"']").attr('value'));
		}
		
	}
});
	
	
	
	
	
	

</script>