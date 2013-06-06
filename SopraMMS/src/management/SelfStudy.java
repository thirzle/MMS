package management;

public class SelfStudy {

@SuppressWarnings("unused")
private int selfstudyID;
private String title;
private int time;


public SelfStudy(String title, int time) {
this.title = title;
this.time = time;
}


public SelfStudy(int selfstudyID, int time, String title) {
this.selfstudyID = selfstudyID;
this.time = time;
this.title = title;
}


public String toString() {
return title+": "+time;
}


public int getTime() {
return time;
}
}