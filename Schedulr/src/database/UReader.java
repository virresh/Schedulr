package database;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class UReader {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		ObjectOutputStream f = new ObjectOutputStream(new FileOutputStream("./src/database/users.dat")); 
		UserList uList = new UserList();
		f.writeObject(uList);
		f.close();

	}

}
