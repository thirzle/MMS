$(document).ready(function() {
$("input[type=text]").change(function() {
		if (!($("input[type=text]")[0].value == "" || $("input[type=text]")[1].value == "")) {
			$('button[type=submit]').each(function() {
				if ($(this).attr('disabled')) {
					$(this).removeAttr('disabled');
				}
			});
		} else {
			$('button[type=submit]').each(function() {
				$(this).attr({
					'disabled' : 'disabled'
				});
			});
		}
	});
});