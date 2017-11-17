package database;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Admin extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8561974389240109946L;

	public Admin(String email, String name, String pass) {
		super(email, name, pass);
	}

	@Override
	public String getType() {
		return "Admin";
	}
	
	public static void main(String[] args) {
		UserList ul = null;
		ObjectInputStream I = null;
		try {
			I = new ObjectInputStream(new FileInputStream("./src/database/users.dat"));
			ul = (UserList)I.readObject();
			I.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		User ad  = new Admin("admin@iiitd.ac.in","Admin","pass");
		if(ul.getUser(ad)==null) {
			ul.addUser(ad);
		}
		
		ObjectOutputStream f;
		try {
			f = new ObjectOutputStream(new FileOutputStream("./src/database/users.dat"));
			f.writeObject(ul);
			f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
