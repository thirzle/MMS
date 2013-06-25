
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
		setTHeads();
		table.tablesorter({
			sortList : [ [ 1, 0 ], [ 2, 0 ] ],
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

function setTHeads() {
	var theads = $("th");
	var contentLength = 0;
	for(var i=0;i<theads.length;i++){
		contentLength = theads[i].textContent.length;
		
		if(contentLength == 0) {
			
			theads[i].setAttribute("class","xs header");
		} else if(contentLength < 6) {
			
			theads[i].setAttribute("class","s header");
		} else if(contentLength < 9) {
			
			theads[i].setAttribute("class","m header");
		} else if(contentLength < 13) {
			
			theads[i].setAttribute("class","l header");
		} else if(contentLength < 17) {
			
			theads[i].setAttribute("class","xl header");
		} else {
			
			theads[i].setAttribute("class","xxl header");
		}
	}
}