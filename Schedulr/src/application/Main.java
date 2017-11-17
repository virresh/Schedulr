package application;
	
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import constants.Constants;
import constants.RequestObj;
import database.Course;
import database.ExtraSlot;
import database.Slot;
import database.TimeTable;
import database.User;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * 
 * This is the client code for running up the GUI system.
 * This class is the main Component servicing the Client Side.
 * This class will be responsible for rendering and handling events on the client side.
 * @author Baani Leen and Viresh Gupta
 *
 */

public class Main extends Application {
	
	public static volatile TimeTable tt;
	
	public static volatile User u;
	
	static volatile Socket server;
	static volatile ObjectOutputStream out;
	static volatile ObjectInputStream in;

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("./GUIs/Login.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("Schedulr");
			primaryStage.setScene(scene);
			primaryStage.sizeToScene();
			primaryStage.centerOnScreen();
			primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("./Images/Schedulr.png")));
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
		RequestObj r = new RequestObj("Timetable",null);
		
		out.writeObject(r);
		out.flush();
		while(true) {
			r = (RequestObj) in.readObject();
			if(r.mode.equals("Acknowleged")) {
				tt = (TimeTable)r.x;
				break;
			}
		}			
	}
	
	/**
	 * Request the server to book a particular room. Returns True/False depending on Accept/Rejection of Request.
	 * @param s
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static boolean requestRoomBooking(ExtraSlot s) throws IOException, ClassNotFoundException {
		RequestObj r = new RequestObj("RoomBookRequest",s,u);
		out.writeObject(r);
		out.flush();
		while(true) {
			r = (RequestObj) in.readObject();
			if(r.mode.equals("Acknowleged")) {
				return true;
			}
			else {
				return false;
			}
		}	
	}
	
	public static boolean auditRoomBooking(ExtraSlot s) throws IOException, ClassNotFoundException {
		RequestObj r = new RequestObj("AuditRoomRequest",s);
		out.writeObject(r);
		out.flush();
		while(true) {
			r = (RequestObj) in.readObject();
			if(r.mode.equals("Acknowleged")) {
				return true;
			}
			else {
				return false;
			}
		}	
	}
	
	
	/**
	 * Returns All the pending Requests for the Admin
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static List<Slot> getRequests() throws IOException, ClassNotFoundException{
		if(!u.getType().equals("Admin")) {
			return null;
		}
		RequestObj r = new RequestObj("GetRequests",null);
		out.writeObject(r);
		out.flush();
		while(true) {
			r = (RequestObj) in.readObject();
			if(r.mode.equals("Acknowleged")) {
				return (List<Slot>)r.x;
			}
		}	
	}
	
	/**
	 * Return all the bookings done by the current user
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static List<Slot> getBookings() throws IOException, ClassNotFoundException{
		RequestObj r = new RequestObj("GetBookings",u);
		out.writeObject(r);
		out.flush();
		while(true) {
			r = (RequestObj) in.readObject();
			if(r.mode.equals("Acknowleged")) {
				return (List<Slot>)r.x;
			}
		}	
	}
	
	/**
	 * Request the server to delete a booking.
	 * @param k
	 * @throws IOException
	 */
	public static void deleteBookings(Slot k) throws IOException {
		RequestObj h = new RequestObj("CancelBooking",k);
		out.writeObject(h);
		out.flush();
	}
	
	/**
	 * Command from admin to accept a request.
	 * @param k
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static String acceptBookings(Slot k) throws IOException, ClassNotFoundException {
		RequestObj h = new RequestObj("AcceptBooking",k);
		out.writeObject(h);
		out.flush();
		while(true) {
			RequestObj r = (RequestObj) in.readObject();
			if(r.mode.equals("Acknowleged")) {
				return (String)r.x;
			}
		}
	}
	
	/**
	 * Deprecated Method. Only meant for use in debugging and Developing purpose.
	 * @throws IOException
	 */
	public static void userPut() throws IOException {
		RequestObj r = new RequestObj("UserPut",u);
		out.writeObject(r);
		out.flush();
	}
	
	/**
	 * Deprecated Method for updating User. Only for debugging purpose.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void updateUser() throws IOException, ClassNotFoundException {
		RequestObj r = new RequestObj("UserGet",u);
		out.writeObject(r);
		out.flush();
		while(true) {
			RequestObj p = (RequestObj) in.readObject();
			if(p.mode.equals("Acknowleged")) {
				u = (User)p.x;
				break;
			}
		}
	}
	
	/**
	 * Method to Register/De-register for a course for the current user. 
	 * @param l
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static String addDropCourse(String l) throws IOException, ClassNotFoundException {
		RequestObj p = new RequestObj("CourseAddDrop",u,l);
		out.writeObject(p);
		out.flush();
		while(true) {
			p = (RequestObj)in.readObject();
			u = (User)p.x;
			return p.mode;
		}		
	}
	
	/**
	 * Return a list of all the courses.
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static List<Course> requestCourses() throws ClassNotFoundException, IOException{
		RequestObj r = new RequestObj("CourseAll",null);
		out.writeObject(r);
		out.flush();
		while(true) {
			r = (RequestObj) in.readObject();
			if(r.mode.equals("Acknowleged")) {
				return (List<Course>) r.x;
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
	 * This method will sign-up the user if possible, and return false otherwise
	 * @param x
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	
	public static boolean signUp(User x) throws IOException, ClassNotFoundException {
		if(x==null) {
			return false;
		}
		
		RequestObj r = new RequestObj("Signup",x);
		
		out.writeObject(r);
		out.flush();

		while(true) {
			r = (RequestObj) in.readObject();
			if(r.mode.equals("Success")) {
				return true;
			}
			else if(r.mode.equals("Failure")) {
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
		String[] p = new String[2];
		p[0] = email;
		p[1] = pass;
		RequestObj r = new RequestObj("Login",p);
		out.writeObject(r);
		out.flush();
		
		while(true) {
			r =  (RequestObj) in.readObject();
			if(r.mode.equals("Success")) {
				u = (User)r.x;
				return true;
			}
			else if(r.mode.equals("Failure")) {
				return false;
			}
		}
	}
	
	/**
	 * This method will initialize connections with the server and will hand-over control to the JavaFX engine
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		connectAndInitialise();
		try {
			launch(args);
		}
		finally {
			exit();
		}
	}
	
	/**
	 * Do up cleaning stuff and exit the Client application.
	 */
	public static void exit() {
		RequestObj r = new RequestObj("End",null);
		try {
			out.writeObject(r);
			out.flush();
			server.close();
		} catch (IOException e) {
//			e.printStackTrace();
		}
		System.out.println("Succesful Connection Termination");
		Platform.exit();
	}
}
