package database;

import java.io.Serializable;
import java.util.HashMap;

public class UserList implements Serializable{

	private static final long serialVersionUID = -1769921675463216624L;
	/**
	 * 
	 */
	private HashMap<String, User> ulist;
	public UserList() {
		ulist = new HashMap<String, User>();
	}
	
	public boolean authenticateUser(String id, String pass) {
		if(ulist.containsKey(id)) {
			if(ulist.get(id).authenticate(pass) == true) {
				return true;
			}
		}
		return false;
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
}
