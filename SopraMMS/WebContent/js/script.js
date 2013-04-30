function fillTable(object) {
	alert("fillTable");
	var rows = object.getElementsByTagName("loginname").length;
	var cols = 7;
	var body = document.getElementById("userTableBody");
	for ( var i = 0; i < rows; i++) {
		var tr = document.createElement("tr");
		for ( var j = 0; j < cols; j++) {
			var td = document.createElement("td");
			td.innerHTML = "cool";
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
			alert(xmlhttp.responseText);
			//fillTable(xmlhttp.responseXML);
		}
	};
	xhr.open("GET", "/SopraMMS/Servlet/MMSServlet.java", true);
	xhr.send(null);
}