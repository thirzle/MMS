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
			<li>Benutzerverwaltung</li>
		</div>
		<div class="expandAdministration">
			<ul class="subNav">
				<li id="newUser"><a href="/SopraMMS/NewUser">Neuer
						Benutzer</a></li>
				<li id="editUser"><a href="/SopraMMS/EditUser">Benutzer
						bearbeiten</a></li>
				<li id="deleteUser"><a href="/SopraMMS/DeleteUser">Benutzer
						löschen</a></li>
				<li id="generatePDF"><a href="/SopraMMS/GeneratePDF">PDF generieren</a></li>
			</ul>
		</div>
	</ul>
</div>
