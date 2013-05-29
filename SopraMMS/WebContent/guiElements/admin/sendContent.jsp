<link rel="stylesheet" href="/SopraMMS/css/style.css" type="text/css" media="print, projection, screen" />
<script type="text/javascript" src="/SopraMMS/js/script.js"></script>
<script type="text/javascript" src="/SopraMMS/js/jquery-latest.js"></script>
<script type="text/javascript" src="/SopraMMS/js/jquery.tablesorter.js"></script>
<script type="text/javascript">

$(function() {		
	loadMessages();
	$("#messageTable").tablesorter({sortList:[[0,0],[2,1]], widgets: ['zebra']});
});	
</script>
<title>Gesendete Nachrichten</title>
<div>
<center>
	<h3>Gesendete Nachrichten</h3>
</center>
</div>
	<table id="messageTable" class="tablesorter" >
		<thead>
			<tr>
				<th>Benutzername</th>
				<th>Name</th>
				<th>Betreff</th>
				<th>Email</th>
				<th>Datum</th>
			</tr>
		</thead>
		<tbody id="messageTableBody">
		</tbody>
	</table>