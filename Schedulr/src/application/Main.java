package application;
	
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
	 * This class is the main Component servicing the Client Side.
	 * This class will be responsible for rendering and handling events on the client side.
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
	
	/**
	 * Get's Time Table over the Socket
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
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
	 * In case user is the person, place the user in the static user object for further use
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static boolean authenticate(String email, String pass) throws IOException, ClassNotFoundException {
		out.writeUTF("Login");
		out.flush();
		while(true) {
			String j = in.readUTF();
			if(j.equals("Acknowleged")) {
				System.out.println("Sending id");
				out.writeUTF(email);
				out.flush();
				break;
			}
		}
		
		while(true) {
			String j = in.readUTF();
			if(j.equals("Next")) {
				System.out.println("Sending Pass");
				out.writeUTF(pass);
				out.flush();
				break;
			}
		}
		
		while(true) {
			String j = in.readUTF();
			if(j.equals("Success")) {
				out.writeUTF("SendOK");
				out.flush();
				u = null;
				do {
					u = (User)in.readObject();
				}while(u==null);
				return true;
			}
			else if(j.equals("Failure")) {
				return false;
			}
		}
	}
	
	/**
	 * This method will initialise connections with the server and will hand-over control to the JavaFX engine
	 * @param args
	 * @throws IOException
	 */
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
