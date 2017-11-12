package application;
	
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import constants.Constants;
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
	
	static Socket server;
	static ObjectOutputStream out;
	static ObjectInputStream in;
	
	@Override
	public void start(Stage primaryStage) {
		try {
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
	
	public static void connectAndInitialise() {
		try {
			server = new Socket(Constants.address,Constants.port);
			System.out.println("Connect to " + server.getRemoteSocketAddress());
			in = new ObjectInputStream(server.getInputStream());
			out = new ObjectOutputStream(server.getOutputStream());
			out.writeUTF("Timetable");
			out.flush();
			while(true) {
				String s = in.readUTF();
				if(s.equals("Acknowleged")) {
					tt = (TimeTable)in.readObject();
					break;
				}
			}			
		}catch(Exception ex) {
//			ex.printStackTrace();
			System.out.println("Could not connect to Server");
			System.exit(1);
		}finally {
			
		}
	}
	
	public static void main(String[] args) throws IOException {
		connectAndInitialise();
		try {
			launch(args);
		}
		finally {
			out.writeUTF("End");
			out.flush();
			server.close();
			System.out.println("Succesful Connection Termination");
		}
	}
}
