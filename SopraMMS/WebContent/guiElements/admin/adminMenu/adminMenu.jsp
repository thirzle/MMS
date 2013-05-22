<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/default.css">
<div class="menuBox">
	<h1>Administrator</h1>

	<script>
	var newUserCompleted = true;
		$(document).ready(function() {
			$(".headerNavAdministration").click(function(e) {
				$(".expandAdministration").toggleClass("expanded");
				$(".expandAdministration").children("ul:first").slideToggle("300");
				$(".contentBox").load("admin/adminMenu/userManagementContent.jsp");
				e.stopPropagation();
				
				$("#newUser").click(function() {
					$("#saveButton").show();
					if(newUserCompleted) {
						newUserCompleted = false;
						// neue Zeile wird an die Tabelle angehangt.
						$.get("admin/adminMenu/userManagement/tmpRow.jsp", function (data) {
							$("#userTableBody").append(data);
			            });
					} else {
						//schon vorhandene Zeile wird sichtbar gemacht.
						$("#tmpRow").show();
					}
				});
				$("#editUser").click(function() {
					$("#saveButton").hide();
					alert($(this).attr("id")+" was clicked");
					$("#tmpRow").hide();
				});
			});
		}); //$(document).ready
	</script>


	<ul id="nav">
		<div class="headerNavAdministration" ><li>Benutzerverwaltung</li></div>
		<div class="expandAdministration">
			<ul id="subNav">
				<li id="newUser">Neuer Benutzer</li>
				<li id="editUser">Benutzer bearbeiten</li>
				<li id="deleteUser">Benutzer löschen</li>
			</ul>
		</div>
	</ul>
</div>
<% Boolean reloadTable = session.getAttribute("task") == "reloadTable";
   if(reloadTable) { 
  	out.println("<script type='text/javascript'>$('.menu').ready(function(){$('.contentBox').load('admin/adminMenu/userManagementContent.jsp');});</script>");
   } %>
