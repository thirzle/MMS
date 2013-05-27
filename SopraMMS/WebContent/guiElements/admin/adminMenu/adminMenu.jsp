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
										.slideToggle("300");
								e.stopPropagation();
							});
				}); //$(document).ready
	</script>


	<ul class="nav">
		<div class="headerNavAdministration">
			<li>Benutzerverwaltung</li>
		</div>
		<div class="expandAdministration">
			<ul class="subNav">
				<li id="newUser"><a href="/SopraMMS/AddUser?newUser">Neuer
						Benutzer</a></li>
				<li id="editUser"><a href="/SopraMMS/AddUser?editUser">Benutzer
						bearbeiten</a></li>
				<li id="deleteUser"><a href="/SopraMMS/AddUser?deleteUser">Benutzer
						löschen</a></li>
			</ul>
		</div>
	</ul>
</div>
