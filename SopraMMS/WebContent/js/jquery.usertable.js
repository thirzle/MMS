$(function() {
	var id = $("#0").attr("id");
	alert("tr id="+id);
	$("#userTable").tablesorter({
		sortList : [ [ 0, 0 ], [ 2, 1 ] ],
		widgets : [ 'zebra' ]
	});
});