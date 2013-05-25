<%
String contentPage = session.getAttribute("content")+"";


if(contentPage.equals("changedPwStatusPw12Wrong")||contentPage.equals("changedPwStatusOldPwWrong")||contentPage.equals("changedPwStatusdone")){
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