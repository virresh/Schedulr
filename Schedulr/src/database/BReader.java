package database;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import com.opencsv.CSVReader;

public class BReader {

	public static void main(String[] args) throws IOException {
		// This class will read the CSV and generate a list of courses will the preconditions
		CSVReader reader = new CSVReader(new FileReader("./src/database/ClassInfo.csv"));
		List<String[]> myEntries = reader.readAll();
		reader.close();
		ObjectOutputStream f = new ObjectOutputStream(new FileOutputStream("./src/database/courses.dat")); 
		CourseList cList = new CourseList();
		
		for(int i=1; i<myEntries.size(); i++) {
			Course x = new Course(myEntries.get(i)[0],myEntries.get(i)[1],myEntries.get(i)[2],myEntries.get(i)[3],myEntries.get(i)[5],Integer.parseInt(myEntries.get(i)[4]),myEntries.get(i)[11],myEntries.get(i)[12]);
			cList.addCourse(x.getAcronym(), x);
		}
		
		f.writeObject(cList);
		f.close();
	}

}
