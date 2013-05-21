<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/default.css">
<div class="menuBox">
	<h1>Allgemein</h1>

	<script>
		$(document).ready(function() {
			$(".headerNavGenerally").click(function(e) {
				$(".expandGenerally").toggleClass("expanded");
				$(".expandGenerally").children("ul:first").slideToggle("300");
				e.stopPropagation();
			});
		}); //$(document).ready
	</script>


	<ul id="nav">
		<div class="headerNavGenerally" ><li>Account verwalten</li></div>
		<div class="expandGenerally">
			<ul id="subNav">
				<li>Passwort &auml;ndern</li>
				<li>Stellvertreter</li>
			</ul>
		</div>
	</ul>
</div>