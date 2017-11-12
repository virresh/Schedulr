package database;

import java.io.Serializable;

public class Student extends User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6511838801947326258L;

	public Student(String email, String name, String pass) {
		super(email, name, pass);
	}

	@Override
	public String getType() {
		return "Student";
	}

}
