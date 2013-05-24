<%
String contentPage = request.getParameter("content")+"";


if(contentPage.equals("changedPwStatusPw12Wrong")||contentPage.equals("changedPwStatusOldPwWrong")){
	%>
	<script>
		$(document).ready(function() {
			$(".contentBox").load("/SopraMMS/guiElements/generally/changePassword.jsp");});
	</script>
			
<%
}
else if(false){
	%>
	<script>
		$(document).ready(function() {
			$(".contentBox").load("/SopraMMS/guiElements/impressum.jsp");});
	</script>
			
<%
}

%>