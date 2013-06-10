package management;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.text.SimpleDateFormat;


//TODO:
//namen adden bitte, alle die an der Klasse geschafft haben...
/**
 * This class is the super class of several Entry related subclasses.
 * <p>
 * Following extensions are related to this class: 
 * {@link CourseEntry}, {@link EffortEntry}, {@link TextualEntry}.
 * <p>
 * All extensions have at least two constructors. One for creating completely new data,
 * that is going to be stored in the database, and one for storing already retrieved data. 
 * <p>
 * Entries are used to build {@link Module}s and fill them with content. 
 * @see Module
 * @see CourseEntry
 * @see EffortEntry
 * @see TextualEntry
 * @author AJ, add names here...
 *
 */
public abstract class Entry {

    	/**
    	 * This entry's ID
    	 */
	private long entryID;
	/**
	 * The version number of this entry.
	 */
	private int version; // version number
	/**
	 * Time stamp.
	 * <p>
	 * Format: "E yyyy.MM.dd 'um' hh:mm:ss a zzz"
	 */
	private String timestamp; // time stamp
	/**
	 * The classification of this entry.
	 * <p>
	 * Classify for Dezernat 2 ?
	 */
	private boolean classification; // Entry classification
	/**
	 * Approve-status of this entry.
	 * <p>
	 * Is this entry approved ?
	 */
	private boolean approved;
	/**
	 * Reject-status of this entry.
	 * <p>
	 * Is this entry rejected ?
	 */
	private boolean rejected;
	/**
	 * The title of this entry.
	 * <p>
	 * Is used to create the documents.
	 */
	private String title;
	/**
	 * The order of the entry.
	 * <p>
	 * Used to sort the entries after retrieving them from the database.
	 */
	private int order;
	/**
	 * A random number generator.
	 * <p>
	 * Used so generate new IDs on creation of new entries.
	 */
	private Random random;

	// use this when you import existing data
//	public Entry(int version, String date, boolean classification,
//			boolean approvalstatus, boolean declined, long entryID, String title) {
//		random = new Random();
//		this.version = version;
//		this.timestamp = timestamp;
//		this.classification = classification;
//		this.approved = approved;
//		this.rejected = rejected;
//		this.title = title;
//		this.entryID = entryID;
//	}


	// use this to create a new entry
	/**
	 * Constructor that initializes the extensions of this class. (Overloaded)
	 * <p>
	 * Following extensions may use this constructor:
	 * {@link CourseEntry}, {@link EffortEntry}, {@link TextualEntry}
	 * <p>
	 * deprecated...
	 * @see CourseEntry	
	 * @see EffortEntry
	 * @see TextualEntry
	 * @param title		A title for the entry. Used to create a document.
	 */
	@Deprecated
	public Entry(String title) {
		// default init...
		random = new Random();
		this.entryID = createEntryID();
		this.version = 1;
		this.timestamp = get_current_time();
		this.classification = false;
		this.approved = false;
		this.rejected = false;
		// actual data...
		this.title = title;
	}
	
	
	// use this when you import existing data
	//TODO: timestamp missing...
	/**
	 * Constructor that initializes the extensions of this class. (Overloaded)
	 * <p>
	 * Following extensions may use this constructor:
	 * {@link CourseEntry}, {@link EffortEntry}, {@link TextualEntry}
	 * <p>
	 * This overloaded version is used to import already existing data. e.g. from the database.
	 * @see CourseEntry	
	 * @see EffortEntry
	 * @see TextualEntry
         * @param version		The version of this Entry. (Used by extensions to initialize this super class)
         * @param date			The modification date. (Used by extensions to initialize this super class)
         * @param classification	Should this entry go to Dezernat 2 ? (Used by extensions to initialize this super class)
         * @param approvalstatus	Is this entry already approved ? (Used by extensions to initialize this super class)
         * @param declined		Is it declined and needs to be rewritten again ? (Used by extensions to initialize this super class)
         * @param entryID		This entry's ID. (Used by extensions to initialize this super class)
         * @param title			The title of this entry. Used by the document creation as content title. (Used by extensions to initialize this super class)
         * @param order			The order of this entry. (Used by extensions to initialize this super class)
         */
	public Entry(int version, String date, boolean classification,
			boolean approvalstatus, boolean declined, long entryID, String title, int order) {
		random = new Random();
		this.version = version;
		this.classification = classification;
		this.title = title;
		this.entryID = entryID;
		this.order = order;
	}

	// updated version
	// use this to create a new entry
	/**
	 * Constructor that initializes the extensions of this class. (Overloaded)
	 * <p>
	 * Following extensions may use this constructor:
	 * {@link CourseEntry}, {@link EffortEntry}, {@link TextualEntry}
	 * <p>
	 * This overloaded version is used when new data is going to be created. e.g. a new entry needs to be added to a module,
	 * or a completely new module is going to be created.
	 * @see CourseEntry	
	 * @see EffortEntry
	 * @see TextualEntry
         * @param title			The title of this entry. Used by the document creation as content title. (Used by extensions to initialize this super class)
         * @param order			The order of this entry. (Used by extensions to initialize this super class)
         */
	public Entry(String title, int order) {
		// default init...
		random = new Random();
		this.entryID = createEntryID();
		this.version = 1;
		this.timestamp = get_current_time();
		this.classification = false;
		this.approved = false;
		this.rejected = false;
		// actual data...
		this.title = title;
		this.order = order;
	}

	/**
	 * Returns the current time.
	 * <p>
	 * Is used when a new entry is created to specify it's creation time (timestamp)
	 * @return	String with current time. Format: "E yyyy.MM.dd 'um' hh:mm:ss a zzz"
	 */
	private String get_current_time() {
		Date current_date = new Date();
		SimpleDateFormat time_format = new SimpleDateFormat("E yyyy.MM.dd 'um' hh:mm:ss a zzz");
		// returning current time:
		return time_format.format(current_date);
	}

	/**
	 * Returns a new ID.
	 * <p>
	 * Is used when a new entry is created to be able to identify it.
	 * @return	An ID.
	 */
	private long createEntryID() {
		return random.nextInt(1000)+100*Calendar.getInstance().getTimeInMillis();	
	}
	

	// GETTERS...
	/**
	 * Returns the entry's ID.
	 * 
	 * @return	This entry's ID.
	 */
	public long getEntryID() {
		return entryID;
	}

	/**
	 * Returns the entry's version.
	 * 
	 * @return	This entry's version.
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * Returns the entry's timestamp.
	 * 
	 * @return	String with the creation/modification time of the entry. Format: "E yyyy.MM.dd 'um' hh:mm:ss a zzz"
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * Returns the classification-status.
	 * 
	 * @return	Classify for Dezernat 2 ?
	 */
	public boolean isClassification() {
		return classification;
	}

	/**
	 * Returns the approve-status.
	 * 
	 * @return	Is this entry approved ?
	 */
	public boolean isApproved() {
		return approved;
	}

	/**
	 * Returns the reject-status.
	 * 
	 * @return	Is this entry rejected ?
	 */
	public boolean isRejected() {
		return rejected;
	}

	/**
	 * Returns the title.
	 * <p>
	 * Used to create the documents.
	 * 
	 * @return	The title of this entry.
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Many once believed that this method's effect is unbelievable high and
	 * of mystic nature. Nobody actually knows the origin of this method. But there are also small groups of scientists believing in
	 * redundancy of this phenomenon.  
	 * @return	"Kein Inhalt vorhanden"
	 */
	public String getContent()
	{
		return "Kein Inhalt vorhanden";
	}

	/**
	 * Returns the order of this entry in the module.
	 * @return	The order in the module.
	 */
	public int getOrder() {
		return order;
	}

}
