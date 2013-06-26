<script>
$(document).ready(function() {
		$("#addNews").click(function(e) {
		$(".contentBox").load("/SopraMMS/guiElements/admin/createNews.jsp");
		});
		$("#showNews").click(function(e) {
		$(".contentBox").load("/SopraMMS/guiElements/admin/showNews.jsp");
		});
		$("#createInstitute").click(function(e) {
		$(".contentBox").load("/SopraMMS/guiElements/admin/createNewInstitute.jsp");
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
				<li id="editUser"><a href="/SopraMMS/LoadTable?task=edit">Benutzer
						bearbeiten</a></li>
				<li id="deleteUser"><a href="/SopraMMS/LoadTable?task=delete">Benutzer
						löschen</a></li>
			</ul>
		</div>
		
		
		<div class="headerNavAdminModule">
			<li>Modulverwaltung</li>
		</div>
		<div class="expandAdminModule">
			<ul class="subNav">
				<li id="generatePDF"><a href="/SopraMMS/CreatePDF">Modulhandbuch erstellen</a></li>
				<li id="deadline"><a href="/SopraMMS/Deadline">Stichtag festlegen</a></li>
				<li id="createInstitute">Institut erstellen</li>
				<li id="deleteModule"><a href="/SopraMMS/DeleteModule">Modul löschen</a></li>
			</ul>
		</div>
		
		
		<div class="headerNavAdminNews">
			<li>Sonstiges</li>
		</div>
		<div class="expandAdminNews">
			<ul class="subNav">
				<li id="showNews">&Uuml;bersicht Neuigkeiten</li>
				<li id="addNews">Neuigkeit verfassen</li>
				<li id="showHistory"><a href="/SopraMMS/ShowHistory">Historie</a></li>
			</ul>
		</div>
	</ul>
</div>

