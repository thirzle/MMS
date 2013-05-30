<script>
		$(document).ready(function() {
			$(".headerNavGenerally").click(function(e) {				
				$(this).next(".expandGenerally").toggleClass("expanded");
				$(this).next(".expandGenerally").children("ul:first").slideToggle("fast");
				e.stopPropagation();
			});
		});
		
		$(document).ready(function() {
			$(".changePassword").click(function(e) {
			$(".contentBox").load("/SopraMMS/guiElements/generally/changePassword.jsp");});});
		//Lisa
		$(document).ready(function() {
			$(".newMsg").click(function(e) {
			$(".contentBox").load("/SopraMMS/guiElements/generally/newMessageContent.jsp");});});
</script>

<% 
if(session.getAttribute("generallyMenu")!=null){
	session.setAttribute("generallyMenu",null); %>
	<script>
		$(document).ready(function() {
				$(".expandGenerally").toggleClass("expanded");
				$(".expandGenerally").children("ul:first").slideToggle(0);
				e.stopPropagation();
		});
	</script><%}
		session.removeAttribute("generallyMenu"); 
%>
	


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
		<div class="headerNavGenerally">
			<li>Nachrichten</li>
		</div>
		<div class="expandGenerally">
			<ul class="subNav">
				<li class=newMsg>Neue Nachricht</li>
			</ul>
		</div>
	</ul>
</div>