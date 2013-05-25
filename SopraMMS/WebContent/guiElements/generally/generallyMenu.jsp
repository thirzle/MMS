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
		$(document).ready(function(){
			$(".showRepresentative").click(function(e){
			$(".contentBox").load("/SopraMMS/guiElements/generally/showRepresentative.jsp");});});
</script>

<% 
if(request.getParameterValues("generallyMenu")!=null){ %>
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
				<li class=showRepresentative>Stellvertreter</li>
			</ul>
		</div>
	</ul>
</div>