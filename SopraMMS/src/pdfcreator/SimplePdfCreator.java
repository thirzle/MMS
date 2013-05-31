package pdfcreator;

import java.io.File;
import java.io.FileOutputStream;
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

    // font definitions
    private PDFont font_bold = PDType1Font.HELVETICA_BOLD;
    private PDFont font_normal = PDType1Font.HELVETICA;
    private int font_size_title = 19;
    private int font_size_content = 8;

    // line spaces
    // space_between_lines > space_between_content
    private int space_between_lines = 18;
    private int space_between_content = 12;

    // page definitions
    private int page_offset_top = 50;
    private int page_offset_left = 50;
    private int page_offset_right = 50;
    private int page_offset_bottom = 50;

    private int content_title_width = 170;

    private int content_offset_after_title = 90;

    public SimplePdfCreator() {
	super();
    }

    private LinkedList<String> divide_string(String string, float max_size, PDFont font, int font_size) {
	LinkedList<String> string_list = new LinkedList<String>();
	String rest = string;
	boolean end = false;
	boolean list = false;

	if (string.startsWith("-")) {
	    list = true;
	}

	try {
	    while ((font.getStringWidth(rest) / 1000 * font_size) > max_size && !end) {
		int space_index_from = rest.length();
		while (rest.lastIndexOf(" ", space_index_from) > 0 && !end) {
		    int space_index = rest.lastIndexOf(" ", space_index_from);

		    space_index_from = space_index - 1;

		    // rest already fits -> no cut
		    if ((font.getStringWidth(rest) / 1000 * font_size) < max_size) {
			end = true;
			System.out.println("String divided successfully!");
			break;
		    }

		    // string fits but only if cut by space
		    if ((font.getStringWidth(rest.substring(0, space_index)) / 1000 * font_size) < max_size) {

			string_list.add(rest.substring(0, space_index));
			if (list) {
			    rest = "   " + rest.substring(space_index + 1, rest.length());
			} else {
			    rest = rest.substring(space_index + 1, rest.length());
			}
			space_index_from = rest.length();

			// System.out.println("> cut@:" + (space_index + 1) +
			// ", " + rest.length());

			break;
		    }// string does not fit...
		}
	    }
	} catch (IOException e) {
	    System.out.println("> ERROR:  String division failed...");
	    e.printStackTrace();
	}
	string_list.add(rest);
	return string_list;
    }

    // TODO: Some data needs to be laoded first from somewhere ...
    // no data no titlepage :D
    private PDPage createTitlePage(PDDocument doc) throws IOException, COSVisitorException {
	// load data and create a title page...

	// temporary first page... needs more content...
	PDPage title_page = new PDPage();

	PDPageContentStream contentStream = new PDPageContentStream(doc, title_page);
	contentStream.beginText();
	contentStream.setFont(font_bold, font_size_title);

	String title = "Title...";
	float title_width = font_bold.getStringWidth(title) / 1000 * font_size_title;
	float title_height = font_bold.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * font_size_title;

	contentStream.moveTextPositionByAmount((title_page.getMediaBox().getWidth() - title_width) / 2, (title_page.getMediaBox().getHeight() - title_height) / 2 + 100);
	contentStream.drawString(title);
	contentStream.endText();
	contentStream.close();

	return title_page;
    }

    private LinkedList<PDPage> createModulePages(PDDocument doc, Module module) throws IOException, COSVisitorException {
	// create module pages
	LinkedList<PDPage> pages = new LinkedList<PDPage>();

	PDPage page = new PDPage();
	pages.add(page);

	PDPageContentStream module_contentStream = new PDPageContentStream(doc, page);

	// Module Title
	module_contentStream.beginText();
	module_contentStream.setFont(font_bold, font_size_title);

	float title_width = font_bold.getStringWidth(module.getName()) / 1000 * font_size_title;
	float title_height = font_bold.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * font_size_title;

	module_contentStream.moveTextPositionByAmount((page.getMediaBox().getWidth() - title_width) / 2, (page.getMediaBox().getHeight() - title_height) - page_offset_top);
	module_contentStream.drawString(module.getName());
	module_contentStream.endText();
	module_contentStream.close();

	// set curser to content pos...
	int x = page_offset_left;
	int y = (int) page.getMediaBox().getHeight() - page_offset_top - content_offset_after_title;

	// Kuerzel/ Nummer:

	// TITLE FIRST...
	x = page_offset_left;
	module_contentStream.beginText();
	module_contentStream.setFont(font_bold, font_size_content);
	module_contentStream.moveTextPositionByAmount(x, y);
	module_contentStream.drawString("K\u00FCrzel/Nummer:");
	module_contentStream.endText();
	module_contentStream.close();

	x = page_offset_left + content_title_width;
	module_contentStream.beginText();
	module_contentStream.setFont(font_normal, font_size_content);
	module_contentStream.moveTextPositionByAmount(x, y);
	module_contentStream.drawString(module.getModuleID() + "");
	module_contentStream.endText();
	module_contentStream.close();
	y -= space_between_lines;

	// Leistungspunkte:

	// MUESSEN BERECHNET WERDEN....

	// TITLE FIRST...
	x = page_offset_left;
	module_contentStream.beginText();
	module_contentStream.setFont(font_bold, font_size_content);
	module_contentStream.moveTextPositionByAmount(x, y);
	module_contentStream.drawString("Leistungspunkte:");
	module_contentStream.endText();
	module_contentStream.close();

	// TODO:
	x = page_offset_left + content_title_width;
	module_contentStream.beginText();
	module_contentStream.setFont(font_normal, font_size_content);
	module_contentStream.moveTextPositionByAmount(x, y);
	module_contentStream.drawString("to do...");
	module_contentStream.endText();
	module_contentStream.close();
	y -= space_between_lines;

	// Semesterwochenstunden:

	// MUESSEN BERECHNET WERDEN....

	// TITLE FIRST...
	x = page_offset_left;
	module_contentStream.beginText();
	module_contentStream.setFont(font_bold, font_size_content);
	module_contentStream.moveTextPositionByAmount(x, y);
	module_contentStream.drawString("Semesterwochenstunden:");
	module_contentStream.endText();
	module_contentStream.close();

	// TODO:
	x = page_offset_left + content_title_width;
	module_contentStream.beginText();
	module_contentStream.setFont(font_normal, font_size_content);
	module_contentStream.moveTextPositionByAmount(x, y);
	module_contentStream.drawString("to do...");
	module_contentStream.endText();
	module_contentStream.close();
	y -= space_between_lines;

	List<Entry> entrylist = module.getEntryList();
	for (int j = 0; j < entrylist.size(); j++) {

	    boolean title_exists = false;

	    // TITLE FIRST...
	    x = page_offset_left;
	    module_contentStream.beginText();
	    module_contentStream.setFont(font_bold, font_size_content);
	    module_contentStream.moveTextPositionByAmount(x, y);
	    module_contentStream.drawString(entrylist.get(j).getTitle());
	    module_contentStream.endText();
	    module_contentStream.close();
	    if (entrylist.get(j).getTitle().length() > 0) {
		title_exists = true;
	    }

	    // CONTENT...
	    x = page_offset_left + content_title_width;

	    if (!title_exists) {
		y += space_between_lines - space_between_content;
	    }

	    // check if entry is an effort entry...
	    if (entrylist.get(j).getClass() == EffortEntry.class) {

		EffortEntry effent = (EffortEntry) entrylist.get(j);

		module_contentStream.beginText();
		module_contentStream.setFont(font_normal, font_size_content);
		module_contentStream.moveTextPositionByAmount(x, y);
		module_contentStream.drawString("Pr\u00E4senzzeit: " + effent.getTime() + " h");
		module_contentStream.endText();
		module_contentStream.close();
		y -= space_between_content;

		// IF the page end is reached create a new page
		// and
		// work on it
		if (y < page_offset_bottom) {
		    page = new PDPage();
		    pages.add(page);
		    module_contentStream = new PDPageContentStream(doc, page);
		    y = (int) page.getMediaBox().getHeight() - page_offset_top;
		}

		module_contentStream.beginText();
		module_contentStream.setFont(font_normal, font_size_content);
		module_contentStream.moveTextPositionByAmount(x, y);
		module_contentStream.drawString("Vor- und Nachbereitung: " + effent.getSelfStudyList().get(0).getTime() + " h");
		module_contentStream.endText();
		module_contentStream.close();
		y -= space_between_content;

		// IF the page end is reached create a new page
		// and
		// work on it
		if (y < page_offset_bottom) {
		    page = new PDPage();
		    pages.add(page);
		    module_contentStream = new PDPageContentStream(doc, page);
		    y = (int) page.getMediaBox().getHeight() - page_offset_top;
		}

		module_contentStream.beginText();
		module_contentStream.setFont(font_normal, font_size_content);
		module_contentStream.moveTextPositionByAmount(x, y);
		module_contentStream.drawString("Summe: " + (effent.getTime() + effent.getSelfStudyList().get(0).getTime()) + " h");
		module_contentStream.endText();
		module_contentStream.close();
		y -= space_between_content;

		// IF the page end is reached create a new page
		// and
		// work on it
		if (y < page_offset_bottom) {
		    page = new PDPage();
		    pages.add(page);
		    module_contentStream = new PDPageContentStream(doc, page);
		    y = (int) page.getMediaBox().getHeight() - page_offset_top;
		}

		y -= space_between_lines - space_between_content;
	    } else {
		// if content fits into one line
		if (font_normal.getStringWidth(entrylist.get(j).toString()) / 1000 * font_size_content < page.getMediaBox().getWidth() - (page_offset_left + content_title_width + page_offset_right)) {
		    module_contentStream.beginText();
		    module_contentStream.setFont(font_normal, font_size_content);
		    module_contentStream.moveTextPositionByAmount(x, y);
		    module_contentStream.drawString(entrylist.get(j).toString());
		    module_contentStream.endText();
		    module_contentStream.close();
		    y -= space_between_lines;
		}// else divide it into smaller pieces and print it ...
		else {
		    // System.out.println("> Dividing text by this width:  " +
		    // (int) (page.getMediaBox().getWidth() - 270));
		    LinkedList<String> string_list = divide_string(entrylist.get(j).toString(), page.getMediaBox().getWidth() - (page_offset_left + content_title_width + page_offset_right), font_normal, font_size_content);
		    for (int k = 0; k < string_list.size(); k++) {
			module_contentStream.beginText();
			module_contentStream.setFont(font_normal, font_size_content);
			module_contentStream.moveTextPositionByAmount(x, y);
			module_contentStream.drawString(string_list.get(k));
			module_contentStream.endText();
			module_contentStream.close();
			y -= space_between_content;

			// IF the page end is reached create a new page
			// and
			// work on it
			if (y < page_offset_bottom) {
			    page = new PDPage();
			    pages.add(page);
			    module_contentStream = new PDPageContentStream(doc, page);
			    y = (int) page.getMediaBox().getHeight() - page_offset_top;
			}
		    }
		    y -= space_between_lines - space_between_content;
		}
	    }

	    // next line

	    // IF the page end is reached create a new page and work on
	    // it
	    if (y < page_offset_bottom) {
		page = new PDPage();
		pages.add(page);
		module_contentStream = new PDPageContentStream(doc, page);
		y = (int) page.getMediaBox().getHeight() - page_offset_top;
	    }
	}
	return pages;
    }

    private LinkedList<PDPage> createIndexPages(PDDocument doc, LinkedList<String> index_page_content_list, LinkedList<String> index_page_content_number_list) throws IOException, COSVisitorException {
	// create module pages
	LinkedList<PDPage> pages = new LinkedList<PDPage>();

	PDPage page = new PDPage();
	pages.add(page);

	PDPageContentStream module_contentStream = new PDPageContentStream(doc, page);
	
	// set curser to content pos...
	int x = page_offset_left;
	int y = (int) page.getMediaBox().getHeight() - page_offset_top - content_offset_after_title;
	
	//pre calculate the page amount used by index pages:
	
	// 3 is the first content page inf index pages count is 1... 
	int page_offset = 3;
	
	for (int j = 0; j < index_page_content_list.size(); j++) {

	    
	    y -= space_between_content;
	        
	    // next line

	    // new page...
	    if (y < page_offset_bottom) {
		page_offset += 1;
		y = (int) page.getMediaBox().getHeight() - page_offset_top;
	    }
	}
	
	System.out.println("content_page_offset:  " + page_offset);

	// Index Title
	String index_title = "Inhaltsverzeichnis";

	module_contentStream.beginText();
	module_contentStream.setFont(font_bold, font_size_title);

	float title_width = font_bold.getStringWidth(index_title) / 1000 * font_size_title;
	float title_height = font_bold.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * font_size_title;

	module_contentStream.moveTextPositionByAmount((page.getMediaBox().getWidth() - title_width) / 2, (page.getMediaBox().getHeight() - title_height) - page_offset_top);
	module_contentStream.drawString(index_title);
	module_contentStream.endText();
	module_contentStream.close();

	// set curser to content pos...
	x = page_offset_left;
	y = (int) page.getMediaBox().getHeight() - page_offset_top - content_offset_after_title;
	


	for (int j = 0; j < index_page_content_list.size(); j++) {

	    
	    
	    // TITLE FIRST...
	    x = page_offset_left;
	    module_contentStream.beginText();
	    module_contentStream.setFont(font_bold, font_size_content);
	    module_contentStream.moveTextPositionByAmount(x, y);
	    module_contentStream.drawString(index_page_content_list.get(j));
	    module_contentStream.endText();
	    module_contentStream.close();

	    // CONTENT...
	    x = (int) page.getMediaBox().getWidth() - page_offset_right - 30;

	    module_contentStream.beginText();
	    module_contentStream.setFont(font_normal, font_size_content);
	    module_contentStream.moveTextPositionByAmount(x, y);
	    module_contentStream.drawString((page_offset + Integer.parseInt(index_page_content_number_list.get(j))) + "");
	    module_contentStream.endText();
	    module_contentStream.close();
	    y -= space_between_content;

	    
	    
	    // next line

	    // IF the page end is reached create a new page and work on
	    // it
	    if (y < page_offset_bottom) {
		page = new PDPage();
		pages.add(page);
		module_contentStream = new PDPageContentStream(doc, page);
		y = (int) page.getMediaBox().getHeight() - page_offset_top;
	    }
	}
	return pages;
    }

    public void createModulePdf(String file, List<Module> module_list, String faculty, String degree, String po,
	    String last_modification_date, String last_author, String semester, int version) throws IOException, COSVisitorException {
	// the document
	PDDocument doc = null;

	PDPage title_page;
	LinkedList<PDPage> index_page_list = new LinkedList<PDPage>();
	LinkedList<String> index_page_content_list = new LinkedList<String>();
	LinkedList<String> index_page_content_number_list = new LinkedList<String>();
	LinkedList<PDPage> content_page_list = new LinkedList<PDPage>();

	try {

	    // creating new document
	    doc = new PDDocument();

	    // FIRST PAGE
	    title_page = createTitlePage(doc);

	    // Content Pages
	    System.out.println("> module_list size:  " + module_list.size());

	    int content_page_count = 0;
	    for (int i = 0; i < module_list.size(); i++) {
		System.out.println("> @ module_list element: " + i + "  " + module_list.get(i).getName() + "  " + content_page_count);
		
		
		index_page_content_list.add(module_list.get(i).getName());
		index_page_content_number_list.add(content_page_count + "");
		
		System.out.println("page_count:  " + content_page_count);

		LinkedList<PDPage> module_pages = createModulePages(doc, module_list.get(i));
		for (int j = 0; j < module_pages.size(); j++) {
		    content_page_list.add(module_pages.get(j));
		}
		content_page_count += module_pages.size();
	    }

	    // add pages to document...

	    // title page...
	    doc.addPage(title_page);

	    // index pages...
	    index_page_list = createIndexPages(doc, index_page_content_list, index_page_content_number_list);
	    
	    
	    //add numbers to the pages...
	    //add pages to document...
	    
	    int page_number = 1;
	    
	    //index pages
	    for (int i = 0; i < index_page_list.size(); i++) {
		PDPageContentStream page_number_contentStream = new PDPageContentStream(doc, index_page_list.get(i), true, true);
		page_number_contentStream.beginText();
		page_number_contentStream.setFont(font_normal, font_size_content);
		page_number_contentStream.moveTextPositionByAmount(	index_page_list.get(i).getMediaBox().getWidth() - page_offset_right,
									page_offset_bottom / 2);
		page_number_contentStream.drawString(page_number + "");
		page_number_contentStream.endText();
		page_number_contentStream.close();
		
		doc.addPage(index_page_list.get(i));
		page_number ++;
	    }

	    // content pages...
	    for (int i = 0; i < content_page_list.size(); i++) {
		PDPageContentStream page_number_contentStream = new PDPageContentStream(doc, content_page_list.get(i), true, true);
		page_number_contentStream.beginText();
		page_number_contentStream.setFont(font_normal, font_size_content);
		page_number_contentStream.moveTextPositionByAmount(	content_page_list.get(i).getMediaBox().getWidth() - page_offset_right,
									page_offset_bottom / 2);
		page_number_contentStream.drawString(page_number + "");
		page_number_contentStream.endText();
		page_number_contentStream.close();
			
		doc.addPage(content_page_list.get(i));
		page_number ++;
	    }

	    FileOutputStream foFileOutputStream = new FileOutputStream(new File(file));
	    doc.save(foFileOutputStream);
	} finally {
	    if (doc != null) {
		doc.close();
		System.out.println("fertsch");
	    }
	}
    }

}
