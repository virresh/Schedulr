package server;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import constants.Constants;
import database.CourseList;
import database.TimeTable;
import database.UserList;

public class ServerRunner {

	public static volatile TimeTable tt;
	public static volatile CourseList cl;
	public static volatile UserList ul;
	
	private static void initialise() throws ClassNotFoundException, IOException {
		ObjectInputStream I = new ObjectInputStream(new FileInputStream("./src/database/timeTable.dat"));
		tt = (TimeTable)I.readObject();
		I.close();
		
		I = new ObjectInputStream(new FileInputStream("./src/database/courses.dat"));
		cl = (CourseList)I.readObject();
		I.close();
		
		I = new ObjectInputStream(new FileInputStream("./src/database/users.dat"));
		ul = (UserList)I.readObject();
		I.close();
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		initialise();
		ServerSocket me = null;
		try {
			/*	create a server socket bound to the specified port */	
			me = new ServerSocket(Constants.port);
			ExecutorService ex = Executors.newFixedThreadPool(4);
			/*	Server is now listening for incoming client’s	request	*/	
			while(true) {	
				/*	Connection	is	established	*/	
				Socket connection = me.accept();	
				System.out.println("Connected");	
				/* Add a corresponding task to this client into the execution pool */	
				Runnable task = new ConnectionHandler(connection);
				ex.execute(task);
			}
		}
		catch(Exception e) {
			// In case of any exception, safeguard the local database files first and then exit
			e.printStackTrace();
		}finally {
			me.close();
			ObjectOutputStream f = new ObjectOutputStream(new FileOutputStream("./src/database/timeTable.dat")); 
			f.writeObject(tt);
			f.close();
			
			f = new ObjectOutputStream(new FileOutputStream("./src/database/courses.dat"));
			f.writeObject(cl);
			f.close();
			
			f = new ObjectOutputStream(new FileOutputStream("./src/database/users.dat"));
			f.writeObject(ul);
			f.close();
		}
	}

}
