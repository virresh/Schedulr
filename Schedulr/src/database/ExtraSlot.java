package database;

/**
 * 
 * Provides an extension so the users can book slots apart from the predefined ones.
 * @author Baani Leen and Viresh Gupta
 *
 */
public class ExtraSlot extends Slot {

	private static final long serialVersionUID = -6263064100167871276L;

	private String day;
	public ExtraSlot(int sTime, int eTime, String sub, String typ, String ven, String acr, String d) {
		super(sTime, eTime, sub, typ, ven, acr);
		day = d;
	}
		
	@Override
	public String getSlotType() {
		return "ExtraSlot";
	}
	
	public String getDay() {
		return day;
	}

}
