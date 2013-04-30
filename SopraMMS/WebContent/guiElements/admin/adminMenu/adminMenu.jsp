<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script>
$(document).ready(function() {
	$(".menuEntry").click(function() {
		$(".contentBox").load("adminMenu/"+$(this).attr('id')+"Content.jsp");
		$("#navigation").children().remove(".temp");
		$("#navigation").append("<navigation class='temp' >"+">"+$(this).text()+"</navigation>");
	});
	$(".logout").click(function() {
		window.location.replace("../../guiElements/Login/logout.jsp");
	});
});
</script>
<div class="adminMenu">
	<p class="menuEntry" id="start">Start</p>
	<p class="menuEntry" id="userManagement">Benutzerverwaltung</p>
	<p class="menuEntry" id="messages">Nachrichten</p>
	<p class="menuEntry" id="modulManagement">Modulverwaltung</p>
	<p class="logout">Logout</p>
</div>
