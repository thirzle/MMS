  <%@page import="java.text.SimpleDateFormat"%>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
  <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
  <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
  	<script src="/SopraMMS/js/jquery.datepicker.translate.js"></script>
<h1>Stichtag</h1>
<p>Folgender Stichtag ist f&uuml;r ihre Fakul&auml;t im Moment festgelegt.</p>
  <script>
  $(function() {
    $( "#datepicker1" ).datepicker();
    
  });
  $(function() {
	$( "#datepicker2" ).datepicker();
  })
  </script>
 <form name="change" action="/SopraMMS/Deadline" method="get">
<table>
	<tr>
			<td>Stichtag:</td>
			<td>
				<input name="deadline" type="date" id="datepicker1" 
				value="<%=new SimpleDateFormat("dd.MM.yyyy").format(session.getAttribute("deadline"))%>"/>
			</td>
	</tr>
	<tr>		
			<td>Erinnerungsbeginn:</td>
			<td>
				<input name="rememberbegin" type="date" id="datepicker2" 
				value="<%=new SimpleDateFormat("dd.MM.yyyy").format(session.getAttribute("beginremember"))%>"/>
			</td>
	</tr>
</table>
<input type="submit" value="Stichtag ändern">
</form>