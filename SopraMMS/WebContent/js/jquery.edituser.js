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
generateLoginname();
function setValues() {
	alert("setValues called");
	var instituteSelectselectedIndex = $('#instituteSelect').multipleSelect('getSelects');
	var rightsSelectselectedIndex = $('#rightsSelect').multipleSelect('getSelects');
	var instituteLabel = $("#instituteLabel");
	var rightsLabel = $("#rightsLabel");
	
	instituteLabel.html(instituteSelectselectedIndex);
	rightsLabel.html(rightsSelectselectedIndex);
	alert("(edituser.js):instituteSelectselectedIndex:"+instituteSelectselectedIndex);
}

function initMultiSelect() {
	$("#rightsSelect").multipleSelect(); 
	$("#instituteSelect").multipleSelect(); 
}

function convertToArray(string) {
	var tmp = new Array();
	if(string != null) {
		var split = string.split(",");
		for ( var i = 0; i < split.length-1; i++) {
			tmp[i] = split[i];
		}
	}
	return tmp;
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
	rights = rightsSelect.multipleSelect('getSelects')+"" == ""; // geht aus irgend einem Grund nicht mehr wenn nichts ausgewählt ist.
	institutes = instituteSelect.multipleSelect('getSelects')+"" == ""; // Das womöglich auch nicht
	setValues();
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

function loadDataIntoMultiSelect() {
	var rightsText = $("#rightsSelectText").html();
	var instituteText = $("#instituteSelectText").html();
	var indexOfRights = convertToArray(rightsText);
	var indexOfInstitutes = convertToArray(instituteText);
	$('#rightsSelect').multipleSelect('setSelects', indexOfRights);
	$('#instituteSelect').multipleSelect('setSelects', indexOfInstitutes);
}

