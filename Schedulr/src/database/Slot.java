package database;

import java.io.Serializable;

public class Slot implements Serializable,Comparable<Slot>{
	/**
	 * Time table Slot Class. This will do stuff you cannot imagine :D.
	 */
	private static final long serialVersionUID = 7912644612579068942L;
	private int startTime,endTime; // Stored in 24 hrs format 100 == 1:00
	private String subject,type,venue,code;
	// subject - subject name
	// type - lecture, tut,lab,other
	// venue - string of all venues concatenated together, space separated
	// subjectCode - like DM,AP,TA etc
	
	Slot(int sTime, int eTime, String sub, String typ,String ven, String acr){
		startTime = sTime;
		endTime = eTime;
		subject = sub;
		type = typ;
		venue = ven;
		code = acr;
	}
	@Override
	public int compareTo(Slot o) {
		// TODO Auto-generated method stub
		if(this.startTime < o.startTime) {
			return -1;
		}
		else if(this.startTime == o.startTime) {
			return 0;
		}
		else {
			return 1;
		}
	}
	
	@Override
	public String toString() {
		return code+"\n"+type+"\n"+venue;
	}
}
