<script>
	$(document).ready(function() {
	$(".noRep").click(	function(e) {
	$(".contentBox").load("/SopraMMS/guiElements/generally/appointRepresentative.jsp");});});	
</script>

<h1>Stellvertreter</h1>
<h2>Sie haben zur Zeit keinen Stellvertreter!</h2>
<input class="noRep" type="submit" name="appointRepresentative" value="Stellvertreter erstellen"/>