package database;

public class Course {
	private String status,name,code,instructorName,acronym;
	private int credits;
	
	Course(String s,String n,String c,String iN,String a,int cr){
		status = s;
		name = n;
		code = c;
		instructorName = iN;
		acronym = a;
		credits = cr;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return the instructorName
	 */
	public String getInstructorName() {
		return instructorName;
	}

	/**
	 * @return the acronym
	 */
	public String getAcronym() {
		return acronym;
	}

	/**
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}
}
