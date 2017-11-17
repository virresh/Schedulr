package application.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.PasswordField;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginController{
	@FXML
	private VBox VB_Holder;
	@FXML
	private TextField Tx_Uname;
	@FXML
	private PasswordField Tx_Passwd;
	@FXML
	private Button Bt_Login;
	@FXML
	private Button Bt_Signup;
	@FXML
	private Label L_Status;

	// Event Listener on Button[#Bt_Login].onAction
	@FXML
	public void f_Login(ActionEvent event) {
		if(Tx_Uname.getText().equals("") || Tx_Passwd.getText().equals("")){
			L_Status.setText("Empty Username or Password");
			return;
		}
		else {
			try {
				if(Main.authenticate(Tx_Uname.getText(), Tx_Passwd.getText())) {
					L_Status.setText("Login Succeeded");
					String LoadFile = null;
					if(Main.u.getType().equals("Student")) {
						LoadFile = "../GUIs/StudentLogin.fxml";
					}
					else if(Main.u.getType().equals("Faculty")) {
						LoadFile = "/application/GUIs/FacultyLogin.fxml";
					}
					else {
						LoadFile = "../GUIs/AdminPage1.fxml";
					}
					Stage stageTheLabelBelongs = (Stage) L_Status.getScene().getWindow();
				    Parent root=null;
					try {
						root = FXMLLoader.load(LoginController.class.getResource(LoadFile));
					} catch (IOException e) {
						e.printStackTrace();
						return;
					}
				    stageTheLabelBelongs.setScene(new Scene(root));
				    stageTheLabelBelongs.sizeToScene();
				    stageTheLabelBelongs.centerOnScreen();
					return;
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			L_Status.setText("Login Failed");
			return;
		}
	}
	// Event Listener on Button[#Bt_Signup].onAction
	@FXML
	public void f_SignUp(ActionEvent event) {
		Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)event.getSource()).getScene().getWindow();
		Parent root=null;
		try {
			root = FXMLLoader.load(ViewRoomBookings_StudentController.class.getResource("/application/GUIs/SignUp.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		stageTheEventSourceNodeBelongs.setScene(new Scene(root));
	}
}
