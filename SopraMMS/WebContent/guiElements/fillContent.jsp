<%
String contentPage = request.getParameter("content")+"";

if(contentPage.equals("changedPwStatusPw12Wrong")){
	%>
	<script>
		$(document).ready(function() {
			$(".contentBox").load("/SopraMMS/guiElements/generally/changePassword.jsp");});
	</script>
			
<%
}
else if(contentPage.equals("changedPwStatusOldOwWrong")){
	%>
	<script>
		$(document).ready(function() {
			$(".contentBox").load("/SopraMMS/guiElements/impressum.jsp");});
	</script>
			
<%
}

%>