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
			<td><%=session.getAttribute("firstnameRep")%></td>
		</tr>
		<tr>
			<td>Nachname:</td>
			<td><%=session.getAttribute("lastnameRep")%></td>
		</tr>
		<tr>
			<td>E-Mail:</td>
			<td><%=session.getAttribute("emailRep")%></td>
		</tr>
		<tr>
			<td>Fakult&auml;t:</td>
			<td><%=session.getAttribute("facRep")%></td>
		</tr>
		<tr>
			<%
				LinkedList<String> instituteList = (LinkedList) session
						.getAttribute("inRep");
			%>
			<td >Institut(e):</td>
			<td >
				<ul>
					<li>
						<%
							out.println(instituteList.getFirst());
							instituteList.removeFirst();
						%>
						
					</li>
					<%
						for (String institute : instituteList) {
					%>
					<li>
						<%
							out.println(institute);
						%>
						<br>
					</li>
					<%
						}
					%>
				</ul>
			</td>
		</tr>

	</table>
	<p>
		<input class="appointRepresentative" type="submit"
			name="StellvertreterErnennen" value="Stellvertreter beantragen" /> <input
			class="removeRepresentative" type="submit"
			name="StellvertreterEntfernen" value="Stellvertreter entfernen" />
</form>
</p>