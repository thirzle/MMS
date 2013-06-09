var currvitae = $("#currvitae");
var text1 = $("text1");
var text2 = $("text2");

function curravail() {
	var currurl = document.getElementById("currvitae").textContent;
	alert(document.getElementById("currvitae").textContent);
	if (currurl == null) {
		document.getElementById("text1").textContent = "Es ist kein Lebenslauf vorhanden, bitte legen Sie einen Lebenslauf an";
		document.getElementById("text2").textContent = "";
	} else {
		window.location.href = currurl;
	}
}