<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js">
</script>
<script>
$(document).ready(function(){
	$(".start").click(function() {
		window.location.replace("adminHome.jsp");
	});
});
</script>
<a href="/SopraMMS/welcome.jsp"><img src="${pageContext.request.contextPath}/images/textHeader.bmp" alt="Logo" align="left"></a>
<img src="${pageContext.request.contextPath}/images/imgHeader.bmp" alt="Logo" align="right">
<br>
<div class="headerNavigation" id="navigation">
	<navigation class="start">Startseite</navigation>
</div>