package database;

import java.io.Serializable;

/**
 * Time table Slot Class. This will do stuff you cannot imagine :D.
 * venue will store Venues in ; separated manner
 */

public class Slot implements Serializable,Comparable<Slot>{

	private static final long serialVersionUID = 7912644612579068942L;
	private int startTime,endTime; // Stored in 24 hrs format 100 == 1:00
	private String subject,type,venue,code;
	// subject - subject name
	// type - lecture, tut,lab,other, holds the booker's name in case of extra bookings
	// venue - string of all venues concatenated together, ; separated
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
	
	public boolean clashes(Slot b) {
		
		if(this.startTime > b.startTime && this.startTime < b.endTime) {
			System.out.println("1st type Clash");
			return true;
		}
		else if(this.endTime > b.startTime && this.endTime <b.endTime) {
			System.out.println("2nd type Clash");
			return true;
		}
		else if(this.startTime == b.startTime && this.endTime == b.endTime) {
			System.out.println("3rd type Clash");
			return true;
		}
		
		return false;		
	}
	
	public String getDetails() {
		return subject+"\n"+code+"\n"+venue+"\n"+type+"\nFrom : "+startTime+"\nTo : "+endTime;
	}
	
	@Override
	public boolean equals(Object c) {
		if(!( c instanceof Slot) ){
			return false;
		}
		Slot b= (Slot)c;
		if(this.startTime == b.startTime && this.endTime == b.endTime && this.venue.equals(b.venue)) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		if(type.equals("Lec")) {
			return code+"  "+venue;
		}
		else {
			return code+"  "+type+"\n"+venue;
		}
	}
	/**
	 * @return the startTime
	 */
	public int getStartTime() {
		return startTime;
	}
	/**
	 * @return the endTime
	 */
	public int getEndTime() {
		return endTime;
	}
	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @return the venue
	 */
	public String getVenue() {
		return venue;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	
	public String getSlotType() {
		return "Default";
	}
}
