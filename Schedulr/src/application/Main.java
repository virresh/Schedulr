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
 * <h1>Schedulr Client</h1>
 * This is the client code for running up the GUI system.
 * This class is the main Component servicing the Client Side.
 * This class will be responsible for rendering and handling events on the client side.
 * @author Baani Leen and Viresh Gupta
 *
 */

public class Main extends Application {
	
	/**
	 * TimeTable object to store the timetable in RAM.
	 */
	public static volatile TimeTable tt;
	
	/**
	 * User object to determine the current user in our client.
	 */
	public static volatile User u;
	
	/**
	 * Socket object to hold communication with Server
	 */
	static volatile Socket server;
	
	/**
	 * Output stream connected through Socket, acts to send commands to server.
	 */
	static volatile ObjectOutputStream out;
	
	/**
	 * Input stream connected through Socket, acts to get commands from server.
	 */
	static volatile ObjectInputStream in;

	/**
	 * Entry point for the GUI application.
	 * Only concerned with loading/un-loading of GUI. 
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(Main.class.getResource("/application/GUIs/Login.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("Schedulr");
			primaryStage.setScene(scene);
			primaryStage.sizeToScene();
			primaryStage.centerOnScreen();
			primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/application/Images/Schedulr.png")));
			primaryStage.show();
			Constants.sizeit(primaryStage);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get's Time Table over the Socket
	 * @throws IOException	When Socket is closed or Object cannot be serialized
	 * @throws ClassNotFoundException When object could not be read properly
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
	 * @param s This is the slot that needs to be booked
	 * @return True/False Based on whether the room request was taken into waiting queue / booked or not.
	 * @throws IOException When Socket is closed or Object cannot be serialized
	 * @throws ClassNotFoundException When object could not be read properly
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
	
	/**
	 * The replacement booking for an already existing slot. Can only be invoked by admin.
	 * NOTE: Use carefully. The slot to be replaced must have already been deleted by removeBooking.()
	 * @param s	New slot, that will be replaced.
	 * @return True/False depending on if this new slot is in clash with existing ones.
	 * @throws IOException When Socket is closed or Object cannot be serialized
	 * @throws ClassNotFoundException When object could not be read properly.
	 */
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
	 * Returns All the pending Requests for the Admin.
	 * @return List of Slots, containing un-accepted requests by students in the database.
	 * @throws IOException When Socket is closed or Object cannot be serialized
	 * @throws ClassNotFoundException When object could not be read properly.
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
	 * Contains all bookings by all users if called by Admin.
	 * @return List of Slots
	 * @throws IOException When Socket is closed or Object cannot be serialized
	 * @throws ClassNotFoundException When object could not be read properly.
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
	 * @param k The slot to be deleted.
	 * @throws IOException When Socket is closed or Object cannot be serialized
	 */
	public static void deleteBookings(Slot k) throws IOException {
		RequestObj h = new RequestObj("CancelBooking",k);
		out.writeObject(h);
		out.flush();
	}
	
	/**
	 * Command from admin to accept a request.
	 * Once accepted, this will move from pending requests to TimeTable
	 * @param k The slot for which to accept.
	 * @return Returns a string indication whether clashes or succesful acceptance.
	 * @throws IOException When Socket is closed or Object cannot be serialized
	 * @throws ClassNotFoundException When object could not be read properly
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
	 * @throws IOException When Socket is closed or Object cannot be serialized
	 */
	public static void userPut() throws IOException {
		RequestObj r = new RequestObj("UserPut",u);
		out.writeObject(r);
		out.flush();
	}
	
	/**
	 * Deprecated Method for updating User. Only for debugging purpose.
	 * @throws IOException When Socket is closed or Object cannot be serialized
	 * @throws ClassNotFoundException When object could not be read properly
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
	 * Method to toggle Register/De-register for a course for the current user. 
	 * Not availaible to Admin
	 * @param l The course acronym for which registration/de-registration will occur.
	 * @return String indication whether registration was success or not.
	 * @throws IOException When Socket is closed or Object cannot be serialized
	 * @throws ClassNotFoundException When object could not be read properly
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
	 * @return List of Courses, same value returned regardless of user.
	 * @throws ClassNotFoundException When object could not be read properly
	 * @throws IOException When Socket is closed or Object cannot be serialized
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
	 * @param x The user who needs to be registered.
	 * @return True/False depending if registration was successful or not
	 * @throws IOException When object could not be read properly
	 * @throws ClassNotFoundException When object could not be read properly
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
	 * @param email The Email Id of person to authenticate
	 * @param pass  The password of person to authenticate
	 * @return True/False depending if the email and password are both valid or not.
	 * @throws IOException When Socket is closed or Object cannot be serialized
	 * @throws ClassNotFoundException When object could not be read properly
	 * 
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
	 * @param args Not used.
	 * @throws IOException When Socket is closed or Object cannot be serialized
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
