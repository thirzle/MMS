package management;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

//TODO:
//namen adden bitte, alle die an der Klasse geschafft haben...
/**
 * This class is the representation of a single Module.
 * <p>
 * Several constructors assure the usability for several use cases.
 * <p>
 * Entries are used to build modules and fill them with content. A list of modules with additional data represents a complete document.
 * Entries can be following extensions of the {@link Entry} class: {@link CourseEntry}, {@link EffortEntry}, {@link TextualEntry}.
 * 
 * @see Entry
 * @see CourseEntry
 * @see EffortEntry
 * @see TextualEntry
 * @see SelfStudy
 * @author AJ, add names here...
 * 
 */
public class Module {
    /**
     * The name of the Module.
     * <p>
     * e.g. "Analysis I für Ingenieure und Informatiker"
     */
    private String name;
    /**
     * The creation date of this Module.
     */
    private Date creationDate;
    /**
     * The modification date of this Module
     */
    private Date modificationDate;
    /**
     * Approve-status of this Module.
     * <p>
     * Is this Module approved ?
     */
    private boolean approved;

    // TODO: die naechsten attribute sind ungenutzt...
    @SuppressWarnings("unused")
    /**
     * The classification of this Module.
     * <p>
     * Classify for Dezernat 2 ?
     */
    private boolean classification;
    @SuppressWarnings("unused")
    /**
     * Reject-status of this Module.
     * <p>
     * Is this Module rejected ?
     */
    private boolean rejected;
    //TODOEND
    
    /**
     * This Module's ID.
     */
    private long moduleID;
    /**
     * The version number of this Module.
     */
    private int version;
    /**
     * The institute of this Module.
     */
    private String instituteID;
    /**
     * The subject this Module will be grouped by.
     */
    private String subject;
    /**
     * A random number generator.
     * <p>
     * Used to generate new IDs on creation of new Modules.
     */
    private Random random;

    /**
     * The author that made the last modification.
     */
    private String modificationauthor;

    /**
     * A list of all entries this module consists of.
     * <p>
     * Entries can be following extensions of the {@link Entry} class: {@link CourseEntry}, {@link EffortEntry}, {@link TextualEntry}.
     */
    private List<Entry> entryList = new LinkedList<Entry>();

    @Deprecated
    /**
     * This constructor should be used to import existing data.
     * <p>
     * Entries are added after initialization.
     * <p>
     * Deprecation caused by moduleID's type change to long.
     * 
     * @param moduleID			This Module's ID.
     * @param version			The version number of this Module.
     * @param name			The name of the Module. e.g. "Analysis I für Ingenieure und Informatiker"
     * @param creationDate		The creation date of this Module.
     * @param modificationDate		The modification date of this Module.
     * @param approved			Approve-status of this Module. Is this Module approved ?
     * @param insituteID		The institute of this Module.
     * @param subject			The subject this Module will be grouped by.
     * @param modificationauthor	The author that made the last modification.
     */
    public Module(int moduleID, int version, String name, Date creationDate, Date modificationDate, boolean approved, String insituteID, String subject, String modificationauthor) {
	this.moduleID = moduleID;
	this.version = version;
	this.name = name;
	this.creationDate = creationDate;
	this.modificationDate = modificationDate;
	this.approved = approved;
	this.instituteID = insituteID;
	this.subject = subject;
	this.modificationauthor = modificationauthor;
	random = new Random();
    }
    
    @Deprecated
    /**
     * This constructor should be used to import existing data.
     * <p>
     * Entries are added during initialization.
     * <p>
     * Deprecation caused by moduleID's type change to long.
     * 
     * @param moduleID			This Module's ID.
     * @param version			The version number of this Module.
     * @param name			The name of the Module. e.g. "Analysis I für Ingenieure und Informatiker"
     * @param creationDate		The creation date of this Module.
     * @param modificationDate		The modification date of this Module.
     * @param approved			Approve-status of this Module. Is this Module approved ?
     * @param insituteID		The institute of this Module.
     * @param entryList			A list of entries that represent this module.
     * @param subject			The subject this Module will be grouped by.
     * @param modificationauthor	The author that made the last modification.
     */
    public Module(int moduleID, int version, String name, Date creationDate, Date modificationDate, boolean approved, String insituteID, List<Entry> entryList, String subject, String modificationauthor) {
	this.moduleID = moduleID;
	this.version = version;
	this.name = name;
	this.creationDate = creationDate;
	this.modificationDate = modificationDate;
	this.approved = approved;
	this.instituteID = insituteID;
	this.entryList = entryList;
	this.subject = subject;
	this.modificationauthor = modificationauthor;
	random = new Random();
    }

    // updated version
    /**
     * This constructor should be used to import existing data.
     * <p>
     * Entries are added after initialization.
     * 
     * @param moduleID			This Module's ID.
     * @param version			The version number of this Module.
     * @param name			The name of the Module. e.g. "Analysis I für Ingenieure und Informatiker"
     * @param creationDate		The creation date of this Module.
     * @param modificationDate		The modification date of this Module.
     * @param approved			Approve-status of this Module. Is this Module approved ?
     * @param insituteID		The institute of this Module.
     * @param subject			The subject this Module will be grouped by.
     * @param modificationauthor	The author that made the last modification.
     */
    public Module(long moduleID, int version, String name, Date creationDate, Date modificationDate, boolean approved, String insituteID, String subject, String modificationauthor) {
	random = new Random();
	this.moduleID = moduleID;
	this.version = version;
	this.name = name;
	this.creationDate = creationDate;
	this.modificationDate = modificationDate;
	this.approved = approved;
	this.instituteID = insituteID;
	this.subject = subject;
	this.modificationauthor = modificationauthor;
    }

    /**
     * This constructor should be used to import existing data.
     * <p>
     * Entries are added during initialization.
     *  
     * @param moduleID			This Module's ID.
     * @param version			The version number of this Module.
     * @param name			The name of the Module. e.g. "Analysis I für Ingenieure und Informatiker"
     * @param creationDate		The creation date of this Module.
     * @param modificationDate		The modification date of this Module.
     * @param approved			Approve-status of this Module. Is this Module approved ?
     * @param insituteID		The institute of this Module.
     * @param entryList			A list of entries that represent this module.
     * @param subject			The subject this Module will be grouped by.
     * @param modificationauthor	The author that made the last modification.
     */
    public Module(long moduleID, int version, String name, Date creationDate, Date modificationDate, boolean approved, String insituteID, List<Entry> entryList, String subject, String modificationauthor) {
	random = new Random();
	this.moduleID = moduleID;
	this.version = version;
	this.name = name;
	this.creationDate = creationDate;
	this.modificationDate = modificationDate;
	this.approved = approved;
	this.instituteID = insituteID;
	this.entryList = entryList;
	this.subject = subject;
	this.modificationauthor = modificationauthor;
    }

    /**
     * This constructor should be used when new data is created.
     * <p>
     * e.g. a new Module is needed or already existing modules should be copied and so on. 
     * <p>
     * Entries are added after initialization.
     * 
     * @param name			The name of the Module. e.g. "Analysis I für Ingenieure und Informatiker"
     * @param creationDate		The creation date of this Module.
     * @param modificationDate		The modification date of this Module.
     * @param approved			Approve-status of this Module. Is this Module approved ?
     * @param insituteID		The institute of this Module.
     * @param subject			The subject this Module will be grouped by.
     * @param modificationauthor	The author that made the last modification.
     */
    public Module(String name, Date creationDate, Date modificationDate, boolean approved, String insituteID, String subject, String modificationauthor) {
	random = new Random();
	this.moduleID = createModuleID();
	this.version = 1;
	this.name = name;
	this.creationDate = creationDate;
	this.modificationDate = modificationDate;
	this.approved = approved;
	this.instituteID = insituteID;
	this.subject = subject;
	this.modificationauthor = modificationauthor;

    }

    /**
     * This constructor should be used when new data is created.
     * <p>
     * e.g. a new Module is needed or already existing modules should be copied and so on. 
     * <p>
     * Entries are added during initialization.
     * 
     * @param name			The name of the Module. e.g. "Analysis I für Ingenieure und Informatiker"
     * @param creationDate		The creation date of this Module.
     * @param modificationDate		The modification date of this Module.
     * @param approved			Approve-status of this Module. Is this Module approved ?
     * @param insituteID		The institute of this Module.
     * @param entryList			A list of entries that represent this module.
     * @param subject			The subject this Module will be grouped by.
     * @param modificationauthor	The author that made the last modification.
     */
    public Module(String name, Date creationDate, Date modificationDate, boolean approved, String insituteID, List<Entry> entryList, String subject, String modificationauthor) {
	random = new Random();
	this.moduleID = createModuleID();
	this.version = 1;
	this.name = name;
	this.creationDate = creationDate;
	this.modificationDate = modificationDate;
	this.approved = approved;
	this.instituteID = insituteID;
	this.entryList = entryList;
	this.subject = subject;
	this.modificationauthor = modificationauthor;

    }

    /**
     * Returns a new ID.
     * <p>
     * Is used when a new Module is created to be able to identify it.
     * @return	An ID.
     */
    private long createModuleID() {
	return random.nextInt(1000) + 100 * Calendar.getInstance().getTimeInMillis();

    }

    /**
     * Returns the name of this Module
     * @return	The Module's name.
     */
    public String getName() {
	return name;
    }
    
    /**
     * Returns the creation date of this Module.
     * @return	The Modules' creation date.
     */
    public Date getCreationDate() {
	return creationDate;
    }

    /**
     * Returns the modification date (last modification) of this Module.
     * @return	The last modification date.
     */
    public Date getModificationDate() {
	return modificationDate;
    }

    /**
     * Returns the approve-status.
     * @return	Is this Module already approved ?
     */
    public boolean isApproved() {
	return approved;
    }

    /**
     * Returns the ID of this Module.
     * @return	The Module's ID.
     */
    public long getModuleID() {
	return moduleID;
    }

    /**
     * Returns the institute of this Module.
     * @return	The Module's institute.
     */
    public String getInstituteID() {
	return instituteID;
    }

    /**
     * Returns the list of all entries that are stored in this Module.
     * @return	The Module's entries.
     */
    public List<Entry> getEntryList() {
	return entryList;
    }

    /**
     * Stores the list of entries in this Module.
     * @param entryList The new list of entries, that this Module is going to handle.
     */
    public void setEntryList(List<Entry> entryList) {
	this.entryList = entryList;
    }

    /**
     * Adds a TextualEntry to the list of entries.
     * @param textualentry A textualEntry.
     */
    public void addTextualEntry(TextualEntry textualentry) {
	entryList.add(textualentry);
    }

    /**
     * Adds an EffortEntry to the list of entries.
     * @param effortentry An EffortEntry
     */
    public void addEffortEntry(EffortEntry effortentry) {
	entryList.add(effortentry);
    }

    /**
     * Adds a CourseEntry to the list of entries.
     * @param courseentry A CourseEntry.
     */
    public void addCourseEntry(CourseEntry courseentry) {
	entryList.add(courseentry);
    }

    /**
     * Adds an existing list of entries to the list of entries.
     * @param entries A list of entries.
     */
    public void addEntryList(List<Entry> entries) {
	entryList.addAll(entries);
    }

    /**
     * Adds an existing list of TextualEntry(s) to the list of entries.
     * @param entries A list of TextualEntry(s).
     */
    public void addTextualEntryList(List<TextualEntry> entries) {
	entryList.addAll(entries);
    }

    /**
     * Returns the subject of this Module.
     * @return	The Module's subject.
     */
    public String getSubject() {
	return subject;
    }

    /**
     * Returns the modification author that made the last changes to any values of this Module
     * or it's entries.
     * @return	The modification author.
     */
    public String getModificationauthor() {
	return modificationauthor;
    }

    /**
     * Returns the version of this Module.
     * @return	The Module's version.
     */
    public int getVersion() {
	return version;
    }

    /**
     * Returns the manual string of this Module.
     * @return	The Module's manual.
     */
//    public String getModuleManual() {
//	return new ModuleAdministration().getModuleManual(moduleID);
//    }

    /**
     * Sets the version number of this Module.
     * <p>
     * Useful after editing a Module.
     * @param version The new version of this Module.
     */
    public void setVersion(int version) {
	this.version = version;
    }
    
    /**
     * Sets the subject of this Module.
     * <p>
     * Useful for finishing a Module.
     * @param subject The new subject of this Module.
     */
    public void setSubject(String subject) {
    this.subject = subject;	
    }

    /**
     * Sets the modification author of this Module.
     * @param author The new modification author of this Module.
     */
    public void setAuthor(String author){
    	this.modificationauthor=author;
    }
    
    /**
     * Sets the modification date of this Module.
     * @param modificationDate The new modification date of this Module.
     */
    public void setModificationDate(Date modificationDate){
    	this.modificationDate=modificationDate;
    }
    
    @SuppressWarnings("unused")
    /**
     * Returns the current date.
     * @return The current Date.
     */
    private Date getCurrentDate() {
	Date date = new Date();
	return date;
    }

    /**
     * Prints the entire data of this Module to the console.
     * <p>
     * Nobody knows why...
     */
    public void print() {
	System.out.println("############ Modul #############");
	System.out.println("Titel: " + name);
	System.out.println("Autor: " + modificationauthor);
	System.out.println("Institut: " + instituteID);
	System.out.println("Erstellungsdatum: " + creationDate.toString());

	for (Entry e : entryList) {
	    System.out.println(e.toString());
	}
	System.out.println("################################");
    }

}
