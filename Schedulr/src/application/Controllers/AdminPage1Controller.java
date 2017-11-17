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
import javafx.application.Platform;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminPage1Controller implements Initializable{
	@FXML
	private VBox VB_Holder;
	@FXML
	private Label L_greet;
	@FXML
	private Button Bt_Logout;
	@FXML
	private Button Bt_ViewBooking;
	@FXML
	private Button Bt_ViewTimeTable;
	@FXML
	private Button Bt_CancelBooking;
	@FXML
	private Button Bt_BookRoom;
	@FXML
	private Button Bt_RespondToReq;

	@FXML
	public void f_logout(ActionEvent event) {
		Main.exit();
	}
	
	// Event Listener on Button[#Bt_ViewBooking].onAction
	@FXML
	public void f_viewBookings(ActionEvent event) {
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
	}
	// Event Listener on Button[#Bt_ViewTimeTable].onAction
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
	}
	// Event Listener on Button[#Bt_CancelBooking].onAction
	@FXML
	public void f_cancelBooking(ActionEvent event) {
		Stage stageTheLabelBelongs = (Stage) L_greet.getScene().getWindow();
		Parent root=null;
		try 
		{
			root = FXMLLoader.load(LoginController.class.getResource("/application/GUIs/CancelABooking.fxml"));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return;
		}
		stageTheLabelBelongs.setScene(new Scene(root));
		stageTheLabelBelongs.sizeToScene();
		stageTheLabelBelongs.centerOnScreen();
	}
	// Event Listener on Button[#Bt_BookRoom].onAction
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
	}
	// Event Listener on Button[#Bt_RespondToReq].onAction
	@FXML
	public void f_respondToRequests(ActionEvent event) {
		Stage stageTheLabelBelongs = (Stage) L_greet.getScene().getWindow();
		Parent root=null;
		try 
		{
			root = FXMLLoader.load(LoginController.class.getResource("/application/GUIs/RespondtoRequests.fxml"));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return;
		}
		stageTheLabelBelongs.setScene(new Scene(root));
		stageTheLabelBelongs.sizeToScene();
		stageTheLabelBelongs.centerOnScreen();
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		L_greet.setText("Welcome Admin !");
	}
}
