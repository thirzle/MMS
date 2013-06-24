<%@page import="com.sun.org.apache.xalan.internal.xsltc.runtime.Parameter"%>
<h1>Neues Passwort beantragen</h1>
<%if(request.getParameter("errortext")!=null) {%>
<error>Die eingegebene E-Mail Addresse ist im System nicht hinterlegt</error>
<%} %>
<p>
	Bitte geben Sie in das Feld Ihre im System hinterlegte E-Mail-Adresse
	ein.<br />Anschließend wird Ihnen eine E-Mail mit einem Link
	zugesendet. Rufen Sie diesen auf und &auml;ndern Sie ihr Passwort
	anschließend ab.
</p>
<form action="/SopraMMS/SendNewPassword">
	<table>
		<tr>
			<td>E-Mail Adresse:</td>
			<td><input name="email" type="email" size="40" maxlength="50"></td>
		</tr>
	</table>
	<br /> <input type="submit" value="Neues Passwort beantragen">
</form>
<p class=importantBox>Das Zusenden der E-Mail kann einige Minuten dauern. <br/>Bitte haben Sie etwas geduld.</p>