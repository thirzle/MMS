
function loadDataIntoForm() {
	$("#loginCellText").val($("#tmpLoginname").html());
	$("#firstnameCellText").val($("#tmpFirstname").html());
	$("#lastnameCellText").val($("#tmpLastname").html());
	$("#emailCellText").val($("#tmpEmail").html());
}

function setValues() {
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

function convertToArray(string) {
	var tmp = new Array();
	if(string != null) {
		string = string.trim();
		for ( var i = 0; i < string.length; i++) {
			tmp[i] = string.charAt(i);
		}
	}
	return tmp;
}

function loadDataIntoMultiSelect() {
	var rightsText = $("#rightsSelectText").html();
	var instituteText = $("#instituteSelectText").html();
	var indexOfRights = convertToArray(rightsText);
	var indexOfInstitutes = convertToArray(instituteText);
	$('#rightsSelect').multipleSelect('setSelects', indexOfRights);
	$('#instituteSelect').multipleSelect('setSelects', indexOfInstitutes);
}