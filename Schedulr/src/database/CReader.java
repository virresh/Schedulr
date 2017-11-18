package database;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import com.opencsv.CSVReader;

import application.Main;

/**
 * This class reads up the CSV file and creates TimeTable hashmap out of that csv and saves it into TimeTable.dat 
 * @author viresh
 *
 */
public class CReader{
	public final static TimeTable tt = new TimeTable();
	
	/**
	 * Helper function to return time
	 * @param alpha String to be converted into time
	 * @return integer representing time in 24hr Format
	 */
	static int getTime(String alpha) {
		return Integer.parseInt(alpha.split(":")[0]+alpha.split(":")[1]); 
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		CSVReader reader = new CSVReader(new FileReader("./src/database/ClassInfo.csv"));
		List<String[]> myEntries = reader.readAll();
		reader.close();
		ObjectOutputStream f = new ObjectOutputStream(new FileOutputStream("./src/database/timeTable.dat")); 

		for(int i=1; i<myEntries.size(); i++) {
			if(!myEntries.get(i)[13].equals("")) {
				String[] y = myEntries.get(i)[13].split("@");
				for(int m=0; m<y.length; m++) {
					String[] z = y[m].split("\\$");
					String[] w = z[1].split("-");
					Slot r = new Slot(getTime(w[0]),getTime(w[1]),myEntries.get(i)[1],"Tut",z[2],myEntries.get(i)[5]);
					tt.addSlot(z[0], r);
				}
			}
			if(!myEntries.get(i)[14].equals("")) {
				String[] y = myEntries.get(i)[14].split("@");
				for(int m=0; m<y.length; m++) {
					String[] z = y[m].split("\\$");
					String[] w = z[1].split("-");
					Slot r = new Slot(getTime(w[0]),getTime(w[1]),myEntries.get(i)[1],"Lab",z[2],myEntries.get(i)[5]);
					tt.addSlot(z[0], r);
				}
			}
			for(int k = 6; k<=10; k++) {
				if(!myEntries.get(i)[k].equals("-") && !myEntries.get(i)[k].equals("")) {
					// particular Day Slot, loop used for lectures
					String[] l = myEntries.get(i)[k].split("\\$");
					String[] m = l[0].split("-");
					Slot r = new Slot(getTime(m[0]),getTime(m[1]),myEntries.get(i)[1],"Lec",l[1],myEntries.get(i)[5]);
//					System.out.print(m[0]+" "+m[1]+" "+l[1]+ " ");
//					System.out.println("");
//					System.out.println(myEntries.get(0)[k].split(" ")[0]+" "+myEntries.get(i)[5]);
					tt.addSlot(myEntries.get(0)[k].split(" ")[0], r);
				}
			}

		}
		
		f.writeObject(tt);
		f.close();
	}
}
