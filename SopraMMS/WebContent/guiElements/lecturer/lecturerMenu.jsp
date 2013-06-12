<script>
$(document).ready(function() {
	$(".newcurriculumvitae").click(function(e) {
	$(".contentBox").load("/SopraMMS/guiElements/lecturer/curriculumvitae.jsp");
	});
});
</script>

<div class="menuBox">
	<h1>Dozent</h1>

	<ul class="nav">
		<div class="headerNavResume">
			<li>Lebenslauf</li>
		</div>
		<div class="expandResume">
			<ul class="subNav">
				<li class="newcurriculumvitae"><a>Lebenslauf
						anlegen</a></li>
				<li id="seecurriculumvitae"><a href="/SopraMMS/GetAllUsers?task=curr">Lebenslauf ansehen</a></li>
			</ul>
		</div>
	</ul>
</div>

