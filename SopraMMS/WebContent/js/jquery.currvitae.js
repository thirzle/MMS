var currvitae = $("#currvitae");
var text1 = $("text1");
var text2 = $("text2");

/**
 * Checks if an URL is in the database. Opens it or displays an error message.
 */
function curravail() {
	var currurl = document.getElementById("currvitae").textContent;
	if (currurl == "") {
		document.getElementById("text1").textContent = "Es ist kein Lebenslauf vorhanden, bitte legen Sie einen Lebenslauf an.";
		document.getElementById("text2").textContent = "";
		document.getElementById("text1").textContent = currurl;
	} else {
		window.location.href = currurl;
	}
}