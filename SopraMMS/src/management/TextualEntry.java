package management;


//TODO:
//namen adden bitte, alle die an der Klasse geschafft haben...
public class TextualEntry extends Entry {

	private String text;


	// invoke this constructor if you create a class with already existing data
//	public TextualEntry(int version, String date, boolean classification,
//			boolean approvalstatus, boolean declined, long entryID, String title, String text) {
//		// all parameters are related to abstract class Entry...
//		super(version, date, classification, approvalstatus, declined, entryID, title);
//		// actual data is text...
//		this.text = text;
//	}

	@Deprecated
	public TextualEntry(String title, String text) {
		// filling abstract class Entry with default values...
		super(title);
		this.text = text;
	}

	
	// invoke this class if you want to create new data
	@Deprecated
	public TextualEntry(String text) {
		// filling abstract class Entry with default values...
		super("Empty Title");
		this.text = text;
	}

	//updated version
	// invoke this constructor if you create a class with already existing data
	public TextualEntry(int version, String date, boolean classification,
			boolean approvalstatus, boolean declined, long entryID, String title, int order, String text) {
		// all parameters are related to abstract class Entry...
		super(version, date, classification, approvalstatus, declined, entryID, title, order);
		// actual data is text...
		this.text = text;
	}


	public TextualEntry(String title, int order, String text) {
		// filling abstract class Entry with default values...
		super(title,order);
		this.text = text;
	}


	// invoke this class if you want to create new data
	@Deprecated
	public TextualEntry(String text, int order) {
		// filling abstract class Entry with default values...
		super("Empty Title",order);
		this.text = text;
	}
	

	public String toString() {
		return this.getTitle() +" - "+ text;
	}
	
	public String getContent()
	{
		return text;
	}

}