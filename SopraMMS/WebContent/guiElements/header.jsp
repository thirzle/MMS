<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js">
</script>
<script>
$(document).ready(function(){
	$(".start").click(function() {
		window.location.replace("home.jsp?home=true");
	});
});
</script>
<a href="/SopraMMS/guiElements/home.jsp?home=true"><img src="${pageContext.request.contextPath}/images/textHeader.bmp" alt="Logo" align="left"></a>
<img src="${pageContext.request.contextPath}/images/imgHeader.bmp" alt="Logo" align="right">
<br>
<div class="headerNavigation" id="navigation">
	<span class="start"><navigation>Startseite</navigation></span>
	<span class="error" style="padding-left: 920px;"><navigation><a href="/SopraMMS/LoadManual" target='_blank'>Hilfe</a></navigation></span>
</div>