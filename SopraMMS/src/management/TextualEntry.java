package management;

public class TextualEntry extends Entry {

	private String text;


	// invoke this constructor if you create a class with already existing data
	public TextualEntry(int version, String date, boolean classification,
			boolean approvalstatus, boolean declined, int entryID, String title, String text) {
		// all parameters are related to abstract class Entry...
		super(version, date, classification, approvalstatus, declined, entryID, title);
		// actual data is text...
		this.text = text;
	}


	public TextualEntry(String title, String text) {
		// filling abstract class Entry with default values...
		super(title);
		this.text = text;
	}


	// invoke this class if you want to create new data
	public TextualEntry(String text) {
		// filling abstract class Entry with default values...
		super("Empty Title");
		this.text = text;
	}


	public String toString() {
		// not sure if title too...
		// return this.getTitle() + text;

		return text;
	}

}