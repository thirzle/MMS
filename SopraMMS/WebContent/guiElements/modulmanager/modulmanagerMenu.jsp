<div class="menuBox">
	<h1>Modulverwaltung</h1>

	<script>
		$(document).ready(function() {
			$(".headerNavModul").click(function(e) {
				$(".expandModul").toggleClass("expanded");
				$(".expandModul").children("ul:first").slideToggle("300");
				e.stopPropagation();
				$(".contentBox").load("/SopraMMS/guiElements/homeContent.jsp");
			});
		}); //$(document).ready
	</script>


	<ul class="nav">
		<div class="headerNavModul" ><li>Modulverwaltung</li></div>
		<div class="expandModul">
			<ul class="subNav">
				<li>TODO</li>
			</ul>
		</div>
	</ul>
</div>