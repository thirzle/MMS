
<div class="menuBox">
	<h1>Administrator</h1>



	<div class="adminMenu">

		<ul class="bullet1">
			<li style="border-bottom: none; "  class="menuEntry"
				id="userManagement">Benutzerverwaltung</li>
		</ul>


	</div>

</div>
<script type="text/javascript">
	$("#userManagement").children().hide();
</script>
<% Boolean reloadTable = session.getAttribute("task") == "reloadTable";
   System.out.println("(adminMenu): reloadTabel(): "+reloadTable);
   if(reloadTable) { 
  	out.println("<script type='text/javascript'>$('.menu').ready(function(){$('.contentBox').load('admin/adminMenu/userManagementContent.jsp');});</script>");
   } %>
