package constants;

import javafx.stage.Stage;

/**
 * This class holds some constants used at other places throughout.
 * @author Baani Leen and Viresh Gupta
 *
 */
public abstract class Constants {
	/**
	 * This is the port on the Server to connect to.
	 */
	public static int port = 8272;
	/**
	 * This is the Height of Window (GUI)
	 */
	public static int winHt = 1000;
	/**
	 * This is the Lenght of Window (GUI)
	 */
	public static int winWt = 1200;
	/**
	 * IP of the server
	 */
	public static String address = "localhost";
	/**
	 * List of all the rooms available (String[])
	 */
	public static String[] venues = {"C01","C02","C03","C11","C12","C13","C21","C22","C23","C24","CdX","LR1","LR2","LR3","L21","L22","L23","L31","L32","L33","S01","S02"};	
	/**
	 * List of Days (String[])
	 */
	public static String[] days = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
	
	/**
	 * Set the size of GUI Window.
	 * @param a The stage whose size will be changed.
	 */
	public static void sizeit(Stage a) {
//		a.setFullScreen(true);
		a.setHeight(winHt);
		a.setWidth(winWt);
		a.centerOnScreen();
	}

}
