<h1>Stichtag</h1>
<p Es gibt mittlerweile noch keinen Stichtag f&uuml;r ihre Facul&auml;t.p>
	<meta charset="utf-8" />
 	<title>jQuery UI Datepicker - Default functionality</title>
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
	<script src="/SopraMMS/js/jquery.datepicker.js"></script>
 	<link rel="stylesheet" href="/resources/demos/style.css" />
	<table>
		<tr>
			<td>Neuer Stichtag:</td>
			<td>
				<input form="deadline" type="date" id="deadline" />
		</tr>
		<tr>			
			<td>Erinnerungsbeginn:</td>
			<td>
				<input form="deadline" type="date" id="rememberbegin" />
			</td>
		</tr>
	</table>
	 	<script>
 		$(function() {
		$( "#deadline" ).datepicker();
		$( "#rememberbegin" ).datepicker();
		});
	</script>
<form name="deadline" action="/SopraMMS/Deadline" method="get">
	<input type="submit" value="Neuen Stichtag festlegen">
</form>
