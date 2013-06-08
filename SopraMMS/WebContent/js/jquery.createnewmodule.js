var inputs = $(".mustNotBeEmpty");
var pras = $("input[name=0ContentD]");
inputs.attr("onkeyup","enableSubmitButton()");
pras.attr("onchange","enableSubmitButton()");

function checkNumber() {
	return (pras.val()>0);
}

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
	if(!fieldsEmpty() && checkNumber()) {
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