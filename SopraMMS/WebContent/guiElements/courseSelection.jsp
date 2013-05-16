<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/default.css">
<div class="menuBox">
	<h1>Modulhandbuch</h1>


	<script type="text/javascript">
		/*
		 * F�gt den Listeneintr�gen Eventhandler und CSS Klassen hinzu,
		 * um die Men�punkte am Anfang zu schlie�en.
		 *
		 * menu: Referenz auf die Liste.
		 * data: String, der die Nummern aufgeklappter Men�punkte enth�lt.
		 */
		function treeMenu_init(menuEntry, data) {
			var array = new Array(0);
			if (data != null && data != "") {
				array = data.match(/\d+/g);
			}
			var items = menuEntry.getElementsByTagName("li");
			for ( var i = 0; i < items.length; i++) {
				items[i].onclick = treeMenu_handleClick;
				if (!treeMenu_contains(treeMenu_getClasses(items[i]),
						"treeMenu_opened")
						&& items[i].getElementsByTagName("ul").length
								+ items[i].getElementsByTagName("ol").length > 0) {
					var classes = treeMenu_getClasses(items[i]);
					if (array.length > 0 && array[0] == i) {
						classes.push("treeMenu_opened")
					} else {
						classes.push("treeMenu_closed")
					}
					items[i].className = classes.join(" ");
					if (array.length > 0 && array[0] == i) {
						array.shift();
					}
				}
			}
		}

		/*
		 * �ndert die Klasse eines angeclickten Listenelements, sodass
		 * ge�ffnete Men�punkte geschlossen und geschlossene ge�ffnet
		 * werden.
		 *
		 * event: Das Event Objekt, dass der Browser �bergibt.
		 */
		function treeMenu_handleClick(event) {
			if (event == null) { //Workaround f�r die fehlenden DOM Eigenschaften im IE
				event = window.event;
				event.currentTarget = event.srcElement;
				while (event.currentTarget.nodeName.toLowerCase() != "li") {
					event.currentTarget = event.currentTarget.parentNode;
				}
				event.cancelBubble = true;
			} else {
				event.stopPropagation();
			}
			var array = treeMenu_getClasses(event.currentTarget);
			for ( var i = 0; i < array.length; i++) {
				if (array[i] == "treeMenu_closed") {
					array[i] = "treeMenu_opened";
				} else if (array[i] == "treeMenu_opened") {
					array[i] = "treeMenu_closed"
				}
			}
			event.currentTarget.className = array.join(" ");
		}

		/*
		 * Gibt alle Klassen zur�ck, die einem HTML-Element zugeordnet sind.
		 *
		 * element: Das HTML-Element
		 * return: Die zugeordneten Klassen.
		 */
		function treeMenu_getClasses(element) {
			if (element.className) {
				return element.className.match(/[^ \t\n\r]+/g);
			} else {
				return new Array(0);
			}
		}

		/*
		 * �berpr�ft, ob ein Array ein bestimmtes Element enth�lt.
		 *
		 * array: Das Array
		 * element: Das Element
		 * return: true, wenn das Array das Element enth�lt.
		 */
		function treeMenu_contains(array, element) {
			for ( var i = 0; i < array.length; i++) {
				if (array[i] == element) {
					return true;
				}
			}
			return false;
		}

		/*
		 * Gibt einen String zur�ck, indem die Nummern aller ge�ffneten
		 * Men�punkte stehen.
		 *
		 * menu: Referenz auf die Liste
		 * return: Der String
		 */
		function treeMenu_store(menuEntry) {
			var result = new Array();
			;
			var items = menuEntry.getElementsByTagName("li");
			for ( var i = 0; i < items.length; i++) {
				if (treeMenu_contains(treeMenu_getClasses(items[i]),
						"treeMenu_opened")) {
					result.push(i);
				}
			}
			return result.join(" ");
		}
	</script>



	<body onload="treeMenu_init(document.getElementById('menuEntry'), '')">
		<ul id="menuEntry" class="bullet1">
			<li>Ingenieurwissenschaften und Informatik
				<ul class="bullet2">
					<strong>Bachelor</strong>
					<li>Informatik</li>
					<li>Medieninformatik</li>
					<li>Informations-systemtechnik</li>
					<li>Software Engineering</li>
					<strong>Master</strong>
					<li>Informatik</li>
					<li>Medieninformatik</li>
					<li>Informations-systemtechnik</li>
				</ul>
			</li>
		</ul>
	</body>

</div>