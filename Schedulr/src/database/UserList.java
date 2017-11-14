package database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserList implements Serializable{

	private static final long serialVersionUID = -1769921675463216624L;
	/**
	 * 
	 */
	private HashMap<String, User> ulist;
	public UserList() {
		ulist = new HashMap<String, User>();
	}
	
	public User authenticateUser(String id, String pass) {
		if(ulist.containsKey(id)) {
			if(ulist.get(id).authenticate(pass) == true) {
				return ulist.get(id);
			}
		}
		return null;
	}
	
	public User getUser(User p) {
		if(ulist.containsKey(p.email)) {
			return ulist.get(p.email);
		}
		return null;
	}
	
	public void putUser(User q) {
		ulist.put(q.email, q);
	}
	
	public boolean addUser(User o) {
		if(o==null) {
			return false;
		}
		if(ulist.containsKey(o.email)) {
			return false;
		}
		else {
			ulist.put(o.email, o);
			return true;
		}
	}
	
	public List<User> getEnrolled(String c){
		List<User> o = new ArrayList<User>();
		for(User x: ulist.values()) {
			if(x.hasRegistered(c)) {
				o.add(x);
			}
		}
		return o;
	}
}
