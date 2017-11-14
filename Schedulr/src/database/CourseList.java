package database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CourseList implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8701283965624725225L;
	private HashMap<String,Course> hm;
	CourseList(){
		hm = new HashMap<String,Course>();
	}
	
	public List<Course> getAllCourses() {
		List<Course> c = new ArrayList<Course>();
		for(Course x: hm.values()) {
			c.add(x);
		}
		return c;
	}
	
	public void addCourse(String cCode, Course c) {
		if(hm.containsKey(cCode)) {
			throw new NullPointerException("Course Already Exists");
		}
		else {
			hm.put(cCode, c);
		}
	}
	
	public Course getCourse(String s) {
		if(hm.containsKey(s)) {
			return hm.get(s);
		}
		else return null;
	}
}
