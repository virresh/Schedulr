package database;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public abstract class User implements Serializable{
	/**
	 * 
	 */
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
	
	public boolean authenticate(String pass) {
		if(pass.hashCode() == password) {
			return true;
		}
		else {
			return false;
		}
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
