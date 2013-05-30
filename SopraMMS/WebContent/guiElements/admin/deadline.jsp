<h1>Stichtag</h1>
<p Es gibt mittlerweile noch keinen Stichtag f&uuml;r ihre Facul&auml;t.p>
	<meta charset="utf-8" />
 	<title>jQuery UI Datepicker - Default functionality</title>
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
 	<link rel="stylesheet" href="/resources/demos/style.css" />
 	<script>
 		$(function() {
		$( "#deadline" ).datepicker();
		$( "#rememberbegin" ).datepicker();
		});
 		jQuery(function($){
			$.datepicker.regional['de'] = {clearText: 'l�schen', clearStatus: 'aktuelles Datum l�schen',
              closeText: 'schlie�en', closeStatus: 'ohne �nderungen schlie�en',
              prevText: '<zur�ck', prevStatus: 'letzten Monat zeigen',
              nextText: 'Vor>', nextStatus: 'n�chsten Monat zeigen',
              currentText: 'heute', currentStatus: '',
              monthNames: ['Januar','Februar','M�rz','April','Mai','Juni',
              'Juli','August','September','Oktober','November','Dezember'],
              monthNamesShort: ['Jan','Feb','M�r','Apr','Mai','Jun',
              'Jul','Aug','Sep','Okt','Nov','Dez'],
              monthStatus: 'anderen Monat anzeigen', yearStatus: 'anderes Jahr anzeigen',
              weekHeader: 'Wo', weekStatus: 'Woche des Monats',
              dayNames: ['Sonntag','Montag','Dienstag','Mittwoch','Donnerstag','Freitag','Samstag'],
              dayNamesShort: ['So','Mo','Di','Mi','Do','Fr','Sa'],
              dayNamesMin: ['So','Mo','Di','Mi','Do','Fr','Sa'],
              dayStatus: 'Setze DD als ersten Wochentag', dateStatus: 'W�hle D, M d',
              dateFormat: 'dd.mm.yy', firstDay: 1, 
              initStatus: 'W�hle ein Datum', isRTL: false};
			$.datepicker.setDefaults($.datepicker.regional['de']);
		});
	</script>
  
	<table>
		<tr>
			<td>Neuer Stichtag:</td>
			<td>
				<input form="submit" type="date" id="deadline" />
		</tr>
		<tr>			
			<td>Erinnerungsbeginn:</td>
			<td>
				<input form="submit" type="date" id="rememberbegin" />
			</td>
		</tr>
	</table><br/>
<form name="submit" action="/SopraMMS/Deadline" method="get">
	<input type="submit" value="Neuen Stichtag festlegen">
</form>
