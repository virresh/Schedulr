package database;

public class ExtraSlot extends Slot {

	/**
	 * These Slots are the one's bookable by the users
	 */
	private static final long serialVersionUID = -6263064100167871276L;

	public ExtraSlot(int sTime, int eTime, String sub, String typ, String ven, String acr) {
		super(sTime, eTime, sub, typ, ven, acr);
	}
	
	@Override
	public String getSlotType() {
		return "ExtraSlot";
	}

}
