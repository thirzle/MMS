package management;

/**
 * The TextualEntry class stores any textual information that belongs to a module.
 * <p>
 * It is an extension of the {@link Entry} class.
 * <p>
 * This class might be the most used and useful extension, because any data can be stored in it.
 * Data just has to be in textual form.
 * <p>
 * Several Constructors are given, but only a few are useful.
 * Use only Constructors that are marked as "useful".
 * All other Constructors are used for test purposes.
 * 
 * @see Module
 * @see Entry
 * @see SelfStudy
 * @author AJ, Max Reuter, David Lehr
 * 
 */
public class TextualEntry extends Entry {

    /**
     * This is the actual content of this {@link Entry}.
     */
    private String text;

    // invoke this constructor if you create a class with already existing data
    // public TextualEntry(int version, String date, boolean classification,
    // boolean approvalstatus, boolean declined, long entryID, String title, String text) {
    // // all parameters are related to abstract class Entry...
    // super(version, date, classification, approvalstatus, declined, entryID, title);
    // // actual data is text...
    // this.text = text;
    // }

    @Deprecated
    /**
     * Creates a TextualEntry with given parameters. (Overloaded)
     * <p>
     * minimalistic Constructor...
     * <p>
     * deprecated...
     * @param title		The title of this entry. Used by the document creation as content title. (Used to initialize the super class)
     * @param text		The textual content.
     */
    public TextualEntry(String title, String text) {
	// filling abstract class Entry with default values...
	super(title);
	this.text = text;
    }

    // invoke this class if you want to create new data
    @Deprecated
    /**
     * Creates a TextualEntry with given parameters. (Overloaded)
     * <p>
     * minimalistic Constructor...
     * <p>
     * deprecated...
     * @param text		The textual content.
     */
    public TextualEntry(String text) {
	// filling abstract class Entry with default values...
	super("Empty Title");
	this.text = text;
    }

    // updated version
    // invoke this constructor if you create an object with already existing data
    /**
     * Creates a TextualEntry with given parameters. (Overloaded)
     * <p>
     * Difference: used to create an Entry from already loaded data.
     * <p>
     * useful
     * @param date		The modification date. (Used to initialize the super class)
     * @param classification	Should this entry go to Dezernat 2 ? (Used to initialize the super class)
     * @param approvalstatus	Is this entry already approved ? (Used to initialize the super class)
     * @param declined		Is it declined and needs to be rewritten again ? (Used to initialize the super class)
     * @param entryID		This entry's ID. (Used to initialize the super class)
     * @param title		The title of this entry. Used by the document creation as content title. (Used to initialize the super class)
     * @param order		The order of this entry. (Used to initialize the super class)
     * @param text		The textual content.
     */
    public TextualEntry(String date, boolean classification, boolean approvalstatus, boolean declined, long entryID, String title, int order, String text) {
	// all parameters are related to abstract class Entry...
	super(date, classification, approvalstatus, declined, entryID, title, order);
	// actual data is text...
	this.text = text;
    }

    /**
     * Creates a TextualEntry with given parameters. (Overloaded)
     * <p>
     * Difference: used to create an Entry from scratch, that can be filled with new content.
     * The super class is initialized with default values for new {@link Entry}.
     * <p>
     * useful
     * @param title		The title of this entry. Used by the document creation as content title. (Used to initialize the super class)
     * @param order		The order of this entry. (Used to initialize the super class)
     * @param text		The textual content.
     */
    public TextualEntry(String title, int order, String text) {
	// filling abstract class Entry with default values...
	super(title, order);
	this.text = text;
    }

    // invoke this class if you want to create new data
    @Deprecated
    /**
     * Creates a TextualEntry with given parameters. (Overloaded)
     * <p>
     * minimalistic Constructor...
     * <p>
     * deprecated...
     * @param text		The textual content.
     * @param order		The order of this entry. (Used to initialize the super class)
     */
    public TextualEntry(String text, int order) {
	// filling abstract class Entry with default values...
	super("Empty Title", order);
	this.text = text;
    }

    public String toString() {
	return this.getTitle() + " - " + text;
    }

    public String getContent() {
	return text;
    }

}