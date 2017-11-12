package application;
	
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import constants.Constants;
import database.TimeTable;
import database.User;
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
	
	public static User u;
	
	static Socket server;
	static ObjectOutputStream out;
	static ObjectInputStream in;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("./GUIs/Login.fxml"));
//			Parent root = FXMLLoader.load(getClass().getResource("../application/GUIs/ViewTimeTable_Student.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("Schedulr");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void requestTimeTable() throws IOException, ClassNotFoundException {
		out.writeUTF("Timetable");
		out.flush();
		while(true) {
			String s = in.readUTF();
			if(s.equals("Acknowleged")) {
				tt = (TimeTable)in.readObject();
				break;
			}
		}			
	}
	/**
	 * This function will connect to the database and set up and initialise required objects
	 */
	public static void connectAndInitialise() {
		try {
			server = new Socket(Constants.address,Constants.port);
			System.out.println("Connect to " + server.getRemoteSocketAddress());
			in = new ObjectInputStream(server.getInputStream());
			out = new ObjectOutputStream(server.getOutputStream());
			requestTimeTable();
		}catch(Exception ex) {
			System.out.println("Could not connect to Server");
			System.exit(1);
		}finally {
			
		}
	}
	
	/**
	 * This method will signup the user if possible, and return false otherwise
	 * @param x
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	
	public static boolean signUp(User x) throws IOException {
		if(x==null) {
			return false;
		}
		out.writeUTF("Signup");
		out.flush();
		while(true) {
			String j = in.readUTF();
			if(j.equals("Acknowleged")) {
				out.writeObject(x);
				out.flush();
				break;
			}
		}
		while(true) {
			String s = in.readUTF();
			if(s.equals("Success")) {
				return true;
			}
			else {
				return false;
			}
		}		
	}
	
	/**
	 * This method will authenticate whether the user is the required person or not
	 * @return
	 */
	public static boolean authenticate() {
		if(u==null) {
			return false;
		}
		
		
		return false;
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
