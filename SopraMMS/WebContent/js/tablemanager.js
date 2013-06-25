
function showButton(input) {
	
	var checked = false;
	for(var i=0; i < $('input[type=radio]').length; i++) {
		if($('input[type=radio]')[i].checked) {
			checked = true;
		}
	}
	
	if (checked) {
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
	table.tablesorter({
		sortList : [ [ 0, 0 ], [ 2, 1 ] ],
		widgets : [ 'zebra' ]
	});
	
	$('input[type=radio]').change(function() {
		showButton($(this));
	});
	showButton($('input[type=radio]'));
}