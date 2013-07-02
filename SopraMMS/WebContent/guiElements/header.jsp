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
	<span class="error" style="padding-left: 900px;"><navigation><a href="<%=sysconfig.Config.system_path.getValue() + sysconfig.Config.system_manual.getValue()%>"  target='_blank'>&rarr;&ensp;Hilfe</a></navigation></span>
</div>