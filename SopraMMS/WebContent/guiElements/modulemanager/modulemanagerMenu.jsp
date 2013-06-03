<script>
$(document).ready(function() {
		$(".newModule").click(function(e) {
		$(".contentBox").load("/SopraMMS/guiElements/modulemanager/createNewModule.jsp");
		});
});
</script>


<div class="menuBox">
	<h1>Modulverwaltung</h1>


	<ul class="nav">
		<div class="headerNavModul" ><li>Modulverwaltung</li></div>
		<div class="expandModul">
			<ul class="subNav">
				<li class='newModule'>Neues Modul eintragen</li>
			</ul>
		</div>
	</ul>
</div>