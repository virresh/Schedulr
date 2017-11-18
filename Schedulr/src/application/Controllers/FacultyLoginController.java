package application.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import constants.Constants;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.stage.Stage;

public class FacultyLoginController implements Initializable {
	@FXML
	private Label L_greet;
	@FXML
	private Button Bt_logout;
	@FXML
	private Button Bt_viewRoomBookings;
	@FXML
	private Button Bt_viewLabel;
	@FXML
	private Button Bt_viewCourse;
	@FXML
	private Button Bt_bookRoom;

	// Event Listener on Button[#Bt_logout].onAction
	@FXML
	public void f_logout(ActionEvent event) {
		Main.exit();
	}
	// Event Listener on Button[#Bt_viewRoomBookings].onAction
	@FXML
	public void f_viewRoomBookings(ActionEvent event) {
		Stage stageTheLabelBelongs = (Stage) L_greet.getScene().getWindow();
		Parent root=null;
		try 
		{
			root = FXMLLoader.load(LoginController.class.getResource("/application/GUIs/ViewRoomBookings_Student.fxml"));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return;
		}
		stageTheLabelBelongs.setScene(new Scene(root));
		stageTheLabelBelongs.sizeToScene();
	    stageTheLabelBelongs.centerOnScreen();
	    Constants.sizeit(stageTheLabelBelongs);
	}
	// Event Listener on Button[#Bt_viewLabel].onAction
	@FXML
	public void f_viewTimeTable(ActionEvent event) {
		Stage stageTheLabelBelongs = (Stage) L_greet.getScene().getWindow();
		Parent root=null;
		try 
		{
			root = FXMLLoader.load(LoginController.class.getResource("/application/GUIs/ViewTimeTable_Student.fxml"));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return;
		}
		stageTheLabelBelongs.setScene(new Scene(root));
		stageTheLabelBelongs.sizeToScene();
	    stageTheLabelBelongs.centerOnScreen();
	    Constants.sizeit(stageTheLabelBelongs);
	}
	// Event Listener on Button[#Bt_cancelBooking].onAction
	@FXML
	public void f_cancelBooking(ActionEvent event) {
		Stage stageTheLabelBelongs = (Stage) L_greet.getScene().getWindow();
		Parent root=null;
		try 
		{
			root = FXMLLoader.load(LoginController.class.getResource("/application/GUIs/ViewCourses_forStudent.fxml"));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return;
		}
		stageTheLabelBelongs.setScene(new Scene(root));
		stageTheLabelBelongs.sizeToScene();
	    stageTheLabelBelongs.centerOnScreen();
	    Constants.sizeit(stageTheLabelBelongs);
	}
	// Event Listener on Button[#Bt_bookRoom].onAction
	@FXML
	public void f_bookRoom(ActionEvent event) {
		Stage stageTheLabelBelongs = (Stage) L_greet.getScene().getWindow();
		Parent root=null;
		try 
		{
			root = FXMLLoader.load(LoginController.class.getResource("/application/GUIs/BookARoom_Student.fxml"));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return;
		}
		stageTheLabelBelongs.setScene(new Scene(root));
		stageTheLabelBelongs.sizeToScene();
	    stageTheLabelBelongs.centerOnScreen();
	    Constants.sizeit(stageTheLabelBelongs);
	} 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		L_greet.setText("Welcome "+Main.u.getName()+" !");
	}
}
