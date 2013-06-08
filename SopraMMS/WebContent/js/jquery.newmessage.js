var mailaddress = $("#mailaddress");
var mailto = $("#mailto");

/**
 * due to the input(Wert) of the user in the text area, the function searches the appropriate email address in the drop
 * 
 * @param Wert
 * 
 */
function Eingabe(Wert)
{
    for(i = 0; i < document.getElementById("mailaddress").options.length; i++)
    {
        if(Wert.substr(0, Wert.length).toLowerCase() == document.getElementById("mailaddress").options[i].value.substr(0, Wert.length).toLowerCase() && Wert.length != 0)
        {
            document.getElementById("mailaddress").options[i].selected = true;
            break;
        }
    }
}

/**
 * Transfers the content into the text field after selecting an email from the drop
 */
function uebertrag(){
	var text = document.getElementById("mailaddress").options[document.getElementById("mailaddress").selectedIndex].value;
	document.getElementById("mailto").value = text;
}