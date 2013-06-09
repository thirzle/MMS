<script>
$(document).ready(function() {
	$(".newcurriculumvitae").click(function(e) {
	$(".contentBox").load("/SopraMMS/guiElements/lecturer/curriculumvitae.jsp");
	});
});
$(document).ready(function() {
	$(".seecurriculumvitae").click(function(e) {
	$(".contentBox").load("/SopraMMS/guiElements/lecturer/seecurriculumvitae.jsp");
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
				<li class="seecurriculumvitae"><a>Lebenslauf
						ansehen</a></li>
			</ul>
		</div>
	</ul>
</div>

