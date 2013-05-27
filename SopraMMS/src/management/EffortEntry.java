package management;

import java.util.List;
import java.util.LinkedList;

public class EffortEntry extends Entry {

	private int presenceTime;

	private List<SelfStudy> selfStudyList = new LinkedList<SelfStudy>();


	public EffortEntry(int version, String date, boolean classification,
			boolean approvalstatus, boolean declined, int entryID, String title, int presenceTime,
			List<SelfStudy> selfStudyList) {
		super(version, date, classification, approvalstatus, declined, entryID, title);
		this.presenceTime = presenceTime;
		this.selfStudyList = selfStudyList;
	}


	public EffortEntry(String title, int presenceTime) {
		super(title);
		this.presenceTime = presenceTime;
	}


	public EffortEntry(int presenceTime) {
		super("Empty Title");
		this.presenceTime = presenceTime;
	}


	public int getTime() {
		return presenceTime;
	}


	public String toString() {
		return "to do...";
	}


	public List<SelfStudy> getSelfStudyList() {
		return selfStudyList;
	}

}
