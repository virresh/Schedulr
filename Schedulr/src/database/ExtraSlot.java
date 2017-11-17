package database;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

/**
 * 
 * Provides an extension so the users can book slots apart from the predefined ones.
 * @author Baani Leen and Viresh Gupta
 *
 */
public class ExtraSlot extends Slot {

	private static final long serialVersionUID = -6263064100167871276L;

	private String day;
	LocalDate dt;
	int capacity;
	public ExtraSlot(int sTime, int eTime, String sub, String typ, String ven, String acr, String d,int cap) {
		super(sTime, eTime, sub, typ, ven, acr);
		day = d;
		capacity = cap;
		dt = LocalDate.now();
	}
	
	@Override 
	public String getDetails() {
		return super.getDetails()+"\nOn "+day+"\nAccomodating "+capacity;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public int getNumDaysPassed() {
		LocalDate two = LocalDate.now();
		return Period.between(two, dt).getDays();
	}
	
	@Override
	public String getSlotType() {
		return "ExtraSlot";
	}
	
	public String getDay() {
		return day;
	}

}
