var mailaddress = $("#mailaddress");
var mailto = $("#mailto");

/**
 * due to the input(value) of the user in the text area, the function searches the appropriate email address in the drop.
 * <p>
 * @param value			value is the input the user makes in the text area
 * 
 */
function Eingabe(value)
{
    for(i = 0; i < document.getElementById("mailaddress").options.length; i++)
    {
        if(value.substr(0, value.length).toLowerCase() == document.getElementById("mailaddress").options[i].value.substr(0, value.length).toLowerCase() && value.length != 0)
        {
            document.getElementById("mailaddress").options[i].selected = true;
            break;
        }
    }
}

/**
 * Transfers the content from the drop into the text field after selecting an email.
 * 
 */
function uebertrag(){
	var text = document.getElementById("mailaddress").options[document.getElementById("mailaddress").selectedIndex].value;
	document.getElementById("mailto").value = text;
}