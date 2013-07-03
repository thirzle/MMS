
<script type="text/javascript" src="/SopraMMS/js/jquery.tablesorter.js"></script>
<script type="text/javascript" src="/SopraMMS/js/tablemanager.js"></script>
<%@page import="java.util.List"%>
<%@page import="user.UserAdministration"%>
<%
	session.setAttribute("content", "showNews");
	UserAdministration ua = new UserAdministration();
	List<String[]> newsList = ua.getNewsByType(0);
%>
<h1>&Uuml;bersicht Neuigkeiten</h1>
<%
	// checks whether an infotext is available
	String infoText = request.getParameter("infotext");
	if (infoText != null) {
%>
<div class="infoBox"><%=infoText%></div>
<%
	}
%>
<form action="/SopraMMS/DeleteNews">
	<table id="showNewsTable" class='tablesorter'>
		<thead>
			<tr>
				<th></th>
				<th>Titel</th>
				<th>Textvorschau</th>
				<th>Sichtbarkeit</th>
				<th>Erstellungsdatum</th>
			</tr>
		</thead>
		<tbody>
		<%
			int index = 0;
			for (String[] entry : newsList) {
				String[] text = entry[1].split(" ");
				StringBuilder prev = new StringBuilder();
				for (int i = 0; i < 6 && i < text.length; i++) {
					prev.append(text[i] + " ");
				}
				prev.append("...");

				String type = "";
				if (entry[3].equals("0")) {
					type = "Front-End Back-End";
				} else if (entry[3].equals("1")) {
					type = "Front-End";
				} else if (entry[3].equals("2")) {
					type = "Back-End";
				}
		%>
		<tr>
			<!--  here is Waldoo ('.') 
							#	#######
							 #			#
								#######	##
										#
								######
								#	#
								#	#
			-->
			<td><input type="checkbox" name="delete<%=index%>"
				value="<%=entry[0]%>"></td>
			<td><%=entry[0]%></td>
			<td><%=prev.toString()%></td>
			<td><%=type%></td>
			<td><%=entry[2]%></td>
		</tr>


		<%
			index++;
			}
		%>
		</tbody>
	</table>
	<button type="submit" value="L&ouml;schen">Löschen</button>
</form>
<script type="text/javascript">
<!--
manageTable($("#showNewsTable"));
//-->
</script>
