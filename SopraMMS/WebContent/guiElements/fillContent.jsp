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
else if(contentPage.equals("applyRepresentative")){
	%>
	<script>
		$(document).ready(function() {
			$(".contentBox").load("/SopraMMS/guiElements/generally/applyRepresentative.jsp");});
	</script>
			
<%
}
else if(contentPage.equals("removeRepresentative")){
	%>
	<script>
	$(document).ready(function(){
		$(".contentBox").load("/SopraMMS/guiElements/generally/removeRepresentative.jsp");});
	</script>
	<%
}
else if(contentPage.equals("showRepresentative")){
	%>
	<script>
		$(document).ready(function(){
		$(".contentBox").load("/SopraMMS/guiElements/generally/showRepresentative.jsp");});
	</script>
	<%
}
else if(contentPage.equals("noRepresentative")){
	%>
	<script>
		$(document).ready(function(){
		$(".contentBox").load("/SopraMMS/guiElements/generally/showNoRepresentative.jsp");});
	</script>
	<%
}

%>