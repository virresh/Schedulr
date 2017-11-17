package database;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Abstract User Class, supports authentication of user and other related stuff.
 */

public abstract class User implements Serializable{
	
	private static final long serialVersionUID = -3875257810585914459L;
	String email,name;
	int password;		// Store passwords as integer hashes of a string 
	Set<String> courses;
	User(String email, String name, String pass){
		this.email = email;
		this.name  = name;
		this.password = pass.hashCode();
		courses = new HashSet<String>();
	}
	
	public abstract String getType();
	
	public boolean equals(Object o) {
		if(!(o instanceof User)) {
			return false;
		}
		User x = (User)o;
		if(x.email.equals(email) && x.name.equals(name)) {
			return true;
		}
		return false;
	}
	
	public boolean authenticate(String pass) {
		if(pass.hashCode() == password) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean hasRegistered(String a) {
		if(courses.contains(a)) {
			return true;
		}
		return false;
	}
	
	public String deregister(String x) {
		if(courses.remove(x)) {
			return "Succeeded";
		}
		return "Failed";
	}
	
	public String registerCourse(Course c) {
		courses.add(c.getAcronym());
		return "Success";
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the courses
	 */
	public Set<String> getCourses() {
		return courses;
	}
}
