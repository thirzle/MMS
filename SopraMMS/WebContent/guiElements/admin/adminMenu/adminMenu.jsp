<div class="menuBox">
	<h1>Administrator</h1>

	<script>
		var newUserCompleted = true;
		$(document).ready(
				function() {
					$(".headerNavAdministration").click(
							function(e) {
								
								$(".expandAdministration").toggleClass(
										"expanded");
								$(".expandAdministration").children("ul:first")
										.slideToggle("fast");
								e.stopPropagation();
							});
				}); //$(document).ready
	</script>


	<ul class="nav">
		<div class="headerNavAdministration">
			<li><a href="/SopraMMS/LoadUser">Benutzerverwaltung</a></li>
		</div>
		<div class="expandAdministration">
			<ul class="subNav">
				<li id="newUser"><a href="/SopraMMS/AddUser">Neuer
						Benutzer</a></li>
				<li id="editUser"><a href="/SopraMMS/EditUser">Benutzer
						bearbeiten</a></li>
				<li id="deleteUser"><a href="/SopraMMS/DeleteUser">Benutzer
						löschen</a></li>
			</ul>
		</div>
	</ul>
</div>
