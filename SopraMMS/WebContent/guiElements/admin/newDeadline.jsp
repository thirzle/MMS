  <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
  <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
  <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
  	<script src="/SopraMMS/js/jquery.datepicker.translate.js"></script>
<h1>Stichtag</h1>
<p> Es gibt mittlerweile noch keinen Stichtag f&uuml;r ihre Fakul&auml;t.</p>
  <script>
  $(function() {
    $( "#datepicker1" ).datepicker();
    
  });
  $(function() {
	$( "#datepicker2" ).datepicker();
  })
  </script>
<table>
	<tr>
			<td>Neuer Stichtag:</td>
			<td>
				<input form="deadline" type="date" id="datepicker1" />
			</td>
	</tr>
	<tr>		
			<td>Erinnerungsbeginn:</td>
			<td>
				<input form="deadline" type="date" id="datepicker2" />
			</td>
	</tr>
</table>
<form name="deadline" action="/SopraMMS/Deadline" method="get">
	<input type="submit" value="Neuen Stichtag festlegen">
</form>
