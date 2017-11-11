package database;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import com.opencsv.CSVReader;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CReader extends Application{

	public final static TimeTable tt = new TimeTable();
	
	static int getTime(String alpha) {
		return Integer.parseInt(alpha.split(":")[0]+alpha.split(":")[1]); 
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		CSVReader reader = new CSVReader(new FileReader("./src/database/ClassInfo.csv"));
		List<String[]> myEntries = reader.readAll();
		reader.close();
		//	    ObjectOutputStream f = new ObjectOutputStream(new FileOutputStream("./src/database/timeTable.dat")); 

		for(int i=1; i<2; i++) {
			//	    	for(int j=0; j<myEntries.get(i).length; j++) {
			//	    		System.out.println(myEntries.get(i)[j]+" ");
			//	    	}
			//	    	System.out.println(myEntries.get(i)[1]+" ");
//			System.out.println(myEntries.get(i)[1]+ " " + myEntries.get(i)[6]+ " " + myEntries.get(i)[7]+ " " + myEntries.get(i)[8]+ " "+ myEntries.get(i)[9]+ " "+ myEntries.get(i)[10]+ " ");
			if(!myEntries.get(i)[13].equals("")) {
				//System.out.println("Has Tut "+myEntries.get(i)[13]);
			}
			if(!myEntries.get(i)[14].equals("")) {
				//System.out.println("Has LAB "+myEntries.get(i)[14]);
			}
			for(int k = 6; k<=10; k++) {
				if(!myEntries.get(i)[k].equals("-") && !myEntries.get(i)[k].equals("")) {
					// particular Day Slot, loop used for lectures
					String[] l = myEntries.get(i)[k].split("\\$");
					String[] m = l[0].split("-");
					System.out.print(m[0]+" "+m[1]+" "+l[1]+ " ");
					System.out.println("");
					Slot r = new Slot(getTime(m[0]),getTime(m[1]),myEntries.get(i)[1],"Lecture",l[1],myEntries.get(i)[5]);
					System.out.println(myEntries.get(0)[k].split(" ")[0]+" "+myEntries.get(i)[5]);
					tt.addSlot(myEntries.get(0)[k].split(" ")[0], r);
				}
			}

		}
		
		launch(args);

		//	    f.close();

		//	    ObjectInputStream l = new ObjectInputStream(new FileInputStream("./src/database/timeTable.dat"));
		//	    String[] r= (String[])l.readObject();
		//	    System.out.println(r[2]);
		//	    l.close();

	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../application/GUIs/ViewTimeTable_Student.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("Schedulr");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
