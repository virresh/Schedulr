package application.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import constants.Constants;
import database.Faculty;
import database.Student;
import database.User;
import javafx.event.ActionEvent;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class SignUpController implements Initializable{
	@FXML
	private Button Bt_back;
	@FXML
	private TextField Tx_fName;
	@FXML
	private TextField Tx_lName;
	@FXML
	private TextField Tx_uName;
	@FXML
	private PasswordField Tx_pass;
	@FXML
	private ComboBox<String> CB_userType;
	@FXML
	private Button Bt_submit;
	@FXML
	private Label L_status;

	// Event Listener on Button[#Bt_back].onAction
	@FXML
	public void f_back(ActionEvent event) {
		Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)event.getSource()).getScene().getWindow();
		Parent root=null;
		try {
			root = FXMLLoader.load(ViewRoomBookings_StudentController.class.getResource("/application/GUIs/Login.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		stageTheEventSourceNodeBelongs.setScene(new Scene(root));
		stageTheEventSourceNodeBelongs.sizeToScene();
	    stageTheEventSourceNodeBelongs.centerOnScreen();
	    Constants.sizeit(stageTheEventSourceNodeBelongs);
	}
	// Event Listener on Button[#Bt_submit].onAction
	@FXML
	public void f_submit(ActionEvent event) {
		System.out.println(CB_userType.getValue());
		if(Tx_fName.getText().equals("") || Tx_lName.getText().equals("") || Tx_uName.getText().equals("") ||CB_userType.getValue()==null || Tx_pass.getText().equals("")) {
			L_status.setText("Incomplete Form");
			return;
		}
		String x = Tx_uName.getText();
		if(!x.contains("@iiitd.ac.in")) {
			L_status.setText("Only IIIT-D Email ID's allowed.");
			return;
		}
		User u = null;
		if(CB_userType.getValue().equals("Faculty")) {
			u = new Faculty(Tx_uName.getText(),Tx_fName.getText()+" "+Tx_lName.getText(),Tx_pass.getText());
		}
		else if(CB_userType.getValue().equals("Student")) {
			u = new Student(Tx_uName.getText(),Tx_fName.getText()+" "+Tx_lName.getText(),Tx_pass.getText());
		}
		
		try {
			if(!Main.signUp(u)) {
				L_status.setText("Sign Up failed !");
				return;
			}
		} catch (IOException | ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		
		// Sign-Up succeeded, now Switch back to Login Screen
		Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)event.getSource()).getScene().getWindow();
		Parent root=null;
		try {
			root = FXMLLoader.load(ViewRoomBookings_StudentController.class.getResource("/application/GUIs/Login.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		stageTheEventSourceNodeBelongs.setScene(new Scene(root));
		stageTheEventSourceNodeBelongs.sizeToScene();
	    stageTheEventSourceNodeBelongs.centerOnScreen();
	    Constants.sizeit(stageTheEventSourceNodeBelongs);
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		CB_userType.getItems().add("Faculty");
		CB_userType.getItems().add("Student");
	}
}
