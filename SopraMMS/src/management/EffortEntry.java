package management;

import java.util.List;
import java.util.LinkedList;

public class EffortEntry extends Entry {

	private int presenceTime;

	private List<SelfStudy> selfStudyList = new LinkedList<SelfStudy>();


	public EffortEntry(int version, String date, boolean classification,
			boolean approvalstatus, boolean declined, long entryID, String title, int presenceTime,
			List<SelfStudy> selfStudyList) {
		super(version, date, classification, approvalstatus, declined, entryID, title);
		this.presenceTime = presenceTime;
		this.selfStudyList = selfStudyList;
	}
	
	
	public EffortEntry(int version, String date, boolean classification,
			boolean approvalstatus, boolean declined, long entryID, String title, int presenceTime) {
		super(version, date, classification, approvalstatus, declined, entryID, title);
		this.presenceTime = presenceTime;
		this.selfStudyList = null;
	}
	
	//updated version
	public EffortEntry(int version, String date, boolean classification,
			boolean approvalstatus, boolean declined, long entryID, String title, int order, int presenceTime,
			List<SelfStudy> selfStudyList) {
		super(version, date, classification, approvalstatus, declined, entryID, title, order);
		this.presenceTime = presenceTime;
		this.selfStudyList = selfStudyList;
	}
	
	
	public EffortEntry(int version, String date, boolean classification,
			boolean approvalstatus, boolean declined, long entryID, String title, int order, int presenceTime) {
		super(version, date, classification, approvalstatus, declined, entryID, title, order);
		this.presenceTime = presenceTime;
		this.selfStudyList = null;
	}


	public void setSelfStudyList(List<SelfStudy> selfStudyList) {
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
		StringBuilder sb = new StringBuilder();
		sb.append("Aufwand\n");
		sb.append("    Präsenzzeit: "+presenceTime+"\n");
		for (SelfStudy ss : selfStudyList) {
			sb.append("    "+ss.toString()+"\n");
		}
		return sb.toString();
	}


	public List<SelfStudy> getSelfStudyList() {
		return selfStudyList;
	}
	
	public String getContent(){
		StringBuilder sb= new StringBuilder();
		sb.append("Präsenzzeit: "+presenceTime+" Stunden\n");
		for (SelfStudy ss : selfStudyList) {
			sb.append(""+ss.toString()+" Stunden\n");
		}
		return sb.toString();
	}

}
