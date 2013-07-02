package management;

import java.util.Calendar;
import java.util.Random;

/**
 * This class stores the "self study" information.
 * <p>
 * It is used by the {@link EffortEntry} class to store a list of all SelfStudy objects, that belong to the corresponding {@link Module}.
 * 
 * @author Johann Albach, Max Reuter
 * @see Module
 * @see EffortEntry
 * 
 */
public class SelfStudy {
    /**
     * The ID of this SelfStudy Object.
     * <p>
     * Used for retrieving/associating it from database.
     */
    private long selfstudyID;
    /**
     * The title of this entry.
     * <p>
     * Is used to create the documents.
     */
    private String title;
    /**
     * Time/effort.
     */
    private int time;
    /**
     * A random number generator.
     * <p>
     * Used to generate new IDs on creation of new entries.
     * <p>
     * This class is not an extension of the {@link Entry} class,
     * thus it needs an own generator.
     */
    private Random random;

    
    /**
     * Use this constructor to create a new object, that is going to be filled
     * with data.
     * <p>
     * (Example: data needs to be stored into database) 
     * @param title	The title of this entry. e.g. "Nachbearbeitung", "\u00DCbungsaufgaben"
     * @param time	The time/effort needed by the title. (estimated)
     */
    public SelfStudy(String title, int time) {
	random = new Random();
	this.selfstudyID = createSelfStudyID();
	this.title = title;
	this.time = time;

    }

    /**
     * Use this constructor to import already existing data.
     * <p>
     * (Example: data is already loaded from database)
     * @param selfstudyID	The ID of this SelfStudy Object.
     * @param time		The time/effort needed by the title. (estimated)
     * @param title		The title of this entry. e.g. "Nachbearbeitung", "\u00DCbungsaufgaben"
     */
    public SelfStudy(long selfstudyID, int time, String title) {
	this.selfstudyID = selfstudyID;
	this.time = time;
	this.title = title;
    }
    
    public void setNewSelfStudyID() {
    	selfstudyID = createSelfStudyID();
    }

    /**
     * Returns a new ID.
     * <p>
     * Is used when a new object is created to be able to identify it.
     * @return	An ID.
     */
    private long createSelfStudyID() {
	return random.nextInt(1000) + 100 * Calendar.getInstance().getTimeInMillis();

    }

    /**
     * Returns the time/effort.
     * @return The time/effort.
     */
    public int getTime() {
	return time;
    }

    /**
     * Returns this object's ID.
     * @return	The ID of this object.
     */
    public long getSelfstudyID() {
	return selfstudyID;
    }

    /**
     * Sets the ID of this object.
     * <p>
     * Might get handy if you update some database entries locally or move them around.
     * @param selfstudyID	The ID this object should have/get.
     */
    public void setSelfstudyID(long selfstudyID) {
	this.selfstudyID = selfstudyID;
    }

    /**
     * Returns the title of this entry.
     * @return The title.
     */
    public String getTitle() {
	return title;
    }

    /**
     * Sets the title of this object.
     * <p>
     * Might come handy if you edit some data and need to update the title.
     * @param title The new title this object should have/get.
     */
    public void setTitle(String title) {
	this.title = title;
    }
    
    /**
     * Overrides the toString method.
     * <p>
     * Just an output of this object's content.
     */
    public String toString() {
	return title + ": " + time;
    }
}