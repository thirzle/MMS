<script type="text/javascript">
$(document).ready(function() {
	$(".impressum").click(function(e) {
		$(".contentBox").load("/SopraMMS/guiElements/impressum.jsp");
	});
});
</script>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

	<div class="footerNavigation">
	
		<navigation><a class="impressum">Impressum</a></navigation>
	</div>
