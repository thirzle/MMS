<div class="menuBox">
	<h1>Freigabeberechtigter</h1>
<%
session.setAttribute("editModuleForEditor", "editModuleForEditor");
%>


	<ul class="nav">
		<div class="headerNavEditorModule">
			<li>Modulverwaltung</li>
		</div>
		<div class="expandEditorModule">
			<ul class="subNav">
				<li id="checkModule"><a href="/SopraMMS/ShowModulesOverviewForEditor">Freizugebende Module</a></li>
				<li id="createModule"><a href='/SopraMMS/CreateModule'>Modul
						erstellen</a></li>
				<li id="editModule"><a href='/SopraMMS/ShowModulesOverviewForEditor'>Modul
						bearbeiten</a></li>
			</ul>
		</div>
	</ul>
</div>
