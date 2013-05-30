
<%@page import="java.util.List"%>
<%@page import="frontend.CourseMenu"%>


<script>
	$(document).ready(function() {
		$(".headerNavCourse").click(function(e) {
			$(".expandCourse").toggleClass("expanded");
			$(".expandCourse").children("ul:first").slideToggle("fast");
			e.stopPropagation();
		});
	});
</script>

<%
	if(session.getAttribute("coursepdfs")!=null){
%>
<script>
	$(document).ready(function() {
		$(".expandCourse").toggleClass("expanded");
		$(".expandCourse").children("ul:first").slideToggle(0);

	});
</script>
<%
	}
%>



<%
	CourseMenu cm = new CourseMenu();
	List<String> courses = cm.getCourses();
%>

<div class="menuBox">
	<h1>Modulhandbuch</h1>

	<ul class="nav">
		<div class="headerNavCourse">
			<li>Ingenieurwissenschaften und Informatik</li>
		</div>
		<div class="expandCourse">
			<ul class="subNav">
				<%
					for (int i = 0; i < courses.size(); i++) {
						session.setAttribute("content", "contentPdf");
						out.print("<li><a href=/SopraMMS/guiElements/home.jsp?contentPdf="
								+ courses.get(i) + ">" + courses.get(i) + "</a></li>");

					}
				%>
			</ul>
		</div>
	</ul>

</div>