$(function() {
	$("#userTable").tablesorter({
		sortList : [ [ 0, 0 ], [ 2, 1 ] ],
		widgets : [ 'zebra' ]
	});
});