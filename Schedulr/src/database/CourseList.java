package database;

import java.io.Serializable;
import java.util.HashMap;

public class CourseList implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8701283965624725225L;
	private HashMap<String,Course> hm;
	CourseList(){
		hm = new HashMap<String,Course>();
	}
	
	public void addCourse(String cCode, Course c) {
		if(hm.containsKey(cCode)) {
			throw new NullPointerException("Course Already Exists");
		}
		else {
			hm.put(cCode, c);
		}
	}
}
