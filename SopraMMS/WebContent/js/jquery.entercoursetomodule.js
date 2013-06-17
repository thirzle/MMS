
var obligatoryModulSelect = $('#obligatoryModulSelect');
var subjectSelect = $("#subjectSelect");
var voteModuleSelect = $('#voteModuleSelect');
var obligatoryLabel = $("#obligatoryLabel");
var voteLabel = $("#voteLabel");
var body = $("body");

function setValues() {	
	$("#obligatoryLabel").html($('#obligatoryModulSelect').multipleSelect('getSelects','value'));
	$("#voteLabel").html($('#voteModuleSelect').multipleSelect('getSelects','value'));
}

function initMultiSelect() {
	$("#obligatoryModulSelect").multipleSelect(); 
	$("#voteModuleSelect").multipleSelect(); 
}

function getSelectedIndex() {
	$("#subjectLabel").html($("#subject").val());
}
body.click(function() {
	setBooleans();
});
function noEmptyChoices() {
	return !($("#obligatoryLabel").val()=="" || $("#voteLabel").val()=="");
}
function noDoubles() {
	var valuesO = $("#obligatoryLabel").val();
	var valuesV = $("#voteLabel").val();
	for(var i=0; i< valuesO.length; i++) {
		for(var j=0; j<valuesV.length; j++) {
			if(valuesO.charAt(i) == valuesV.charAt(j)) {
				return false;
			}
		}
	}
	return true;
}

function setBooleans() {
	setValues();
	if(noDoubles()&&noEmptyChoices()) {
		$('#submitModule').each(function() {
	        if ($(this).attr('disabled')) {
	            $(this).removeAttr('disabled');
	        }
	    });
	} else {
		$('#submitModule').each(function() {
	            $(this).attr({
	                'disabled': 'disabled'
	            });
	    });
	}
}