<script>
$(function() {
	$(this).css("background-color","#FFFFFF");
	$("#userManagement").append("<div class='userManagementEntry'>"+
									"<p>Neuer Benutzer</p>"+
									"<p>Benutzer bearbeiten</p>"+
									"<p>Benutzer löschen</p>"+
								"</div>");
	$("#userManagement").hover(function() {
		$(this).css("background-color","#FFFFFF");
	});
});
</script>
<div>
	<center>
		<h3>Benutzerverwaltung</h3>
	</center>
</div>
<div class="userManagementContent">
	<jsp:include page="table.jsp"></jsp:include>
</div>


