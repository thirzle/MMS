package pdfcreator;

import java.io.IOException;

import org.apache.pdfbox.exceptions.COSVisitorException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;

import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import management.*;
import java.util.LinkedList;
import java.util.List;

public class SimplePdfCreator {

    public SimplePdfCreator() {
	super();
    }

    public LinkedList<String> divide_string(String string, float max_size, PDFont font, int font_size) {
	LinkedList<String> string_list = new LinkedList<String>();

	String rest = string;
	boolean end = false;

	try {
	    while ((font.getStringWidth(rest) / 1000 * font_size) > max_size && !end) {

		int space_index_from = rest.length();
		while (rest.lastIndexOf(" ", space_index_from) > 0 && !end) {
		    int space_index = rest.lastIndexOf(" ", space_index_from);

		    System.out.println("space_index: " + space_index + "    space_index_from: " + space_index_from);

		    space_index_from = space_index - 1;

		    // rest already fits -> no cut
		    if ((font.getStringWidth(rest) / 1000 * font_size) < max_size) {
			end = true;
			break;
		    }

		    // string fits but if cut by space
		    if ((font.getStringWidth(rest.substring(0, space_index)) / 1000 * font_size) < max_size) {

			string_list.add(rest.substring(0, space_index));
			rest = rest.substring(space_index + 1, rest.length());
			space_index_from = rest.length();

			System.out.println("> cut@:" + (space_index + 1) + ", " + rest.length());
			System.out.println("> fits!!!");

			break;
		    }// string does not fit...

		}
	    }
	} catch (IOException e) {
	    System.out.println("> ERROR:  SString division failed...");
	    e.printStackTrace();
	}

	string_list.add(rest);
	return string_list;
    }

    public void createModulePdf(String file, List<Module> module_list) throws IOException, COSVisitorException {
	// the document
	PDDocument doc = null;
	try {

	    // creating new document
	    doc = new PDDocument();

	    // FIRST PAGE

	    // temporary first page... needs more content...

	    PDPage title_page = new PDPage();
	    doc.addPage(title_page);

	    PDFont font_bold = PDType1Font.HELVETICA_BOLD;
	    PDFont font_normal = PDType1Font.HELVETICA;
	    int font_size = 20;

	    PDPageContentStream contentStream = new PDPageContentStream(doc, title_page);
	    contentStream.beginText();
	    contentStream.setFont(font_bold, font_size);

	    String title = "Title...";
	    float title_width = font_bold.getStringWidth(title) / 1000 * font_size;
	    float title_height = font_bold.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * font_size;

	    contentStream.moveTextPositionByAmount((title_page.getMediaBox().getWidth() - title_width) / 2, (title_page.getMediaBox().getHeight() - title_height) / 2 + 100);
	    contentStream.drawString(title);
	    contentStream.endText();
	    contentStream.close();

	    // LIST OF CONTENT HERE ...

	    // TODO:

	    // NEXT PAGE

	    System.out.println("> module_list size:  " + module_list.size());

	    for (int i = 0; i < module_list.size(); i++) {
		System.out.println("> module_list element:  " + i);

		System.out.println("> module_list size:  " + module_list.size());

		PDPage page = new PDPage();
		doc.addPage(page);

		PDPageContentStream module_contentStream = new PDPageContentStream(doc, page);

		module_contentStream.beginText();
		module_contentStream.setFont(font_bold, font_size);

		title_width = font_bold.getStringWidth(module_list.get(i).getName()) / 1000 * font_size;
		title_height = font_bold.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * font_size;

		module_contentStream.moveTextPositionByAmount((page.getMediaBox().getWidth() - title_width) / 2, (page.getMediaBox().getHeight() - title_height) - 50);
		module_contentStream.drawString(module_list.get(i).getName());
		module_contentStream.endText();
		module_contentStream.close();

		// set curser to content pos...
		int x = 50;
		int y = 650;

		// Kuerzel/ Nummer:

		// TITLE FIRST...
		x = 50;
		module_contentStream.beginText();
		module_contentStream.setFont(font_bold, 12);
		module_contentStream.moveTextPositionByAmount(x, y);
		module_contentStream.drawString("K\u00FCrzel/Nummer:");
		module_contentStream.endText();
		module_contentStream.close();

		x = 220;
		module_contentStream.beginText();
		module_contentStream.setFont(font_normal, 12);
		module_contentStream.moveTextPositionByAmount(x, y);
		module_contentStream.drawString(module_list.get(i).getModuleID() + "");
		module_contentStream.endText();
		module_contentStream.close();
		y -= 24;

		// Leistungspunkte:

		// MUESSEN BERECHNET WERDEN....

		// TITLE FIRST...
		x = 50;
		module_contentStream.beginText();
		module_contentStream.setFont(font_bold, 12);
		module_contentStream.moveTextPositionByAmount(x, y);
		module_contentStream.drawString("Leistungspunkte:");
		module_contentStream.endText();
		module_contentStream.close();

		// TODO:
		x = 220;
		module_contentStream.beginText();
		module_contentStream.setFont(font_normal, 12);
		module_contentStream.moveTextPositionByAmount(x, y);
		module_contentStream.drawString("to do...");
		module_contentStream.endText();
		module_contentStream.close();
		y -= 24;

		// Semesterwochenstunden:

		// MUESSEN BERECHNET WERDEN....

		// TITLE FIRST...
		x = 50;
		module_contentStream.beginText();
		module_contentStream.setFont(font_bold, 12);
		module_contentStream.moveTextPositionByAmount(x, y);
		module_contentStream.drawString("Semesterwochenstunden:");
		module_contentStream.endText();
		module_contentStream.close();

		// TODO:
		x = 220;
		module_contentStream.beginText();
		module_contentStream.setFont(font_normal, 12);
		module_contentStream.moveTextPositionByAmount(x, y);
		module_contentStream.drawString("to do...");
		module_contentStream.endText();
		module_contentStream.close();
		y -= 24;

		List<Entry> entrylist = module_list.get(i).getEntryList();
		for (int j = 0; j < entrylist.size(); j++) {

		    boolean title_exists = false;

		    // TITLE FIRST...
		    x = 50;
		    module_contentStream.beginText();
		    module_contentStream.setFont(font_bold, 12);
		    module_contentStream.moveTextPositionByAmount(x, y);
		    module_contentStream.drawString(entrylist.get(j).getTitle());
		    module_contentStream.endText();
		    module_contentStream.close();
		    if (entrylist.get(j).getTitle().length() > 0) {
			title_exists = true;
		    }

		    // CONTENT...
		    x = 220;

		    if (!title_exists) {
			y += 6;
		    }

		    // check if entry is an effort entry...
		    if (entrylist.get(j).getClass() == EffortEntry.class) {

			EffortEntry effent = (EffortEntry) entrylist.get(j);

			module_contentStream.beginText();
			module_contentStream.setFont(font_normal, 12);
			module_contentStream.moveTextPositionByAmount(x, y);
			module_contentStream.drawString("Pr\u00E4senzzeit: " + effent.getTime() + " h");
			module_contentStream.endText();
			module_contentStream.close();
			y -= 17;

			// IF the page end is reached create a new page
			// and
			// work on it
			if (y < 50) {
			    page = new PDPage();
			    doc.addPage(page);
			    module_contentStream = new PDPageContentStream(doc, page);
			    y = 720;
			}
			
			
			module_contentStream.beginText();
			module_contentStream.setFont(font_normal, 12);
			module_contentStream.moveTextPositionByAmount(x, y);
			module_contentStream.drawString("Vor- und Nachbereitung: " + effent.getSelfStudyList().get(0).getTime() + " h");
			module_contentStream.endText();
			module_contentStream.close();
			y -= 17;

			// IF the page end is reached create a new page
			// and
			// work on it
			if (y < 50) {
			    page = new PDPage();
			    doc.addPage(page);
			    module_contentStream = new PDPageContentStream(doc, page);
			    y = 720;
			}
			
			
			module_contentStream.beginText();
			module_contentStream.setFont(font_normal, 12);
			module_contentStream.moveTextPositionByAmount(x, y);
			module_contentStream.drawString("Summe: " + (effent.getTime() + effent.getSelfStudyList().get(0).getTime()) + " h");
			module_contentStream.endText();
			module_contentStream.close();
			y -= 17;

			// IF the page end is reached create a new page
			// and
			// work on it
			if (y < 50) {
			    page = new PDPage();
			    doc.addPage(page);
			    module_contentStream = new PDPageContentStream(doc, page);
			    y = 720;
			}

			y -= 7;
		    } else {
			// if content fits into one line
			if (font_bold.getStringWidth(entrylist.get(j).toString()) / 1000 * font_size < page.getMediaBox().getWidth() - 250) {
			    module_contentStream.beginText();
			    module_contentStream.setFont(font_normal, 12);
			    module_contentStream.moveTextPositionByAmount(x, y);
			    module_contentStream.drawString(entrylist.get(j).toString());
			    module_contentStream.endText();
			    module_contentStream.close();
			    y -= 24;
			}// else divide it into smaller pieces and print it ...
			else {
			    System.out.println("> Dividing text by this width:  " + (int) (page.getMediaBox().getWidth()));
			    LinkedList<String> string_list = divide_string(entrylist.get(j).toString(), page.getMediaBox().getWidth(), font_normal, font_size);
			    for (int k = 0; k < string_list.size(); k++) {
				module_contentStream.beginText();
				module_contentStream.setFont(font_normal, 12);
				module_contentStream.moveTextPositionByAmount(x, y);
				module_contentStream.drawString(string_list.get(k));
				module_contentStream.endText();
				module_contentStream.close();
				y -= 17;

				// IF the page end is reached create a new page
				// and
				// work on it
				if (y < 50) {
				    page = new PDPage();
				    doc.addPage(page);
				    module_contentStream = new PDPageContentStream(doc, page);
				    y = 720;
				}
			    }
			    y -= 7;
			}
		    }

		    // next line

		    // IF the page end is reached create a new page and work on
		    // it
		    if (y < 50) {
			page = new PDPage();
			doc.addPage(page);
			module_contentStream = new PDPageContentStream(doc, page);
			y = 720;
		    }

		}

	    }

	    doc.save(file);
	} finally {
	    if (doc != null) {
		doc.close();
	    }
	}
    }

}
