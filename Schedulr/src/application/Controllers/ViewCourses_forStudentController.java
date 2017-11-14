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
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import database.Course;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

public class ViewCourses_forStudentController implements Initializable{
	@FXML
	private Button Bt_back;
	@FXML
	private TextField Tf_search;
	@FXML
	private Button Bt_Search;
	@FXML
	private ComboBox CB_criterion;
	@FXML
	private Button Bt_logout;
	@FXML
	private Button Bt_register;
	@FXML
	private ListView Lv_courses;

	// Event Listener on Button[#Bt_back].onAction
	@FXML
	public void f_back(ActionEvent event) {
		// TODO Autogenerated
		Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)event.getSource()).getScene().getWindow();
		Parent root=null;
		try {
			root = FXMLLoader.load(ViewRoomBookingsController.class.getResource("/application/GUIs/StudentLogin.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		stageTheEventSourceNodeBelongs.setScene(new Scene(root));
	}
	// Event Listener on Button[#Bt_Search].onAction
	@FXML
	public void f_search(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#Bt_logout].onAction
	@FXML
	public void f_logout(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#Bt_register].onAction
	@FXML
	public void f_register(ActionEvent event) {
		// TODO Autogenerated
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		List<Course> l = null;
		try {
			l = Main.requestCourses();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Main.exit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Main.exit();
		}
		ObservableList<Course> x = FXCollections.observableArrayList(l);
		Lv_courses.setItems(x);
	}
}
