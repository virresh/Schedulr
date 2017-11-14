package database;

import java.io.Serializable;

public class Course implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8070851068529370648L;
	private String status,name,code,instructorName,acronym,preconditions,postconditions;
	private int credits;
	
	Course(String s,String n,String c,String iN,String a,int cr,String pre, String post){
		status = s;	// mandatory, elective, etc etc
		name = n;	// name of course
		code = c;	// course code
		instructorName = iN;	// instructor name, not necessarily registered in the database
		acronym = a;	// course acronym
		credits = cr;	// course credits
		preconditions = pre;	// course preconditions
		postconditions = post;	// course postcondtions
	}
	
	@Override
	public String toString() {
		return status+" "+name+" "+code;
	}
	
	public String getDetailedString() {
		return "Course - "+name+" : "+code + " , "+acronym+"\n"+"Taught by "+instructorName+"\n"+"Worth "+credits+" credits\n Preconditions - \n"+preconditions+"\nPostconditions - \n"+postconditions;
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

	/**
	 * @return the preconditions
	 */
	public String getPreconditions() {
		return preconditions;
	}

	/**
	 * @return the postconditions
	 */
	public String getPostconditions() {
		return postconditions;
	}
}
