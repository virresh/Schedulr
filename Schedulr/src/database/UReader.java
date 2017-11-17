package database;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * 
 * One time initialization of the user list.
 * @author viresh
 *
 */

public class UReader {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		ObjectOutputStream f = new ObjectOutputStream(new FileOutputStream("./src/database/users.dat")); 
		UserList uList = new UserList();
		f.writeObject(uList);
		f.close();

	}

}
