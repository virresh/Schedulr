package constants;

import javafx.stage.Stage;

/**
 * This class holds some constants used at other places throughout.
 * @author Baani Leen and Viresh Gupta
 *
 */
public abstract class Constants {
	public static int port = 8272;
	public static int winHt = 1000;
	public static int winWt = 1200;
	public static String address = "localhost";
	public static String[] venues = {"C01","C02","C03","C11","C12","C13","C21","C22","C23","C24","CdX","LR1","LR2","LR3","L21","L22","L23","L31","L32","L33","S01","S02"};	
	public static String[] days = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
	public static void sizeit(Stage a) {
//		a.setFullScreen(true);
		a.setHeight(winHt);
		a.setWidth(winWt);
		a.centerOnScreen();
	}

}
