function fillTable(object) {
	var rows = object.getElementsByTagName("loginname").length;
	var cols = 7;
	var body = document.getElementById("userTableBody");
	for ( var i = 0; i < rows; i++) {
		var tr = document.createElement("tr");
		for ( var j = 0; j < cols; j++) {
			var td = document.createElement("td");
			td.innerHTML = object.firstChild.childNodes[j].firstChild.nodeValue;
			tr.appendChild(td);
		}
		body.appendChild(tr);
	}
}

function loadUsers() {
	
	var xmlhttp = null;
	if (window.XMLHttpRequest) {
	      xmlhttp = new XMLHttpRequest();
	} else if (window.ActiveXObject) {
	      xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4) {
			//xmlhttp.responseXML.
			fillTable(xmlhttp.responseXML);
		}
	};
	xmlhttp.open("GET", "/SopraMMS/Servlet/MMSServlet.java", true);
	xmlhttp.send(null);
}