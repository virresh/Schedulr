package database;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * One Time initialization for bookings array
 * @author viresh
 *
 */
public class ExSlotReader {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		List<Slot> x  = new ArrayList<Slot>();
		ObjectOutputStream f = new ObjectOutputStream(new FileOutputStream("./src/database/bookings.dat"));
		f.writeObject(x);
		f.close();
	}

}
