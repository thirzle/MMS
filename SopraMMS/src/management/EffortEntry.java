package management;

import java.util.List;
import java.util.LinkedList;

//TODO:
//namen adden bitte, alle die an der Klasse geschafft haben...
/**
 * The EffortEntry class stores effort information that belong to a module.
 * <p>
 * It is an extension of the {@link Entry} class.
 * <p>
 * This class is actually a wrapper class for multiple {@link SelfStudy}
 * objects, that receive additional information. This wrapping makes it possible
 * to access and handle the information in a more sophisticated manner.
 * <p>
 * Several Constructors are given, but only a few are useful. Use only
 * Constructors that are marked as "useful". All other Constructors are used for
 * test purposes.
 * @see Module
 * @see Entry
 * @see SelfStudy
 * @author AJ, add names here...
 * 
 */
public class EffortEntry extends Entry {
    
    /**
     * The presence time of this EffortEntry.
     */
    private int presenceTime;

    /**
     * A list of {@link SelfStudy} objects.
     * <p>
     * Used to specify the time that needs to be spent for the given {@link Module}.
     */
    private List<SelfStudy> selfStudyList = new LinkedList<SelfStudy>();

    // public EffortEntry(int version, String date, boolean classification,
    // boolean approvalstatus, boolean declined, long entryID, String title, int
    // presenceTime,
    // List<SelfStudy> selfStudyList) {
    // super(version, date, classification, approvalstatus, declined, entryID,
    // title);
    // this.presenceTime = presenceTime;
    // this.selfStudyList = selfStudyList;
    // }
    //
    //
    // public EffortEntry(int version, String date, boolean classification,
    // boolean approvalstatus, boolean declined, long entryID, String title, int
    // presenceTime) {
    // super(version, date, classification, approvalstatus, declined, entryID,
    // title);
    // this.presenceTime = presenceTime;
    // this.selfStudyList = null;
    // }

    // updated version
    //existing entry import
    /**
     * Creates an EffortEntry with given parameters. (Overloaded)
     * <p>
     * Difference: multiple {@link SelfStudy} objects in a list needed as parameter.
     * <p>
     * useful 
     * @see SelfStudy
     * @param version		The version of this {@link Entry}-extension. (Used to initialize the super class)
     * @param date		The modification date. (Used to initialize the super class)
     * @param classification	Should this entry go to Dezernat 2 ? (Used to initialize the super class)
     * @param approvalstatus	Is this entry already approved ? (Used to initialize the super class)
     * @param declined		Is it declined and needs to be rewritten again ? (Used to initialize the super class)
     * @param entryID		This entry's ID. (Used to initialize the super class)
     * @param title		The title of this entry. Used by the document creation as content title. (Used to initialize the super class)
     * @param order		The order of this entry. (Used to initialize the super class)
     * @param presenceTime	The presence time of this EffortEntry.
     * @param selfStudyList	A list of {@link SelfStudy} objects. Used to specify the time that needs to be spent for the given {@link Module}.
     */
    public EffortEntry(int version, String date, boolean classification, boolean approvalstatus, boolean declined, long entryID, String title, int order, int presenceTime, List<SelfStudy> selfStudyList) {
	super(version, date, classification, approvalstatus, declined, entryID, title, order);
	this.presenceTime = presenceTime;
	this.selfStudyList = selfStudyList;
    }
    
    //existing entry import
    /**
     * Creates an EffortEntry with given parameters. (Overloaded)
     * <p>
     * Difference: no selfStudyList parameter. {@link SelfStudy} objects need to be added after creation.
     * <p>
     * useful
     * @see SelfStudy
     * @param version		The version of this {@link Entry}-extension. (Used to initialize the super class)
     * @param date		The modification date. (Used to initialize the super class)
     * @param classification	Should this entry go to Dezernat 2 ? (Used to initialize the super class)
     * @param approvalstatus	Is this entry already approved ? (Used to initialize the super class)
     * @param declined		Is it declined and needs to be rewritten again ? (Used to initialize the super class)
     * @param entryID		This entry's ID. (Used to initialize the super class)
     * @param title		The title of this entry. Used by the document creation as content title. (Used to initialize the super class)
     * @param order		The order of this entry. (Used to initialize the super class)
     * @param presenceTime	The presence time of this EffortEntry.
     */
    public EffortEntry(int version, String date, boolean classification, boolean approvalstatus, boolean declined, long entryID, String title, int order, int presenceTime) {
	super(version, date, classification, approvalstatus, declined, entryID, title, order);
	this.presenceTime = presenceTime;
	this.selfStudyList = null;
    }

    @Deprecated
    /**
     * Creates an EffortEntry with given parameters. (Overloaded)
     * <p>
     * minimalistic Constructor...
     * <p>
     * deprecated...
     * @param title		The title of this entry. Used by the document creation as content title. (Used to initialize the super class)
     * @param presenceTime	The presence time of this EffortEntry.
     */
    public EffortEntry(String title, int presenceTime) {
	super(title);
	this.presenceTime = presenceTime;
    }

    //new entry
    /**
     * Creates an EffortEntry with given parameters. (Overloaded)
     * <p>
     * Is used to create new EffortEntries. e.g. a {@link Module} needs to be modified or is created from scratch.
     * The super class is initialized with default values for new {@link Entry}.
     * <p>
     * useful
     * @param title		The title of this entry. Used by the document creation as content title. (Used to initialize the super class)
     * @param order		The order of this entry. (Used to initialize the super class)
     * @param presenceTime	The presence time of this EffortEntry.
     */
    public EffortEntry(String title, int order, int presenceTime) {
	super(title, order);
	this.presenceTime = presenceTime;
    }

    @Deprecated
    /**
     * Creates an EffortEntry with given parameters. (Overloaded)
     * <p>
     * minimalistic Constructor...
     * <p>
     * deprecated...
     * @param presenceTime	The presence time of this EffortEntry.
     */
    public EffortEntry(int presenceTime) {
	super("Empty Title");
	this.presenceTime = presenceTime;
    }

    
    /**
     * Overwrites this EffortEntry object's {@link SelfStudy} list.
     * @see SelfStudy
     * @param selfStudyList		List of {@link SelfStudy} objects.
     */
    public void setSelfStudyList(List<SelfStudy> selfStudyList) {
	this.selfStudyList = selfStudyList;
    }

    
    /**
     * Returns the presence time.
     * 
     * @return	The presence time.
     */
    public int getTime() {
	return presenceTime;
    }

    /**
     * Returns the list of {@link SelfStudy} objects.
     * <p>
     * Used to specify the time that needs to be spent for the given {@link Module}.
     * 
     * @return The list of {@link SelfStudy} objects.
     */
    public List<SelfStudy> getSelfStudyList() {
	return selfStudyList;
    }

    // TODO:
    // Summe des Aufwands fehlt.
    /**
     * Overrides the toString method.
     * <p>
     * Just an output of this object's content.
     */
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("Aufwand\n");
	sb.append("    Präsenzzeit: " + presenceTime + "\n");
	for (SelfStudy ss : selfStudyList) {
	    sb.append("    " + ss.toString() + "\n");
	}
	return sb.toString();
    }

    public String getContent() {
	StringBuilder sb = new StringBuilder();
	sb.append("Präsenzzeit: " + presenceTime + " Stunden\n");
	for (SelfStudy ss : selfStudyList) {
	    sb.append("" + ss.toString() + " Stunden\n");
	}
	return sb.toString();
    }

}
