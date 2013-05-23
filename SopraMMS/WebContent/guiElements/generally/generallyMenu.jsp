<div class="menuBox">
	<h1>Allgemein</h1>

	<script>
		$(document).ready(function() {
			$(".headerNavGenerally").click(function(e) {
				$(".expandGenerally").toggleClass("expanded");
				$(".expandGenerally").children("ul:first").slideToggle("300");
				e.stopPropagation();
				$(".contentBox").load("/SopraMMS/guiElements/generally/generallyContent.jsp");
			});
			$(".changePassword").click(function(e) {
					$(".contentBox").load("/SopraMMS/guiElements/generally/changePassword.jsp");
			});
		});
	</script>


	<ul class="nav">
		<div class="headerNavGenerally">
			<li>Account verwalten</li>
		</div>
		<div class="expandGenerally">
			<ul class="subNav">
				<li class=changePassword>Passwort &auml;ndern</li>
				<li>Stellvertreter</li>
			</ul>
		</div>
	</ul>
</div>