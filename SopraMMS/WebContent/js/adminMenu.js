$(document).ready(function() {
	var UMOpend = false;
	var newUserCompleted = true;
	$.get("adminMenu/userManagement/userManagementMenu.jsp", function (data) {
		$("#userManagement").append(data);
    });
	//NAVIGATION
	/* Kuemmert sich darum, wenn ein Menupunkt geklickt wird
	 * fuegt in der Navigation entprechend das Wort ein.
	 * */
	$(".menuEntry").click(function() {
		if($(this).attr('id')!="userManagement") {
			/* schliesst das Untermenue der Benutzerverwaltung wenn 
			 * ein anderer Menuepunkt geklickt wird
			 */
			$("#userManagement").children().hide();
			$("#userManagement").css("background-color","");
			$("#tmpRow").hide();
			UMOpend = false;
			newUserCompleted = true;
		}
		$("#navigation").children().remove(".temp");
		if($(this).attr('class')=="menuEntry") {
			$("#navigation").append("<navigation class='temp' >"+">"+$(this).clone().children().remove().end().text()+"</navigation>");
		}
	});
	
	// MENU CLICK HANDLING
	
	// Wenn der Menureiter 'start' geclickt wurde
	$("#start").click(function() {
		$(".contentBox").load("adminMenu/"+$(this).attr('id')+"Content.jsp");
	});
	
	// Wenn der Menureiter 'Benutzerverwaltung' geclickt wurde
	
	$("#userManagement").click(function() {
		/*
		 * Wenn die Benutzerverwaltung noch nicht geoffnet ist, werden
		 * die Kinder angezeigt und somit die Benutzerverwaltung geoffnet
		 * und der Inhalt in die contentBox eingehangt.
		 */ 
		if(!UMOpend) {
			$(".contentBox").load("adminMenu/"+$(this).attr('id')+"Content.jsp");
			UMOpend = true;
			$(this).children().show();
			$(this).css("background-color","#FFFFFF");
		}
			/* NEW USER
			* Die Benutzerverwaltung ist offen. Wenn der Reiter 'Neuer Benutzer' geclickt
			* wird und noch es kein offener neuer Benutzer gibt, wird eine neue 
			* Tabellenzeile an die userTable angehangt.
			* Ansonsten wird einfach die schon vorhandene Zeile sichtbar gemacht.
			*
			*/
			$("#newUser").click(function() {
				$("#saveButton").show();
				if(newUserCompleted) {
					newUserCompleted = false;
					// neue Zeile wird an die Tabelle angehangt.
					$.get("adminMenu/userManagement/tmpRow.jsp", function (data) {
						$("#userTableBody").append(data);
		            });
				} else {
					//schon vorhandene Zeile wird sichtbar gemacht.
					$("#tmpRow").show();
				}
			});
			/* EDIT USER
			 * Der Reiter 'Benutzer bearbeiten' wird geclickt
			 * Davor muss eine Zeile in der Tabelle angewahlt sein.
			 * Damit kann nun diese Zeile geandert werden.
			 * 
			 */		
			$("#editUser").click(function() {
				$("#saveButton").hide();
				alert($(this).attr("id")+" was clicked");
				$("#tmpRow").hide();
				$("#controlBoard").children().remove();
			});
			/* DELETE USER
			 * Der Reiter 'Benutzer loschen' wird geclickt
			 * dazu muss vorher eine Zeile in der Tabelle aus-
			 * gewahlt sein, um diese Zeile / Benutzer zu loschen.
			 */
			$("#deleteUser").click(function() {
				$("#saveButton").hide();
				alert($(this).attr("id")+" was clicked");
				$("#tmpRow").hide();
				$("#controlBoard").children().remove();
			});
		
	});
	
	// Wenn der Menureiter 'Nachrichten' geclickt wurde
	$("#messages").click(function() {
		$(".contentBox").load("adminMenu/"+$(this).attr('id')+"Content.jsp");
	});
	
	// Wenn der Menureiter 'Modulverwaltung' geclickt wurde
	
	
	$("#modulManagement").click(function() {
		$(".contentBox").load("adminMenu/"+$(this).attr('id')+"Content.jsp");
	});
	
	
	// Wenn der Menureiter 'Logout' geclickt wurde
	
	
	$(".logout").click(function() {
		window.location.replace("../../guiElements/Login/logout.jsp");
	});
	
	
	
});