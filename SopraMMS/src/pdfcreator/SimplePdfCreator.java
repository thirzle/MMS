package pdfcreator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.pdfbox.exceptions.COSVisitorException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;

import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;

import management.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is responsible for the creation of PDF documents.
 * <p>
 * Create an object of this class and call it's createModulePdf method
 * to create a PDF for an existing list of modules and some more parameters.
 * <p>
 * Edit parameters in this class to adjust font size, spacings and so on.
 * @author AJ
 *
 */
public class SimplePdfCreator {

    // font definitions
    
    /**
     * This font is used for titles, headers and so on.
     */
    private PDFont font_bold = PDType1Font.HELVETICA_BOLD;  
    /**
     * This font is used for content.
     */
    private PDFont font_normal = PDType1Font.HELVETICA;
   
    /**
     * Font size for titles.
     */
    private int font_size_title = 19;
    /**
     * Font size for content titles, content, page numbers and so on.
     * Generally used for anything that does not match "bigger titles".
     */
    private int font_size_content = 9;

    // line spaces
    // space_between_lines > space_between_content
    /**
     * Specifies the space between entries ({@link Entry}). Is also used for index pages to group.
     * <p>
     * Note: Has to be greater than space_between_content.
     */
    private int space_between_lines = 18;
    /**
     * Specifies the space between content lines. Is also used for index pages.
     * <p>
     * Note: Has to be smaller than space_between_lines.
     */
    private int space_between_content = 12;

    // page definitions
    /**
     * Page offset used to start text paintings.
     */
    private int page_offset_top = 50;
    /**
     * Page offset used to start text paintings.
     */
    private int page_offset_left = 50;
    /**
     * Page offset used to start text paintings.
     */
    private int page_offset_right = 50;
    /**
     * Page offset used to start text paintings.
     */
    private int page_offset_bottom = 50;

    /**
     * Width of the content title. Used to determine the offset for the content. Horizontal.
     */
    private int content_title_width = 170;
    
    /**
     * Offset for content after a title. Vertical.
     */
    private int content_offset_after_title = 90;

    /**
     * Offset for and index page entry. Offsets the content from its group title. Horizontal.
     */
    private int index_content_offset = 50;

    /**
     * Creates an object of this class. Create one to be able to create PDFs.
     */
    public SimplePdfCreator() {
	super();
    }

    /**
     * Creates a time stamp with current date and time. Is used during PDF creation as creation time.
     * @return String with current date and time. Format: "E yyyy.MM.dd 'um' hh:mm:ss a zzz"
     */
    private String get_current_time() {
	Date current_date = new Date();
	SimpleDateFormat time_format = new SimpleDateFormat("E yyyy.MM.dd 'um' hh:mm:ss a zzz");

	// returning current time:
	return time_format.format(current_date);
    }

    
    
    private List<String> parse_lines(String content){
	LinkedList<String> parsed_lines = new LinkedList<String>();
	
	System.out.println("#     parse_lines...");
	System.out.println("#     " + content);
	
	String[] lines = content.split("\n");
	int num = 0;
	for(int i = 0; i < lines.length; i++){
	    parsed_lines.add(lines[i]);
	    
	    System.out.println("#     " + lines[i]);
	    
	    num ++;
	}	
	
	System.out.println("#     breaks: " + num);
	
	return parsed_lines;
    }
    
    
    
    /**
     * Divides a given string into a list of Strings. In many cases the string for the content
     * of entries ({@link Entry}) is longer than a line on the PDF. In this case the string gets divided by
     * the max_size parameter to fit into multiple lines.
     * @param string		The String that gets divided.
     * @param max_size		The length of divided Strings.
     * @param font		The Font that is used to determine the length of a String.
     * @param font_size		The Font's size that is used to determine the length of a String.
     * @return			A list of strings.
     */
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

    /**
     * Creates a title page for the PDF document.
     * @param doc			The document the page is created for.
     * @param institute			The institute this document is belonging to.
     * @param faculty			The faculty this document is belonging to.
     * @param degree			The degree of this document. ("Master" , "Bachelor")
     * @param po			Examination Regulations. e.g. "FSPO 2012"
     * @param last_modification_date	Last modification date.
     * @param last_author		Last author.
     * @param semester			The semester this document is valid for.
     * @param version			The version number.
     * @return				A page. (The title page)
     * @throws IOException
     * @throws COSVisitorException
     */
    private PDPage createTitlePage(PDDocument doc, String institute, String faculty, String degree, String po, String last_modification_date, String last_author, String semester, String version) throws IOException, COSVisitorException {
	// load data and create a title page...

	// temporary first page... needs more content...
	PDPage title_page = new PDPage();
	
	
	//IMAGE
	
	PDXObjectImage ximage = null;
	ximage = new PDJpeg(doc, new FileInputStream( sysconfig.Config.system_path.getValue() + "pdf/uulm_logo.jpg" ) );

	PDPageContentStream contentStream = new PDPageContentStream(doc, title_page);
	
	
	//ximage.setHeight(300);
	//ximage.setWidth((int)(title_page.getMediaBox().getWidth()-page_offset_left-page_offset_right));
	
	//ximage.setBitsPerComponent(8);
	
	//contentStream.drawImage( ximage, page_offset_left, page_offset_top );
	
	
	
	contentStream.drawXObject(ximage, page_offset_left, title_page.getMediaBox().getHeight()-page_offset_top-100,
		title_page.getMediaBox().getWidth()-page_offset_left-page_offset_right,
		95);
	
	
	contentStream.beginText();
	contentStream.setFont(font_bold, font_size_content);
	String string = "Modulhandbuch";
	float string_width = font_bold.getStringWidth(string) / 1000 * font_size_content;
	float string_height = font_bold.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * font_size_content;
	contentStream.moveTextPositionByAmount((title_page.getMediaBox().getWidth() - string_width) / 2, (title_page.getMediaBox().getHeight() - string_height) / 2 + 290 - 150);
	contentStream.drawString(string);
	contentStream.endText();
	contentStream.close();

	contentStream.beginText();
	contentStream.setFont(font_bold, font_size_title);
	string = degree + "studiengang";
	string_width = font_bold.getStringWidth(string) / 1000 * font_size_title;
	string_height = font_bold.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * font_size_title;
	contentStream.moveTextPositionByAmount((title_page.getMediaBox().getWidth() - string_width) / 2, (title_page.getMediaBox().getHeight() - string_height) / 2 + 200  - 150);
	contentStream.drawString(string);
	contentStream.endText();
	contentStream.close();

	contentStream.beginText();
	contentStream.setFont(font_bold, font_size_title);
	string = institute;
	string_width = font_bold.getStringWidth(string) / 1000 * font_size_title;
	string_height = font_bold.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * font_size_title;
	contentStream.moveTextPositionByAmount((title_page.getMediaBox().getWidth() - string_width) / 2, (title_page.getMediaBox().getHeight() - string_height) / 2 + 170  - 150);
	contentStream.drawString(string);
	contentStream.endText();
	contentStream.close();

	contentStream.beginText();
	contentStream.setFont(font_bold, font_size_title);
	string = "(" + po + ")";
	string_width = font_bold.getStringWidth(string) / 1000 * font_size_title;
	string_height = font_bold.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * font_size_title;
	contentStream.moveTextPositionByAmount((title_page.getMediaBox().getWidth() - string_width) / 2, (title_page.getMediaBox().getHeight() - string_height) / 2 + 140  - 150);
	contentStream.drawString(string);
	contentStream.endText();
	contentStream.close();

	// TODO:
	// parse faculty into several strings if too big....
	contentStream.beginText();
	contentStream.setFont(font_bold, font_size_content);
	string = faculty;
	contentStream.moveTextPositionByAmount(title_page.getMediaBox().getWidth() - page_offset_top - 100, title_page.getMediaBox().getHeight() - page_offset_top - 20 - 150);
	contentStream.drawString(string);
	contentStream.endText();
	contentStream.close();

	contentStream.beginText();
	contentStream.setFont(font_normal, font_size_content);
	string = "Basierend auf Rev. " + version + ". Letzte Änderung am " + last_modification_date + " durch " + last_author + ". Generiert am " + get_current_time() + ".";
	string_width = font_normal.getStringWidth(string) / 1000 * font_size_content;
	contentStream.moveTextPositionByAmount((title_page.getMediaBox().getWidth() - string_width) / 2, page_offset_bottom);
	contentStream.drawString(string);
	contentStream.endText();
	contentStream.close();

	return title_page;
    }

    
    /**
     * Creates the Module Pages. If a page is full additional pages are created.
     * @param doc			The document the pages belong to.
     * @param module			A list of modules that is written on the pages.
     * @return				A list of Pages. (The content pages)
     * @throws IOException
     * @throws COSVisitorException
     */
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

	
	/*
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
	
	*/


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
		y -= space_between_lines;
		
		
		
		//NEW
		
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
		module_contentStream.drawString((effent.getTime() + effent.getSelfStudyList().get(0).getTime()) / 30 + "");
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
		module_contentStream.drawString((effent.getTime() + effent.getSelfStudyList().get(0).getTime()) / 30 + "");
		module_contentStream.endText();
		module_contentStream.close();
		y -= space_between_lines;
		
		
		
		
		
		

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
				
		String content = entrylist.get(j).getContent();
		
		List<String> content_lines = parse_lines(content);
		
		for(int o = 0; o < content_lines.size(); o++){
		
		
        		if (font_normal.getStringWidth(content_lines.get(o)) / 1000 * font_size_content < page.getMediaBox().getWidth() - (page_offset_left + content_title_width + page_offset_right)) {
        		    module_contentStream.beginText();
        		    module_contentStream.setFont(font_normal, font_size_content);
        		    module_contentStream.moveTextPositionByAmount(x, y);
        		    module_contentStream.drawString(content_lines.get(o));
        		    module_contentStream.endText();
        		    module_contentStream.close();
        		    y -= space_between_content;
        		}// else divide it into smaller pieces and print it ...
        		else {
        		    // System.out.println("> Dividing text by this width:  " +
        		    // (int) (page.getMediaBox().getWidth() - 270));
        		    LinkedList<String> string_list = divide_string(content_lines.get(o), page.getMediaBox().getWidth() - (page_offset_left + content_title_width + page_offset_right), font_normal, font_size_content);
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
        		    
        		}
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
    
    

    /**
     * Creates the index page(s) of the document. Pages need to be created at this point. 
     * @param doc				The document the pages belong to.
     * @param module_subject_list		This is a list of lists of modules. First index is used to find the subject the modules are grouped by. The second index gets the actual modules of this subject.
     * @param subject_list			This list of subjects stores the subject strings that are used to group the modules. The index is equal to the first index of module_subject_list parameter.
     * @param index_page_content_number_list	Stores the number of pages a module used. Used to calculate the offset for the page numbers. Order is assumed to be same as modules in module_subject_list parameter.
     * @return					A list of Pages. (The index pages)
     * @throws IOException
     * @throws COSVisitorException
     */
    private LinkedList<PDPage> createIndexPages(PDDocument doc, LinkedList<LinkedList<Module>> module_subject_list,
	    LinkedList<String> subject_list, LinkedList<String> index_page_content_number_list) throws IOException, COSVisitorException {
	// create module pages
	LinkedList<PDPage> pages = new LinkedList<PDPage>();

	PDPage page = new PDPage();
	pages.add(page);

	PDPageContentStream module_contentStream = new PDPageContentStream(doc, page);

	// set curser to content pos...
	int x = page_offset_left;
	int y = (int) page.getMediaBox().getHeight() - page_offset_top - content_offset_after_title;

	// pre calculate the page amount used by index pages:

	// 3 is the first content page inf index pages count is 1...
	int page_offset = 3;

	
	for (int j = 0; j < module_subject_list.size(); j++) {
	    y -= space_between_content;
	    for (int i = 0; i < module_subject_list.get(j).size(); i++) {
		y -= space_between_content;
		if (y < page_offset_bottom) {
		    page_offset += 1;
		    y = (int) page.getMediaBox().getHeight() - page_offset_top;
		}
	    }
	    
	    y -= space_between_content;
	}
	
	/*
	 * for (int j = 0; j < index_page_content_list.size(); j++) {
	 * 
	 * 
	 * y -= space_between_content;
	 * 
	 * // next line
	 * 
	 * // new page... if (y < page_offset_bottom) { page_offset += 1; y =
	 * (int) page.getMediaBox().getHeight() - page_offset_top; } }
	 */

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

	int subject_list_index = 0;
	int index_page_content_number_list_index = 0;
	for (int j = 0; j < module_subject_list.size(); j++) {

	    // print subject...
	    // TODO:...

	    x = page_offset_left;

	    module_contentStream.beginText();
	    module_contentStream.setFont(font_bold, font_size_content);
	    module_contentStream.moveTextPositionByAmount(x, y);
	    module_contentStream.drawString(subject_list.get(subject_list_index));
	    module_contentStream.endText();
	    module_contentStream.close();
	    
	    y -= space_between_content;
	    
	    subject_list_index ++;

	    
	    for (int i = 0; i < module_subject_list.get(j).size(); i++) {

		// TITLE FIRST...
		x = page_offset_left + index_content_offset;
		module_contentStream.beginText();
		module_contentStream.setFont(font_bold, font_size_content);
		module_contentStream.moveTextPositionByAmount(x, y);
		module_contentStream.drawString(module_subject_list.get(j).get(i).getName());
		module_contentStream.endText();
		module_contentStream.close();

		// CONTENT...
		x = (int) page.getMediaBox().getWidth() - page_offset_right - 30;

		module_contentStream.beginText();
		module_contentStream.setFont(font_normal, font_size_content);
		module_contentStream.moveTextPositionByAmount(x, y);
		module_contentStream.drawString((page_offset + Integer.parseInt(index_page_content_number_list.get(index_page_content_number_list_index))) + "");
		module_contentStream.endText();
		module_contentStream.close();
		y -= space_between_content;

		index_page_content_number_list_index ++;
		
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
	    
	    y -= space_between_content;
	}
	return pages;
    }
    

    /**
     * Generates the PDF Document.
     * <p>
     * All module_list {@link Module}'s entries ({@link Entry}) have to have a value. If entries have null values the PDF fails to generate.
     * <p>
     * The specified file path has to be valid, needs rights for write access, and the file, if already existing, has to be closed to be overwritten!!!
     * @param file			Path to the file, where it should be stored.
     * @param module_list		A valid List of modules, that belongs to the following attributes:
     * @param institute			The institute this document is belonging to.			
     * @param faculty			The faculty this document is belonging to.
     * @param degree			The degree of this document. ("Master" , "Bachelor")
     * @param po			Examination Regulations. e.g. "FSPO 2012"
     * @param last_modification_date	Last modification date.
     * @param last_author		Last author.
     * @param semester			The semester this document is valid for.
     * @param version			The version number.
     * @throws IOException
     * @throws COSVisitorException
     */
    public void createModulePdf(String file, List<Module> module_list, String institute, String faculty, String degree, String po, String last_modification_date, String last_author, String semester, String version) throws IOException, COSVisitorException {

	LinkedList<String> subject_list = new LinkedList<String>();
	LinkedList<LinkedList<Module>> module_subject_list = new LinkedList<LinkedList<Module>>();

	// pre sort the modules by subject...

	for (int i = 0; i < module_list.size(); i++) {
	    String subject = module_list.get(i).getSubject();
	    System.out.println(subject);

	    // check if subject already in list
	    boolean not_in_list = true;
	    int index_of_subject = -1;
	    for (int j = 0; j < subject_list.size(); j++) {
		if (subject_list.get(j).compareTo(subject) == 0) {
		    not_in_list = false;
		    index_of_subject = j;
		    break;
		}
	    }

	    // if subject already in list add the module to the right list
	    if (not_in_list) {
		subject_list.add(subject);
		// create new module group...
		module_subject_list.add(new LinkedList<Module>());
		module_subject_list.get(module_subject_list.size() - 1).add(module_list.get(i));
	    } else {
		module_subject_list.get(index_of_subject).add(module_list.get(i));
	    }
	}

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
	    title_page = createTitlePage(doc, institute, faculty, degree, po, last_modification_date, last_author, semester, version);

	    // Content Pages
	    System.out.println("> module_list size:  " + module_list.size());

	    int content_page_count = 0;
	    for (int k = 0; k < subject_list.size(); k++) {
		for (int i = 0; i < module_subject_list.get(k).size(); i++) {
		    System.out.println("> @ module_list element: " + i + "  " + module_subject_list.get(k).get(i).getName() + "  " + content_page_count);

		    index_page_content_list.add(module_subject_list.get(k).get(i).getName());
		    index_page_content_number_list.add(content_page_count + "");

		    System.out.println("page_count:  " + content_page_count);

		    LinkedList<PDPage> module_pages = createModulePages(doc, module_subject_list.get(k).get(i));
		    for (int j = 0; j < module_pages.size(); j++) {
			content_page_list.add(module_pages.get(j));
		    }
		    content_page_count += module_pages.size();
		}
	    }

	    // add pages to document...

	    // title page...
	    doc.addPage(title_page);

	    // index pages...
	    index_page_list = createIndexPages(doc, module_subject_list, subject_list, index_page_content_number_list);

	    // add numbers to the pages...
	    // add pages to document...

	    int page_number = 2;

	    // index pages
	    for (int i = 0; i < index_page_list.size(); i++) {
		PDPageContentStream page_number_contentStream = new PDPageContentStream(doc, index_page_list.get(i), true, true);
		page_number_contentStream.beginText();
		page_number_contentStream.setFont(font_normal, font_size_content);
		page_number_contentStream.moveTextPositionByAmount(index_page_list.get(i).getMediaBox().getWidth() - page_offset_right, page_offset_bottom / 2);
		page_number_contentStream.drawString(page_number + "");
		page_number_contentStream.endText();
		page_number_contentStream.close();

		doc.addPage(index_page_list.get(i));
		page_number++;
	    }

	    // content pages...
	    for (int i = 0; i < content_page_list.size(); i++) {
		PDPageContentStream page_number_contentStream = new PDPageContentStream(doc, content_page_list.get(i), true, true);
		page_number_contentStream.beginText();
		page_number_contentStream.setFont(font_normal, font_size_content);
		page_number_contentStream.moveTextPositionByAmount(content_page_list.get(i).getMediaBox().getWidth() - page_offset_right, page_offset_bottom / 2);
		page_number_contentStream.drawString(page_number + "");
		page_number_contentStream.endText();
		page_number_contentStream.close();

		doc.addPage(content_page_list.get(i));
		page_number++;
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
