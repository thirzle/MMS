package testsuite;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.pdfbox.exceptions.COSVisitorException;

import management.*;
import pdfcreator.*;
import controller.ModuleDBController;

public class LogicTest {

	private static ModuleDBController connection;


	public static void main(String[] args) {
		System.out.println("Starting test...");

		// creating a connection to the database
		connection = new ModuleDBController();

		// init test #1
		module_load_test();

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

		// id, name, creation, modification, approved, instituteid
		Module test_module = new Module(1337, "test_module", new Date(),
				new Date(), false, "Institut007");

		// version, timestamp, classification, approved, rejected, title, text
		TextualEntry test_textualentry = new TextualEntry(42, "02:44:35",
				false, false, false, "TextualEntryTitle: ",
				"some strange text for test purposes...");

		// title, time
		SelfStudy test_selfstudy1 = new SelfStudy("Selfstudy1: ", 3);

		// title, time
		SelfStudy test_selfstudy2 = new SelfStudy("Selfstudy2: ", 4);

		List<SelfStudy> selfstudylist = new LinkedList<SelfStudy>();
		selfstudylist.add(test_selfstudy1);
		selfstudylist.add(test_selfstudy2);

		// version, timestamp, classification, approved, rejected, title,
		// presenceTime, selfstudyList
		EffortEntry test_effortentry = new EffortEntry(43, "02:44:36", false,
				false, false, "EffortEntryTitle: ", 2, selfstudylist);

		// version, timestamp, classification, approved, rejected, title, course
		CourseEntry test_courseentry = new CourseEntry(44, "02:44:37", false,
				false, false, "CourseEntryTitle: ", "Couse 08-15");

		test_module.addTextualEntry(test_textualentry);
		test_module.addEffortEntry(test_effortentry);
		test_module.addCourseEntry(test_courseentry);

		SimplePdfCreator pdfcreator = new SimplePdfCreator();
		try {
			pdfcreator
					.createModulePdf("C:/PDFBox_test/module.pdf", test_module);
		} catch (COSVisitorException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("# module should be created now...");

		System.out.println("###########################################");
	}

}
