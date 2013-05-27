<div class="menuBox">
	<h1>Administrator</h1>

	<script>
	var open = false;
	var newUserCompleted = true;
		$(document).ready(function() {
			$(".headerNavAdministration").click(function(e) {
				if(!open) {
					open = true;
					$.get("/SopraMMS/LoadUser",function() {
						window.location.href = "/SopraMMS/guiElements/home.jsp";
					});
				} else {
					open = false;
					$(".expandAdministration").toggleClass("expanded");
					$(".expandAdministration").children("ul:first").slideToggle("300");
					e.stopPropagation();
				}
				/*
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
					$("#tmpRow").hide();
				});
				$("#deleteUser").click(function() {
					$("#saveButton").hide();
					$("#tmpRow").hide();
				});*/
			});
		}); //$(document).ready
	</script>


	<ul class="nav">
		<div class="headerNavAdministration"><li>Benutzerverwaltung</li></div>
		<div class="expandAdministration">
			<ul class="subNav">
				<li id="newUser"><a href="/SopraMMS/AddUser" >Neuer Benutzer</a></li>
				<li id="editUser">Benutzer bearbeiten</li>
				<li id="deleteUser">Benutzer löschen</li>
			</ul>
		</div>
	</ul>
</div>
