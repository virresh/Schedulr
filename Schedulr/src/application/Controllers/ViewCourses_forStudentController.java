package application.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import database.Course;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class ViewCourses_forStudentController implements Initializable{
	@FXML
	private Button Bt_back;
	@FXML
	private TextField Tf_search;
	@FXML
	private Button Bt_Search;
	@FXML
	private ComboBox<String> CB_criterion;
	@FXML
	private Button Bt_register;
	@FXML
	private Button Bt_view;
	@FXML
	private ListView<Course> Lv_courses;
	@FXML
	private Label L_status;

	// Event Listener on Button[#Bt_back].onAction
	@FXML
	public void f_back(ActionEvent event) {
		Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)event.getSource()).getScene().getWindow();
		Parent root=null;
		String load = "";
		if(Main.u.getType().equals("Student")) {
			load = "/application/GUIs/StudentLogin.fxml";
		}
		else if(Main.u.getType().equals("Faculty")) {
			load = "/application/GUIs/FacultyLogin.fxml";
		}
		else {
			load = "/application/GUIs/AdminPage1.fxml";
		}
		try {
			root = FXMLLoader.load(ViewRoomBookings_StudentController.class.getResource(load));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		stageTheEventSourceNodeBelongs.setScene(new Scene(root));
		stageTheEventSourceNodeBelongs.sizeToScene();
		stageTheEventSourceNodeBelongs.centerOnScreen();
	}
	// Event Listener on Button[#Bt_Search].onAction
	@FXML
	public void f_search(ActionEvent event) {
		if(CB_criterion.getSelectionModel().isEmpty()){
			L_status.setText("No Criterion Selected");
			return;
		}
		
		List<Course> l = null;
		try {
			l = Main.requestCourses();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			Main.exit();
		} catch (IOException e) {
			e.printStackTrace();
			Main.exit();
		}
		
		String k = Tf_search.getText();
		for (Iterator<Course> iterator = l.iterator(); iterator.hasNext();) {
		    Course b = iterator.next();
		    if (Main.u.hasRegistered(b.getAcronym()) && !CB_criterion.getSelectionModel().getSelectedItem().equals("All")) {
		        iterator.remove();
		    }
		    else if(CB_criterion.getSelectionModel().getSelectedItem().equals("By Postconditions")) {
				if(!b.getPostconditions().contains(k)) {
					iterator.remove();
				}
			}
			else if(CB_criterion.getSelectionModel().getSelectedItem().equals("By Preconditions")) {
				if(!b.getPreconditions().contains(k)) {
					iterator.remove();
				}
			}
		}
		
		ObservableList<Course> x = FXCollections.observableArrayList(l);
		Lv_courses.setItems(x);
		
		L_status.setText("Ta Da: Above are the courses as per your liking :D");
	}
	// Event Listener on Button[#Bt_register].onAction
	@FXML
	public void f_register(ActionEvent event) {
		if(Lv_courses.getSelectionModel().getSelectedItem() == null) {
			L_status.setText("No Course Selected");
			return;
		}
		L_status.setText("");
		Course a = Lv_courses.getSelectionModel().getSelectedItem();
		String p="";
		try {
			p = Main.addDropCourse(a.getAcronym());
			Main.updateUser();
		} catch (ClassNotFoundException | IOException e) {
			L_status.setText("Error");
			e.printStackTrace();
			Main.exit();
		}
		L_status.setText(p);
	}
	
	@FXML
	public void f_showDetails(ActionEvent event) {
		if(Lv_courses.getSelectionModel().getSelectedItem() == null) {
			L_status.setText("No Course Selected");
			return;
		}
		
		L_status.setText("");
		Course a = Lv_courses.getSelectionModel().getSelectedItem();
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Course Information");
		alert.setHeaderText(a.getName());
		alert.getDialogPane().setContent(new Label(a.getDetailedString()));

		alert.showAndWait();

	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		List<Course> l = null;
		try {
			l = Main.requestCourses();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			Main.exit();
		} catch (IOException e) {
			e.printStackTrace();
			Main.exit();
		}
		ObservableList<Course> x = FXCollections.observableArrayList(l);
		Lv_courses.setItems(x);
		
		// Add search options
		CB_criterion.getItems().add("All");
		CB_criterion.getItems().add("By Postconditions");
		CB_criterion.getItems().add("By Preconditions");
		
		CB_criterion.getSelectionModel().select(0);
		
	}
}
