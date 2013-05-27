<%@page import="user.UserAdministration"%>
<%@page import="user.User"%>
<%@page import="java.util.LinkedList"%>
<script>
	$(document)
			.ready(
					function() {
						$(".appointRepresentative")
								.click(
										function(e) {
											$(".contentBox")
													.load(
															"/SopraMMS/guiElements/generally/appointRepresentative.jsp");
										});
					});

	$(document)
			.ready(
					function() {
						$(".removeRepresentative")
								.click(
										function(e) {
											$(".contentBox")
													.load(
															"/SopraMMS/guiElements/generally/removeRepresentative.jsp");
										});
					});
</script>

<h1>Stellvertreter</h1>
<p>Ihr aktueller Stellvertreter:</p>

<form action="/SopraMMS/RemoveRepresentative" method="get">
	<table>
		<tr>
			<td>Vorname:</td>
			<td>
				<%
					User user = (User) session.getAttribute("user");
					String representativeName = user.getRepresentative();
					//String representativeName = "lehrd";
					User userR = new UserAdministration().getUser(representativeName);
					out.println(user.getFirstName());
				%>
			</td>
		</tr>
		<tr>
			<td>Nachname:</td>
			<td>
				<%
					out.println(userR.getLastName());
				%>
			</td>
		</tr>
		<tr>
			<td>E-Mail:</td>
			<td>
				<%
					out.println(userR.getMail());
				%>
			</td>
		</tr>
		<tr>
			<td>Fakult&auml;t:</td>
			<td>
				<%
					out.println(new UserAdministration().getFacultyName(userR));
				%>
			</td>
		</tr>
		<tr>
			<td>Institut:</td>
			<%
				LinkedList<String> instituteList = (LinkedList) new UserAdministration()
						.getInstituteNames(userR);
			%>
			<td>
				<%
					out.println(instituteList.getFirst());
					instituteList.removeFirst();
				%>
			</td>
			<%
				for (String institute : instituteList) {
			%>
		
		<tr>
			<td></td>
			<td>
				<%
					out.println(institute);
				%>
			</td>
		</tr>
		<%
			}
		%>
		</tr>

	</table>
	<p>
		<input class="appointRepresentative" type="submit"
			name="StellvertreterErnennen" value="Stellvertreter beantragen" /> <input
			class="removeRepresentative" type="submit"
			name="StellvertreterEntfernen" value="Stellvertreter entfernen" />
</form>
</p>