/**
 * checks whether a radiobutton is checked or not and returns the result.
 * 
 * @returns {Boolean}
 */
function checkRadioButton() {
	var checked = false;
	var radios = $('input[type=radio]');
	for(var i=0; i < radios.length; i++) {
		if(radios[i].checked) {
			checked = true;
		}
	}
	return checked;
}
/**
 * enables all buttons with type of 'submit' if a single radio button is checked or half of all radio buttons
 */
function showButton() {
	var radios = $('input[type=radio]');
	var radiocount = radios.length;
	if ((checkRadioButton() && radiocount < 20) || checkRadioButtons() || checkCheckBox()) {
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
/**
 * registers a tablesorter to the given parameter and a onchange event listener to all radio buttons.
 * 
 * @param table
 */
function manageTable(table) {
	var radios = $('input[type=radio]');
	var checkboxes = $('input[type=checkbox]');
	if(table != null) {
		setTHeads();
		table.tablesorter({
			sortList : [ [ 1, 0 ], [ 2, 0 ] ],
			widgets : [ 'zebra' ]
		});
	}
	radios.change(function() {
		showButton();
	});
	checkboxes.change(function() {
		showButton();
	});
	showButton();
}
/**
 * checks whether a checkbox is checked or not and returns the result.
 * 
 * @returns {Boolean}
 */
function checkCheckBox() {
	var checked = false;
	for(var i=0; i < $('input[type=checkbox]').length; i++) {
		if($('input[type=checkbox]')[i].checked) {
			checked = true;
		}
	}
	return checked;
}
/**
 * checks whether half of all radio buttons is checked or not and returns result.
 * 
 * @returns {Boolean}
 */
function checkRadioButtons() {
	var needToBeChecked = $('input[type=radio]').length/2;
	var areChecked = 0;
	for(var i=0; i<$('input[type=radio]').length; i++) {
		if($('input[type=radio]')[i].checked) {
			areChecked++;
		}
	}
	return (needToBeChecked == areChecked && needToBeChecked != 0);
}
/**
 * sets class attribute of table head depending on its text content length to xs, s, m, l, xl or xxl.
 * that class attribute is used of the cascade stylesheet 'style.css' which sets th width accordingly to the class attribute.
 *  
 */
function setTHeads() {
	var theads = $("th");
	var contentLength = 0;
	for(var i=0;i<theads.length;i++){
		contentLength = theads[i].textContent.length;
		var thClass = theads[i].getAttribute("class");
		if(thClass == null) {
			thClass = "null";
		}
		if(thClass.toLowerCase().indexOf("final") === -1) {
		
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
}