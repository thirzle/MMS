<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/default.css">
<div class="menuBox">
	<h1>Modulhandbuch</h1>

	<script>
		$(document).ready(function() {
			$(".headerNavCourse").click(function(e) {
				$(".expandCourse").toggleClass("expanded");
				$(".expandCourse").children("ul:first").slideToggle("300");
				e.stopPropagation();
			});
		});// $(document).ready
	</script>

	<ul id="nav">
		<div class="headerNavCourse" ><li>Ingenieurwissenschaften und Informatik</li></div>
		<div class="expandCourse">
			<ul id="subNav">
				<strong>Bachelor</strong>
				<li>Informatik</li>
				<li>Medieninformatik</li>
				<li>Informations-systemtechnik</li>
				<li>Software Engineering</li>
				<strong>Master</strong>
				<li>Informatik</li>
				<li>Medieninformatik</li>
				<li>Informations-systemtechnik</li>
			</ul>
		</div>
	</ul>

</div>