
<%@page import="java.util.List"%>
<%@page import="frontend.CourseMenu"%>


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
						out.print("<li> <a href=/SopraMMS/CourseSelection?contentPdf="
								+ courses.get(i) + ">" + courses.get(i) + "</a></li>");
					}
				%>
			</ul>
		</div>
	</ul>

</div>