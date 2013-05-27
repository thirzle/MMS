
<%@page import="java.util.List"%>
<%@page import="frontend.CourseMenu"%>

<script>
	$(document).ready(function() {
		$(".headerNavCourse").click(function(e) {
			$(".expandCourse").toggleClass("expanded");
			$(".expandCourse").children("ul:first").slideToggle("300");
			e.stopPropagation();
			$(".contentBox").load("/SopraMMS/guiElements/moduleSelection.jsp");
		});
	});

</script>

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
				<%for(int i=0; i<courses.size();i++){
	
					out.print("<li class='"+i+"'><a href=/SopraMMS/CourseContent?pdf="+courses.get(i)+">"+courses.get(i)+"</a></li>");
					
				} %>
			</ul>
		</div>
	</ul>

</div>