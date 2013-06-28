function checkInput(except) {
	var inputs = $("input");
	doCheck(except, inputs);
	inputs.keyup(function(){doCheck(except, inputs);});
	inputs.change(function(){doCheck(except, inputs);});
}

function doCheck(except, inputs) {
	var isEmpty = false;
	for ( var i = 0; i < inputs.length; i++) {
		if (except == null || inputs[i].name != except.get(0).name) {
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
}