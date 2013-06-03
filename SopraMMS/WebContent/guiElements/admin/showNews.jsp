<link rel="stylesheet" href="/SopraMMS/css/style.css" type="text/css" media="print, projection, screen" />
<%@page import="java.util.List"%>
<%@page import="user.UserAdministration"%>
<%
	session.setAttribute("content", "showNews");
	UserAdministration ua = new UserAdministration();
	List<String[]> newsList = ua.getNewsByType(0);
%>
<h1>&Uuml;bersicht Neuigkeiten</h1>
<form action="/SopraMMS/DeleteNews">
	<table id="showNewsTable" class='tablesorter'>
		<thead>
			<tr>
				<th style="width: 1%;"></th>
				<th>Titel</th>
				<th>Text</th>
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
	<input type="submit" value="L&ouml;schen">
</form>
<script type="text/javascript" src="/SopraMMS/js/jquery.newstable.js"></script>
<script type="text/javascript" src="/SopraMMS/js/jquery.tablesorter.js"></script>


