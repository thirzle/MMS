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

function initMultiSelect() {
	$("#rightsSelect").multipleSelect(); 
	$("#instituteSelect").multipleSelect(); 
}

function loadDataIntoMultiSelect() {
	var rightsText = $("#rightsSelectText").html();
	var instituteText = $("#instituteSelectText").html();
	var indexOfRights = convertToArray(rightsText);
	var indexOfInstitutes = convertToArray(instituteText);
	$('#rightsSelect').multipleSelect('setSelects', indexOfRights);
	$('#instituteSelect').multipleSelect('setSelects', indexOfInstitutes);
}

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