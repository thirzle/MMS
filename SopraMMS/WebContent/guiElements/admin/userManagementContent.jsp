<script type="text/javascript">
$(".expandAdministration").toggleClass(
"expanded");
$(".expandAdministration").children("ul:first")
.slideToggle("fast");
e.stopPropagation();
</script>
		<h1>Benutzerverwaltung</h1>

<div class="userManagementContent">
	<jsp:include page="/guiElements/admin/table.jsp"></jsp:include>
	<div id="controlBoard"></div>
</div>


