//	Ingenieurwissenschaften und Informatik
$(document).ready(function() {
	$(".headerNavCourse").click(function(e) {
		var exp = sessionStorage.getItem('expCourse');
		if (exp == 'true') {
			$(".expandCourse").toggleClass("expanded", false);
			sessionStorage.setItem('expCourse', 'false');
		} else {
			$(".expandCourse").toggleClass("expanded", true);
			sessionStorage.setItem('expCourse', 'true');
		}
		$(".expandCourse").children("ul:first").slideToggle("fast");
	});
});

$(document).ready(function() {
	var exp = sessionStorage.getItem('expCourse');
	if (exp == 'true') {
		$(".expandCourse").toggleClass("expanded", false);
		$(".expandCourse").children("ul:first").slideToggle(0);
	} else {
		$(".expandCourse").toggleClass("expanded", true);

	}
});

// Benutzerverwaltung
//TODO
$(document).ready(function() {
	$(".headerNavAdminAccount").click(function(e) {
		var exp = sessionStorage.getItem('expAdminAccount');
		if (exp == 'true') {
			$(".expandAdminAccount").toggleClass("expanded", false);
			sessionStorage.setItem('expAdminAccount', 'false');
		} else {
			$(".expandAdminAccount").toggleClass("expanded", true);
			sessionStorage.setItem('expAdminAccount', 'true');
		}
		$(".expandAdminAccount").children("ul:first").slideToggle("fast");
	});
});

$(document).ready(function() {
	var exp = sessionStorage.getItem('expAdminAccount');
	if (exp == 'true') {
		$(".expandAdminAccount").toggleClass("expanded", false);
		$(".expandAdminAccount").children("ul:first").slideToggle(0);
	} else {
		$(".expandAdminAccount").toggleClass("expanded", true);

	}
});

// Modulhandbuch
$(document).ready(function() {
	$(".headerNavAdminModule").click(function(e) {
		var exp = sessionStorage.getItem('expAdminModule');
		if (exp == 'true') {
			$(".expandAdminModule").toggleClass("expanded", false);
			sessionStorage.setItem('expAdminModule', 'false');
		} else {
			$(".expandAdminModule").toggleClass("expanded", true);
			sessionStorage.setItem('expAdminModule', 'true');
		}
		$(".expandAdminModule").children("ul:first").slideToggle("fast");
	});
});

$(document).ready(function() {
	var exp = sessionStorage.getItem('expAdminModule');
	if (exp == 'true') {
		$(".expandAdminModule").toggleClass("expanded", false);
		$(".expandAdminModule").children("ul:first").slideToggle(0);
	} else {
		$(".expandAdminModule").toggleClass("expanded", true);

	}
});

// Neuigkeiten 
$(document).ready(function() {
	$(".headerNavAdminNews").click(function(e) {
		var exp = sessionStorage.getItem('expAdminNews');
		if (exp == 'true') {
			$(".expandAdminNews").toggleClass("expanded", false);
			sessionStorage.setItem('expAdminNews', 'false');
		} else {
			$(".expandAdminNews").toggleClass("expanded", true);
			sessionStorage.setItem('expAdminNews', 'true');
		}
		$(".expandAdminNews").children("ul:first").slideToggle("fast");
	});
});

$(document).ready(function() {
	var exp = sessionStorage.getItem('expAdminNews');
	if (exp == 'true') {
		$(".expandAdminNews").toggleClass("expanded", false);
		$(".expandAdminNews").children("ul:first").slideToggle(0);
	} else {
		$(".expandAdminNews").toggleClass("expanded", true);

	}
});


// Modulhandbuchverwaltung
$(document).ready(function() {
	$(".headerNavModul").click(function(e) {
		var exp = sessionStorage.getItem('expModul');
		if (exp == 'true') {
			$(".expandModul").toggleClass("expanded", false);
			sessionStorage.setItem('expModul', 'false');
		} else {
			$(".expandModul").toggleClass("expanded", true);
			sessionStorage.setItem('expModul', 'true');
		}
		$(".expandModul").children("ul:first").slideToggle("fast");
	});
});

$(document).ready(function() {
	var exp = sessionStorage.getItem('expModul');
	if (exp == 'true') {
		$(".expandModul").toggleClass("expanded", false);
		$(".expandModul").children("ul:first").slideToggle(0);
	} else {
		$(".expandModul").toggleClass("expanded", true);
	}
});

//Account verwalten
$(document).ready(function() {
	$(".headerNavAccount").click(function(e) {
		var exp = sessionStorage.getItem('expAccount');
		if (exp == 'true') {
			$(".expandAccount").toggleClass("expanded", false);
			sessionStorage.setItem('expAccount', 'false');
		} else {
			$(".expandAccount").toggleClass("expanded", true);
			sessionStorage.setItem('expAccount', 'true');
		}
		$(".expandAccount").children("ul:first").slideToggle("fast");
	});
});

$(document).ready(function() {
	var exp = sessionStorage.getItem('expAccount');
	if (exp == 'true') {
		$(".expandAccount").toggleClass("expanded", false);
		$(".expandAccount").children("ul:first").slideToggle(0);
	} else {
		$(".expandAccount").toggleClass("expanded", true);
	}
});


// Nachrichten
$(document).ready(function() {
	$(".headerNavMessages").click(function(e) {
		var exp = sessionStorage.getItem('expMessages');
		if (exp == 'true') {
			$(".expandMessages").toggleClass("expanded", false);
			sessionStorage.setItem('expMessages', 'false');
		} else {
			$(".expandMessages").toggleClass("expanded", true);
			sessionStorage.setItem('expMessages', 'true');
		}
		$(".expandMessages").children("ul:first").slideToggle("fast");
	});
});

$(document).ready(function() {
	var exp = sessionStorage.getItem('expMessages');
	if (exp == 'true') {
		$(".expandMessages").toggleClass("expanded", false);
		$(".expandMessages").children("ul:first").slideToggle(0);
	} else {
		$(".expandMessages").toggleClass("expanded", true);
	}
});
// Lebenslauf
$(document).ready(function() {
	$(".headerNavResume").click(function(e) {
		var exp = sessionStorage.getItem('expResume');
		if (exp == 'true') {
			$(".expandResume").toggleClass("expanded", false);
			sessionStorage.setItem('expResume', 'false');
		} else {
			$(".expandResume").toggleClass("expanded", true);
			sessionStorage.setItem('expResume', 'true');
		}
		$(".expandResume").children("ul:first").slideToggle("fast");
	});
});

$(document).ready(function() {
	var exp = sessionStorage.getItem('expResume');
	if (exp == 'true') {
		$(".expandResume").toggleClass("expanded", false);
		$(".expandResume").children("ul:first").slideToggle(0);
	} else {
		$(".expandResume").toggleClass("expanded", true);
	}
});
// Modulverantwortlicher
$(document).ready(function() {
	$(".headerNavEditorModule").click(function(e) {
		var exp = sessionStorage.getItem('expEditorModule');
		if (exp == 'true') {
			$(".expandEditorModule").toggleClass("expanded", false);
			sessionStorage.setItem('expEditorModule', 'false');
		} else {
			$(".expandEditorModule").toggleClass("expanded", true);
			sessionStorage.setItem('expEditorModule', 'true');
		}
		$(".expandEditorModule").children("ul:first").slideToggle("fast");
	});
});

$(document).ready(function() {
	var exp = sessionStorage.getItem('expEditorModule');
	if (exp == 'true') {
		$(".expandEditorModule").toggleClass("expanded", false);
		$(".expandEditorModule").children("ul:first").slideToggle(0);
	} else {
		$(".expandEditorModule").toggleClass("expanded", true);
	}
});
// Koordinator
$(document).ready(function() {
	$(".headerNavCoordinatorModule").click(function(e) {
		var exp = sessionStorage.getItem('expCoordModule');
		if (exp == 'true') {
			$(".expandCoordinatorModule").toggleClass("expanded", false);
			sessionStorage.setItem('expCoordModule', 'false');
		} else {
			$(".expandCoordinatorModule").toggleClass("expanded", true);
			sessionStorage.setItem('expCoordModule', 'true');
		}
		$(".expandCoordinatorModule").children("ul:first").slideToggle("fast");
	});
});

$(document).ready(function() {
	var exp = sessionStorage.getItem('expCoordModule');
	if (exp == 'true') {
		$(".expandCoordinatorModule").toggleClass("expanded", false);
		$(".expandCoordinatorModule").children("ul:first").slideToggle(0);
	} else {
		$(".expandCoordinatorModule").toggleClass("expanded", true);
	}
});

//Verwaltung
$(document).ready(function() {
	$(".headerNavCoordinatorAdministration").click(function(e) {
		var exp = sessionStorage.getItem('expCoordAdministration');
		if (exp == 'true') {
			$(".expandCoordinatorAdministration").toggleClass("expanded", false);
			sessionStorage.setItem('expCoordAdministration', 'false');
		} else {
			$(".expandCoordinatorAdministration").toggleClass("expanded", true);
			sessionStorage.setItem('expCoordAdministration', 'true');
		}
		$(".expandCoordinatorAdministration").children("ul:first").slideToggle("fast");
	});
});

$(document).ready(function() {
	var exp = sessionStorage.getItem('expCoordAdministration');
	if (exp == 'true') {
		$(".expandCoordinatorAdministration").toggleClass("expanded", false);
		$(".expandCoordinatorAdministration").children("ul:first").slideToggle(0);
	} else {
		$(".expandCoordinatorAdministration").toggleClass("expanded", true);
	}
});
//Dezernat 2
$(document).ready(function() {
	$(".headerNavDez2Module").click(function(e) {
		var exp = sessionStorage.getItem('expandDez2Module');
		if (exp == 'true') {
			$(".expandDez2Module").toggleClass("expanded", false);
			sessionStorage.setItem('expandDez2Module', 'false');
		} else {
			$(".expandDez2Module").toggleClass("expanded", true);
			sessionStorage.setItem('expandDez2Module', 'true');
		}
		$(".expandDez2Module").children("ul:first").slideToggle("fast");
	});
});

$(document).ready(function() {
	var exp = sessionStorage.getItem('expandDez2Module');
	if (exp == 'true') {
		$(".expandDez2Module").toggleClass("expanded", false);
		$(".expandDez2Module").children("ul:first").slideToggle(0);
	} else {
		$(".expandDez2Module").toggleClass("expanded", true);
	}
});

