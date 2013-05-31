<h1>Stichtag</h1>
<p Es gibt mittlerweile noch keinen Stichtag f&uuml;r ihre Facul&auml;t.p>
	<script src="/SopraMMS/js/jquery.datepicker.js"></script>
	<script src="/SopraMMS/js/jquery.eye.js"></script>
	<script src="/SopraMMS/js/jquery.js"></script>
	<script src="/SopraMMS/js/jquery.layout.js"></script>
	<script src="/SopraMMS/js/jquery.utils.js"></script>
	<script src="/SopraMMS/js/jquery.datepicker.js"></script>
 	<link rel="stylesheet" href="/resources/demos/style.css" />
 	<script>
 		$(function() {
		$( "#deadline" ).DatePicker();
		$( "#rememberbegin" ).datepicker();
		});
	</script>
  
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
<form name="deadline" action="/SopraMMS/Deadline" method="get">
	<input type="submit" value="Neuen Stichtag festlegen">
</form>
