<script type="text/javascript" src="/SopraMMS/js/jquery.newmessage.js"></script>
<script type="text/javascript" src="/SopraMMS/js/inputmanager.js"></script>
<%@page import="user.User"%>
<%@ page import="model.UserDBController"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%
	List<User> users;
	try {
	    users = (List<User>) session.getAttribute("users");
	    System.out.println("users " + users);
%>
<h1>Neue Nachricht</h1>
<form id="sendMailForm" method="get" action="/SopraMMS/Mail"></form>
<table cellpadding="10" cellspacing="10">
	<tr>
		<td align="left" colspan="2">Absender:</td>
		<td align="left">
			<%User user = (User) session.getAttribute("user");
				out.print(user.getMail());%>
		</td>
	</tr>
	<tr>
		<td align="left" colspan="2">Empf�nger:</td>
		<td align="left">
		<input class="inputField" form="sendMailForm" name="mailto" id="mailto"
			onKeyUp="Eingabe(this.value);" type="text" size="40"></td>
		<!-- <br>
		<br> -->
		<td align="left">
		<select class="inputField" name="mailaddress" id="mailaddress" onclick="uebertrag();" style="width: 250px">
				<%
			for(int i=0; i<users.size();i++) {
		%>
				<option value=<%=users.get(i).getMail()%>><%=users.get(i).getMail()%></option>
				<%
			}
		%>
		</select>
		</td>
	</tr>
	<tr>
		<td align="left" colspan="2">Betreff:</td>
		<td align="left" colspan="4"><input class="inputField" form="sendMailForm"
			type="text" name="subject" size="40"></td>
	</tr>
	<tr>
		<td align="left" colspan="2">Nachricht:</td>
		<td align="left" colspan="4"><textarea form="sendMailForm"
				cols="77" rows="20" name="message" style="resize: none;"></textarea>
		</td>
	</tr>
	<%
		} catch(NullPointerException e) {
		    System.out.println("(newMessage.jsp): KEINE USER VORHANDEN ");
		}
		%>
</table>
<table cellpadding="10" cellspacing="10" align=right>
	<tr>
		<td><button form="sendMailForm" type="submit" value="Senden">Senden</button>
		</td>
	</tr>
</table>
<script>
checkInput(null);
</script>