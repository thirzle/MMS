<script>
		$(document).ready(function() {
			$(".headerNavGenerally").click(function(e) {
				$(".expandGenerally").toggleClass("expanded");
				$(".expandGenerally").children("ul:first").slideToggle("300");
				e.stopPropagation();
			});
		});
		
		$(document).ready(function() {
			$(".changePassword").click(function(e) {
			$(".contentBox").load("/SopraMMS/guiElements/generally/changePassword.jsp");});});
</script>

<% 
if(session.getAttribute("generallyMenu")!=null){
	session.setAttribute("generallyMenu",null); %>
	<script>
		$(document).ready(function() {
				$(".expandGenerally").toggleClass("expanded");
				$(".expandGenerally").children("ul:first").slideToggle("300");
				e.stopPropagation();
		});
	</script><%} %>
	


<div class="menuBox">
	<h1>Allgemein</h1>
	
	<ul class="nav">
		<div class="headerNavGenerally">
			<li>Account verwalten</li>
		</div>
		<div class="expandGenerally">
			<ul class="subNav">
				<li class=changePassword>Passwort &auml;ndern</li>
				<form id="showR" action="/SopraMMS/ShowRepresentative" method="get" >
					<li class=showRepresentative onclick="showR.submit()">Stellvertreter</li>
				</form>
			</ul>
		</div>
	</ul>
</div>