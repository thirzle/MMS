var inputs = $("input");
inputs.attr("oninput","enableSubmitButton()");

function fieldsEmpty() {
	var isEmpty = false;
	for(var i=0;i<inputs.length;i++) {
		if(inputs[i].value.trim() == "") {
			isEmpty = true;
		}
	}
	return isEmpty;
}

function enableSubmitButton() {
	alert("enableSubmitButton");
	if(!fieldsEmpty()) {
		$('#submitModulButton').each(function() {
	        if ($(this).attr('disabled')) {
	            $(this).removeAttr('disabled');
	        }
	    });
	} else {
		$('#submitModulButton').each(function() {
	            $(this).attr({
	                'disabled': 'disabled'
	            });
	    });
	}
}