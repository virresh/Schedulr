package server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import constants.Constants;
import database.CourseList;
import database.ExtraSlot;
import database.Slot;
import database.TimeTable;
import database.UserList;

/**
 * 
 * This class is responsible for starting server on a port and generating sockets for clients and passing to separate threads.
 * @author Baani Leen and Viresh Gupta
 *
 */
public class ServerRunner {

	public static volatile TimeTable tt;
	public static volatile CourseList cl;
	public static volatile UserList ul;
	public static volatile List<Slot> bookings;
	
	/**
	 * Load up the temporary parameters in ram for faster serving to clients.
	 * @throws ClassNotFoundException When Files are corrupted and cannot be read
	 * @throws IOException	Should be taken care of manually in case this ever occurs
	 */
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
		
		I = new ObjectInputStream(new FileInputStream("./src/database/bookings.dat"));
		bookings = (List<Slot>)I.readObject();
		I.close();
	}
	
	/**
	 * Saves the state to disk.
	 * @throws FileNotFoundException The required database files were not found.
	 * @throws IOException Unknown hazards. Needs manual Attention.
	 */
	public static void saveToDisk() throws FileNotFoundException, IOException {
		System.out.println("Saving the changes to disk.");
		ObjectOutputStream f = new ObjectOutputStream(new FileOutputStream("./src/database/timeTable.dat")); 
		f.writeObject(tt);
		f.close();
		
		f = new ObjectOutputStream(new FileOutputStream("./src/database/courses.dat"));
		f.writeObject(cl);
		f.close();
		
		f = new ObjectOutputStream(new FileOutputStream("./src/database/users.dat"));
		f.writeObject(ul);
		f.close();
		
		f = new ObjectOutputStream(new FileOutputStream("./src/database/bookings.dat"));
		f.writeObject(bookings);
		f.close();
	}
	
	/**
	 * This method will remove all the requests that are more than 5 days old.
	 */
	public static void clearOldRequests() {
		List<Slot> h = new ArrayList<Slot>();
		for(Slot p: bookings) {
			ExtraSlot q = (ExtraSlot)p;
			if(q.getNumDaysPassed()>5) {
				h.add(p);
			}
		}
		for(Slot p: h) {
			bookings.remove(p);
		}
	}
	
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		ServerSocket me = null;
		try {
			initialise();

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
			saveToDisk();
			me.close();
		}
	}

}
