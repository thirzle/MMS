/**
 * The inputmanager verifies all input tags on the page for null values. If all input fields are free 
 * from null values, submit buttons have their disabled attribute removed.
 * the null value check is trigger through a onchange and onkeyup event of the input field. 
 * <p>
 * 
 * @param except optional - inputmanager checks all inputs on the page except the one given in the parameter.
 */
function checkInput(except) {
	$('html, body').animate({ scrollTop: 0 }, 'slow');
	var inputs = $("input");
	doCheck(except, inputs);
	inputs.keyup(function(){doCheck(except, inputs);});
	inputs.change(function(){doCheck(except, inputs);});
}
/**
 * this method is called from another method. Initially on page load the inputmanager checks all available input fields for null values.
 * next is the registration of the onkeyup and onchange event listener to all input fields.
 * Lastly the 'disabled' attribute is accordingly set or removed.
 * <p>
 * 
 * 
 * @param except
 * @param inputs
 */
function doCheck(except, inputs) {
	var isEmpty = false;
	for ( var i = 0; i < inputs.length; i++) {
		if (except == null || inputs[i].name != except.get(0).name) {
			if (inputs[i].value == "") {
				isEmpty = true;
			}
		}
	}
	if (!isEmpty) {
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