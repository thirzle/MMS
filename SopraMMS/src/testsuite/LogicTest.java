package testsuite;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.pdfbox.exceptions.COSVisitorException;

import management.*;
import model.ModuleDBController;
import pdfcreator.*;

public class LogicTest {

	private static ModuleDBController connection;


	public static void main(String[] args) {
		System.out.println("Starting test...");

		// creating a connection to the database
		connection = new ModuleDBController();

		// init test #1
		//module_load_test();

		// init test #2
		module_creation_test();

		System.out.println("Test ended...");
	}


	private static void module_load_test() {
		System.out.println("###########################################");
		System.out.println("# Logic Test # 1");
		System.out.println("# 'Load all Modules' ");
		System.out.println("#------------------------------------------");

		List<Module> modules = connection.getModules();

		System.out.println("# " + modules.size() + " Modules found in DB");

		for (int i = 0; i < modules.size(); i++) {
			System.out.println("#");
			Module m = modules.get(i);
			System.out.println("# Name: " + m.getName());
			System.out.println("# Creation Date: " + m.getCreationDate());
			System.out.println("# Modification Date: "
					+ m.getModificationDate());
			if (m.isApproved()) {
				System.out.println("# Approved: TRUE");
			} else {
				System.out.println("# Approved: FALSE");
			}
			System.out.println("# Module ID: " + m.getModuleID());
			System.out.println("# Institute ID: " + m.getInstituteID());

			// TODO: List all Entries of given module m

		}
		System.out.println("###########################################");
	}


	private static void module_creation_test() {
		System.out.println("###########################################");
		System.out.println("# Logic Test # 2");
		System.out.println("# 'Create a Module with several entries");
		System.out.println("# and stores it into a PDF file' ");
		System.out.println("#------------------------------------------");
		
		
		//CREATE ULTRA HUGE MODULE to TEST PDF CREATION....

		Module test_module = new Module(1337, "Analysis I für Ingenieure und Informatiker", new Date(),
				new Date(), false, "Institut007");
		
		

		TextualEntry test_textualentry1 = new TextualEntry(42, "02:44:35",
				false, false, false, 1 ,"Englischer Titel: ",
				"Analysis I for Computer Scientists");

		TextualEntry test_textualentry2 = new TextualEntry(42, "02:44:35",
				false, false, false, 1,"Turnus/Dauer: ",
				"jedes Sommersemester / 1 Semester");
		
		TextualEntry test_textualentry3 = new TextualEntry(42, "02:44:35",
			false, false, false, 1 ,"Sprache: ",
			"Deutsch");
		
		TextualEntry test_textualentry4 = new TextualEntry(42, "02:44:35",
				false, false, false, 1,"Modulverantwortlicher: ",
				"Dr. Hartmut Lanzinger");
		
		TextualEntry test_textualentry5 = new TextualEntry(42, "02:44:35",
			false, false, false, 1,"Dozenten: ",
			"Dr. Gerhard Baur, Dr. Ludwig Tomm");
		
		//TODO: "Einordnung des Moduls in Studiengaenge:" muss noch geparst werden in mehrere Zeilen
		TextualEntry test_textualentry6 = new TextualEntry(42, "02:44:35",
			false, false, false, 1,"Studieng\u00E4nge: ",
			"Informatik, B.Sc., Pflichtfach Mathematik Medieninformatik, B.Sc., Pflichtfach Mathematik Software-Engineering, B.Sc., Pflichtfach Mathematik");
		
		
		
		TextualEntry test_textualentry7 = new TextualEntry(42, "02:44:35",
			false, false, false, 1,"Voraussetzungen: ",
			"Schulmathematik");
		
		TextualEntry test_textualentry8 = new TextualEntry(42, "02:44:35",
			false, false, false, 1,"Lernziele: ",
			"Die Studierenden sollen");
		
		TextualEntry test_textualentry9 = new TextualEntry(42, "02:44:35",
			false, false, false, 1,"",
			"- Verständnis für die Grundprinzipien mathematischer Denk- und Arbeitsweisen exemplarisch für deren Einsatz in Anwendungen entwickeln;");

		TextualEntry test_textualentry10 = new TextualEntry(42, "02:44:35",
			false, false, false, 1,"",
			"- Grundbegriffe und -techniken sicher beherrschen und die Fähigkeit zum aktiven Umgang mit diesen erwerben;");

		TextualEntry test_textualentry11 = new TextualEntry(42, "02:44:35",
			false, false, false, 1,"",
			"- die Formulierung von Anwendungsproblemen in mathematischer Sprache erlernen;");
		
		TextualEntry test_textualentry12 = new TextualEntry(42, "02:44:35",
			false, false, false, 1,"",
			"- die wesentlichen Grundlagen der Mathematik für Anwendungen in Informatik sicher beherrschen;");
		
		TextualEntry test_textualentry13 = new TextualEntry(42, "02:44:35",
			false, false, false, 1,"",
			"- das Basiswissen und Fertigkeiten für das gesamte Studium, insbesondere die Grundlagen für Aufbaumodule erwerben;");
		
		TextualEntry test_textualentry14 = new TextualEntry(42, "02:44:35",
			false, false, false, 1,"",
			"- die Voraussetzungen für Vorlesungen der Anwender und weiterführende Vorlesungen in Mathematik erlernen.");
		
		
		
		TextualEntry test_textualentry15 = new TextualEntry(42, "02:44:35",
			false, false, false, 1,"Inhalt: ",
			"- Konvergenz von Folgen, unendliche Reihen");
		
		TextualEntry test_textualentry16 = new TextualEntry(42, "02:44:35",
			false, false, false, 1,"",
			"- Funktionen und Stetigkeit");
		
		TextualEntry test_textualentry17 = new TextualEntry(42, "02:44:35",
			false, false, false, 1,"",
			"- Differenzialrechnung: Ableitungen, Mittelwertsätze, Satz von Taylor, Extremwerte, Potenzreihen");
		
		TextualEntry test_textualentry18 = new TextualEntry(42, "02:44:35",
			false, false, false, 1,"",
			"- Integralrechnung, Riemann-Integral, Hauptsatz der Differential- und Integralrechung");
		
		TextualEntry test_textualentry19 = new TextualEntry(42, "02:44:35",
			false, false, false, 1,"",
			"- Elementare Differenzialgleichungen");
		
		
		
		TextualEntry test_textualentry20 = new TextualEntry(42, "02:44:35",
			false, false, false, 1,"Literatur: ",
			"siehe Vorlesung");
		
		//TODO:  "Lehrveranstaltungen und Lehrformen:" muss noch geparst werden...
		TextualEntry test_textualentry21 = new TextualEntry(42, "02:44:35",
			false, false, false, 1,"Lehrveranstaltungen: ",
			"Vorlesung Analysis I, 4 SWS (Dr. Gerhard Baur)");
		
		TextualEntry test_textualentry22 = new TextualEntry(42, "02:44:35",
			false, false, false, 1,"",
			"Übung Analysis I, 2 SWS (Dr. Gerhard Baur)");
		
		TextualEntry test_textualentry23 = new TextualEntry(42, "02:44:35",
			false, false, false, 1,"",
			"Tutorium Analysis I, 2 SWS ()");
		
		

	
		SelfStudy test_selfstudy1 = new SelfStudy(1, 120);
		

		List<SelfStudy> selfstudylist = new LinkedList<SelfStudy>();
		selfstudylist.add(test_selfstudy1);



		// "Absch\u00E4tzung des Arbeitsaufwands: "
		EffortEntry test_effortentry = new EffortEntry(43, "02:44:36", false,
				false, false, 1,"Arbeitsaufwand: ", 120, selfstudylist);

		//ich vermute mal, dass das hier "Einordnung des Moduls in Studiengaenge:" entspricht...
		//vorerst mal weggelassen fuer spaetere Klaerung des Sachverhalts...
		
		//CourseEntry test_courseentry = new CourseEntry(44, "02:44:37", false,
		//false, false, 1, "CourseEntryTitle: ", "Couse 08-15");
		
		
		
		
		TextualEntry test_textualentry24 = new TextualEntry(42, "02:44:35",
			false, false, false, 1,"Leistungsnachweis/Pr\u00FCfung: ",
			"50 % der Übungspunkte als Voraussetzung für die Zulassung zur Prüfung. Schriftliche Prüfung am Ende des Semesters.");
		
		TextualEntry test_textualentry25 = new TextualEntry(42, "02:44:35",
			false, false, false, 1,"Voraussetzungen (formal): ", " ");
		
		TextualEntry test_textualentry26 = new TextualEntry(42, "02:44:35",
			false, false, false, 1,"Notenbildung: ", "Ergebnis der Schriftlichen Prüfung");
		
		
		test_module.addTextualEntry(test_textualentry1);
		test_module.addTextualEntry(test_textualentry2);
		test_module.addTextualEntry(test_textualentry3);
		test_module.addTextualEntry(test_textualentry4);
		test_module.addTextualEntry(test_textualentry5);
		
		//test_module.addCourseEntry(test_courseentry);
		test_module.addTextualEntry(test_textualentry6);
		test_module.addTextualEntry(test_textualentry7);
		test_module.addTextualEntry(test_textualentry8);
		test_module.addTextualEntry(test_textualentry9);
		test_module.addTextualEntry(test_textualentry10);
		test_module.addTextualEntry(test_textualentry11);
		test_module.addTextualEntry(test_textualentry12);
		test_module.addTextualEntry(test_textualentry13);
		test_module.addTextualEntry(test_textualentry14);
		test_module.addTextualEntry(test_textualentry15);
		test_module.addTextualEntry(test_textualentry16);
		test_module.addTextualEntry(test_textualentry17);
		test_module.addTextualEntry(test_textualentry18);
		test_module.addTextualEntry(test_textualentry19);
		test_module.addTextualEntry(test_textualentry20);
		test_module.addTextualEntry(test_textualentry21);
		test_module.addTextualEntry(test_textualentry22);
		test_module.addTextualEntry(test_textualentry23);

		
		test_module.addEffortEntry(test_effortentry);
		
		test_module.addTextualEntry(test_textualentry24);
		test_module.addTextualEntry(test_textualentry25);
		test_module.addTextualEntry(test_textualentry26);
		
		
		//Erweiterte Testfaelle:
		TextualEntry test_textualentry27 = new TextualEntry(42, "02:44:35",
			false, false, false, 1," ", " ");
		
		TextualEntry test_textualentry28 = new TextualEntry(42, "02:44:35",
			false, false, false, 1,"Testf\u00E4lle: ", " ");
		
		TextualEntry test_textualentry29 = new TextualEntry(42, "02:44:35",
			false, false, false, 1,"Langer Punkt: ", "- Das ist ein extrem langer Punkt, der auf mehrere Zeilen aufgeteilt werden sollte um zu testen ob die String-spillter-Methode ihren Job richtig erledigt und den Text einrückt :D");
		
		test_module.addTextualEntry(test_textualentry27);
		test_module.addTextualEntry(test_textualentry28);
		test_module.addTextualEntry(test_textualentry29);
		
	
		
		LinkedList<Module> module_list = new LinkedList<Module>();
		module_list.add(test_module);
		module_list.add(test_module);


		SimplePdfCreator pdfcreator = new SimplePdfCreator();
		try {
			pdfcreator.createModulePdf("C:/PDFBox_test/module.pdf", module_list);
		} catch (COSVisitorException | IOException e) {
			System.out.println("Error in PDF creation, check if the path exists and / or the file isn't open...");
			System.exit(-1);
		}

		System.out.println("# module should be created now...");

		System.out.println("###########################################");
	}

}
