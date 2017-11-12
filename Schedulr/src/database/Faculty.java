package database;

import java.io.Serializable;

public class Faculty extends User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6704644637996020617L;

	public Faculty(String email, String name, String pass) {
		super(email, name, pass);
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "Faculty";
	}

}
