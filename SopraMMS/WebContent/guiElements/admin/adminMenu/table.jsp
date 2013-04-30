<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" media="print, projection, screen" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-latest.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.tablesorter.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/script.js"></script>
<script type="text/javascript">
loadUsers();
$(function() {		
	$("#userTable").tablesorter({sortList:[[0,0],[2,1]], widgets: ['zebra']});
});	
</script>
	<table id="userTable" class="tablesorter">
		<thead>
			<tr>
				<th>Benutzername</th>
				<th>Vorname</th>
				<th>Nachname</th>
				<th>Email</th>
				<th>Recht</th>
				<th>Institut</th>
				<th>Supervisor</th>
			</tr>
		</thead>
		<tbody id="userTableBody">
		</tbody>
	</table>

