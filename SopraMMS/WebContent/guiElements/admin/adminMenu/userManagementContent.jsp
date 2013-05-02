<script>
$(function() {
	$(this).css("background-color","#FFFFFF");
	$("#userManagement").append("<div style='margin-top: 5%; margin-bottom: 0px;'>"+
									"<p class='userManagementEntry'>Neuer Benutzer</p>"+
									"<p class='userManagementEntry'>Benutzer bearbeiten</p>"+
									"<p class='userManagementEntry'>Benutzer löschen</p>"+
								"</div>");
	$("#userManagement").hover(function() {
		$(this).css("background-color","#FFDDFF");
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


