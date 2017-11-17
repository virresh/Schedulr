package database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import constants.Constants;
import server.ServerRunner;

/**
 * 
 * Faculty Class to support the faculty functions
 * @author viresh
 *
 */

public class Faculty extends User implements Serializable {
	
	private static final long serialVersionUID = 6704644637996020617L;

	public Faculty(String email, String name, String pass) {
		super(email, name, pass);
	}

	@Override
	public String getType() {
		return "Faculty";
	}
	
	@Override
	public String registerCourse(Course c) {
		System.out.println("Registering for Student");

		for(String day: Constants.days) {
			List<Slot> newSubj = new ArrayList<Slot>();
			List<Slot> current = new ArrayList<Slot>();
			for(Slot i:ServerRunner.tt.getSlots(day)) {
				if(i.getCode().equals(c.getAcronym())) {
					newSubj.add(i);
				}
				else if(this.hasRegistered(i.getCode())) {
					current.add(i);
				}
			}
			for(Slot i: current) {
				for(Slot j: newSubj) {
					if(i.clashes(j)) {
						return "Failure, "+ j.getCode()+" Clashes with "+i.getCode();
					}
				}
			}
		}
		
		// also check for faculty clashes
		List<User> k = ServerRunner.ul.getEnrolled(c.getAcronym());
		for(User x: k) {
			if(x.getType().equals("Faculty")) {
				return "Failure, Already " + x.getEmail()+" Assigned to Course." ;
			}
		}
		return super.registerCourse(c);
	}
}
