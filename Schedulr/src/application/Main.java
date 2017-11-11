package application;
	
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import database.TimeTable;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 
 * This is the client code for running up the TimeTable management system.
 * @author Viresh Gupta
 *
 */

public class Main extends Application {
	
	/**
	 * Start method will simply be responsible for starting the main GUI
	 */
	
	public static TimeTable tt;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			ObjectInputStream I = new ObjectInputStream(new FileInputStream("./src/database/timeTable.dat"));
			tt = (TimeTable)I.readObject();
			I.close();
//			Parent root = FXMLLoader.load(getClass().getResource("./GUIs/Login.fxml"));
			Parent root = FXMLLoader.load(getClass().getResource("../application/GUIs/ViewTimeTable_Student.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("Schedulr");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
