
function checkRadioButton() {
	var checked = false;
	for(var i=0; i < $('input[type=radio]').length; i++) {
		if($('input[type=radio]')[i].checked) {
			checked = true;
		}
	}
	return checked;
}

function showButton() {
	var radiocount = $('input[type=radio]').length;
	if ((checkRadioButton() && radiocount < 20) || checkRadioButtons()) {
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

function manageTable(table) {
	if(table != null) {
		table.tablesorter({
			sortList : [ [ 0, 0 ], [ 2, 1 ] ],
			widgets : [ 'zebra' ]
		});
	}
	$('input[type=radio]').change(function() {
		showButton();
	});
	showButton();
}

function checkRadioButtons() {
	var needToBeChecked = $('input[type=radio]').length/2;
	var areChecked = 0;
	for(var i=0; i<$('input[type=radio]').length; i++) {
		if($('input[type=radio]')[i].checked) {
			areChecked++;
		}
	}
	return (needToBeChecked == areChecked);
}