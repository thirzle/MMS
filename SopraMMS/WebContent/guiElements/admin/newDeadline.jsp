  <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
  <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
  <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
  	<script src="/SopraMMS/js/jquery.datepicker.translate.js"></script>
  	<script src="/SopraMMS/js/inputmanager.js"></script>
<h1>Stichtag</h1>
<p> Es gibt mittlerweile noch keinen Stichtag f&uuml;r ihre Fakul&auml;t.</p>
<% 
	String content = request.getParameter("submitDeadline")+"";
	if(content.equals("deadBeforeRemem")) {
%>
<p>
	<error>Der Stichtag darf nicht vor dem Erinnerungsbeginn liegen</error>
</p>
<%
	} else if(content.equals("deadBeforeToday")) {
%>
<p>
	<error>Der Stichtag darf nicht in der Vergangenheit liegen</error>
</p>
<%
	}
%>
  <form name="submit" action="/SopraMMS/Deadline" method="get">
<table>
	<tr>
			<td>Neuer Stichtag:</td>
			<td>
				<input class="inputField" name="deadline" type="text" id="datepicker1" />
			</td>
	</tr>
	<tr>		
			<td>Erinnerungsbeginn:</td>
			<td>
				<input class="inputField" name="beginremember" type="text" id="datepicker2" />
			</td>
	</tr>
</table>
	<button type="submit" value="Neuen Stichtag festlegen">Neuen Stichtag festlegen</button>
</form>

<script>
  $(function() {
    $( "#datepicker1" ).datepicker();
    
  });
  $(function() {
	$( "#datepicker2" ).datepicker();
  });
  checkInput(null);
  </script>