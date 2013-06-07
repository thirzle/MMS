package management;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.text.SimpleDateFormat;

public abstract class Entry {

	private long entryID;
	private int version; // version number
	private String timestamp; // time stamp, FORMAT: hh:mm:ss a zzz
	private boolean classification; // Entry classification
	private boolean approved;
	private boolean rejected;
	private String title;
	Random random;

	// use this when you import existing data
	public Entry(int version, String date, boolean classification,
			boolean approvalstatus, boolean declined, int entryID, String title) {
		random = new Random();
		this.version = version;
		this.timestamp = timestamp;
		this.classification = classification;
		this.approved = approved;
		this.rejected = rejected;
		this.title = title;
		this.entryID = entryID;
	}


	// use this to create a new entry
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

	
	private String get_current_time() {
		Date current_date = new Date();
		SimpleDateFormat time_format = new SimpleDateFormat("hh:mm:ss a zzz");
		// with date:
		// SimpleDateFormat date_and_time_format = new SimpleDateFormat
		// ("E yyyy.MM.dd 'at' hh:mm:ss a zzz");

		// returning current time:
		return time_format.format(current_date);
	}

	
	private long createEntryID() {
		return random.nextInt(1000)+100*Calendar.getInstance().getTimeInMillis();
		
	}
	

	// GETTERS...
	public long getEntryID() {
		return entryID;
	}


	public int getVersion() {
		return version;
	}


	public String getTimestamp() {
		return timestamp;
	}


	public boolean isClassification() {
		return classification;
	}


	public boolean isApproved() {
		return approved;
	}


	public boolean isRejected() {
		return rejected;
	}


	public String getTitle() {
		return title;
	}
	
	
	public String getContent()
	{
		return "Kein Inhalt vorhanden";
	}
}
