package management;

import java.util.Date;
import java.text.SimpleDateFormat;

public abstract class Entry {

	private int entryID;
	private int version; // version number
	private String timestamp; // time stamp, FORMAT: hh:mm:ss a zzz
	private boolean classification; // Entry classification
	private boolean approved;
	private boolean rejected;
	private String title;

	// use this when you import existing data
	public Entry(int version, String date, boolean classification,
			boolean approvalstatus, boolean declined, int entryID, String title) {
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


	// GETTERS...

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
}
