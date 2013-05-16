$(document).ready(function() {
	var UMOpend = false;
	var newUserCompleted = true;
	$.get("adminMenu/userManagement/userManagementMenu.jsp", function (data) {
		$("#userManagement").append(data);
    });
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
	$("#start").click(function() {
		$(".contentBox").load("adminMenu/"+$(this).attr('id')+"Content.jsp");
	});
	
	$("#userManagement").click(function() {
		if(!UMOpend) {
			$(".contentBox").load("adminMenu/"+$(this).attr('id')+"Content.jsp");
			UMOpend = true;
			$(this).children().show();
			$(this).css("background-color","#FFFFFF");
		} //UMOpend
			$("#newUser").click(function() {
				$("#controlBoard").load("adminMenu/userManagement/addUser.jsp");
				if(newUserCompleted) {
					newUserCompleted = false;
					$.get("adminMenu/userManagement/tmpRow.jsp", function (data) {
						$("#userTableBody").append(data);
	                });
				} else {
					$("#tmpRow").show();
				}
			});
						
			$("#editUser").click(function() {
				alert($(this).attr("id")+" was clicked");
				$("#tmpRow").hide();
				$("#controlBoard").children().remove();
			});
			$("#deleteUser").click(function() {
				alert($(this).attr("id")+" was clicked");
				$("#tmpRow").hide();
				$("#controlBoard").children().remove();
			});
	});
	
	$("#messages").click(function() {
		$(".contentBox").load("adminMenu/"+$(this).attr('id')+"Content.jsp");
	});
	$("#modulManagement").click(function() {
		$(".contentBox").load("adminMenu/"+$(this).attr('id')+"Content.jsp");
	});
	$(".logout").click(function() {
		window.location.replace("../../guiElements/Login/logout.jsp");
	});
	$("#userManagement").hover(
		function() {
			//mouseenter
		}, 
		function() {
			//mouseleave
		});
	});