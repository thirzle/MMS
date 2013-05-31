<script>
$(document).ready(function() {
		$("#addNews").click(function(e) {
		$(".contentBox").load("/SopraMMS/guiElements/admin/creatNews.jsp");
		});
		$("#showNews").click(function(e) {
			$(".contentBox").load("/SopraMMS/guiElements/admin/showNews.jsp");
			});
});
</script>


<div class="menuBox">
	<h1>Administrator</h1>

	<ul class="nav">
		<div class="headerNavAdminAccount">
			<li>Benutzerverwaltung</li>
		</div>
		<div class="expandAdminAccount">
			<ul class="subNav">
				<li id="newUser"><a href="/SopraMMS/NewUser">Neuer
						Benutzer</a></li>
				<li id="editUser"><a href="/SopraMMS/EditUser">Benutzer
						bearbeiten</a></li>
				<li id="deleteUser"><a href="/SopraMMS/DeleteUser">Benutzer
						löschen</a></li>
			</ul>
		</div>
		
		<div class="headerNavAdminNews">
			<li>Neuigkeiten</li>
		</div>
		<div class="expandAdminNews">
			<ul class="subNav">
				<li id="showNews">&Uuml;bersicht Neuigkeiten</li>
				<li id="addNews">Neuigkeit verfassen</li>
			</ul>
		</div>
		
		<div class="headerNavAdminModule">
			<li>Modulhandbuchverwaltung</li>
		</div>
		<div class="expandAdminModule">
			<ul class="subNav">
				<li id="generatePDF"><a href="/SopraMMS/CreatePDF">PDF generieren</a></li>
				<li id="deadline"><a href="/SopraMMS/Deadline">Stichtag</a></li>
			</ul>
		</div>
	</ul>
</div>

