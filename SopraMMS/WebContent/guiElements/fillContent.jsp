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

else if(contentPage.equals("appointRepresentative")){
	%>
	<script>
		$(document).ready(function() {
			$(".contentBox").load("/SopraMMS/guiElements/generally/appointRepresentative.jsp");});
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
else if(contentPage.equals("createdRepresentative")){
	%>
	<script>
	$(document).ready(function(){
	$(".contentBox").load("/SopraMMS/guiElements/generally/createdRepresentative.jsp");});
	</script>
	<%
} else if(contentPage.equals("user")) {
	%>
	<script >
	$(document).ready(function() {
		$(".contentBox").load("/SopraMMS/guiElements/admin/adminMenu/userManagementContent.jsp");
		$(".expandAdministration").toggleClass("expanded");
		$(".expandAdministration").children("ul:first").slideToggle("fast");
		e.stopPropagation();
	});
	</script>
	<%
	session.setAttribute("content", "home");
} else if(contentPage.equals("home")) {
	%>
	<script >
	$(document).ready(function() {
		$(".contentBox").load("/SopraMMS/guiElements/homeContent.jsp");
	});
	</script>
	<%
} else if(contentPage.equals("addUser")) {
	%>
	<script >
	$(document).ready(function() {
		$(".contentBox").load("/SopraMMS/guiElements/admin/adminMenu/userManagementContent.jsp");
		
	});
	</script>
	<%
}

%>