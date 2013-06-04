var loginnameInput = $("#loginCellText");
var firstnameInput = $("#firstnameCellText");
var lastnameInput = $("#lastnameCellText");
var emailInput = $("#emailCellText");
var instituteSelect = $('#instituteSelect');
var rightsSelect = $('#rightsSelect');
var body = $("body");

var firstname = false;
var lastname = false;
var email = false;
var rights = false;
var institutes = false;

function checkValues() {
	var instituteSelectselectedIndex = $('#instituteSelect').multipleSelect('getSelects');
	var rightsSelectselectedIndex = $('#rightsSelect').multipleSelect('getSelects');
	var instituteLabel = $("#instituteLabel");
	var rightsLabel = $("#rightsLabel");
	
	instituteLabel.html(instituteSelectselectedIndex);
	rightsLabel.html(rightsSelectselectedIndex);
}

function initMultiSelect() {
	$("#rightsSelect").multipleSelect(); 
	$("#instituteSelect").multipleSelect(); 
}

function generateLoginname() {	
	var string = lastnameInput.val().toLowerCase();
	var string2 = firstnameInput.val().toLowerCase().charAt(0);
	var generatedString = string + string2;
	loginnameInput.val("");
	loginnameInput.val(generatedString);
	setBooleans();
}

body.click(function() {
	setBooleans();
});

function setBooleans() {
	firstname = firstnameInput.val().trim() == "";
	lastname = lastnameInput.val().trim() == "";
	email = emailInput.val().trim() == "";
	rights = rightsSelect.multipleSelect('getSelects')+"" == "";
	institutes = instituteSelect.multipleSelect('getSelects')+"" == "";
	if(!firstname&&!lastname&&!email&&!rights&&!institutes) {
		$('#saveButton').each(function() {
	        if ($(this).attr('disabled')) {
	            $(this).removeAttr('disabled');
	        }
	    });
	} else {
		$('#saveButton').each(function() {
	            $(this).attr({
	                'disabled': 'disabled'
	            });
	    });
	}
}