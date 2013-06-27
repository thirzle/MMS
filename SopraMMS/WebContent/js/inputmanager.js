function checkInput(except) {
	var inputs = $("input[type=text]");
	inputs.change(function() {
		var isEmpty = false;
		for ( var i = 0; i < inputs.length; i++) {
			if (except != null && inputs[i].name != except.attr("name")) {
				if (inputs[i].value == "") {
					isEmpty = true;
				}
			}
		}

		if (!isEmpty) {
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
}
