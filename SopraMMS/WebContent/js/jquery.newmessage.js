var mailaddress = $("#mailaddress");
var mailto = $("#mailto");


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

function uebertrag(){
	var text = document.getElementById("mailaddress").options[document.getElementById("mailaddress").selectedIndex].value;
	document.getElementById("mailto").value = text;
}