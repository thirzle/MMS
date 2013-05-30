<script>
	function popup(URL) {
		window.open(
						URL,
						"Neue Nachricht",
						"toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=1,width=910,height=700,left = 0,top = 0");
		console.log("4321");
	}
	$(document).ready(function() {
		popup('newMessage.jsp')
		console.log("1234");
	});
</script>
