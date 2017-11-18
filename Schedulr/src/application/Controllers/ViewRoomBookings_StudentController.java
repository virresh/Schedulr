package application.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import application.Main;
import constants.Constants;
import database.ExtraSlot;
import database.Slot;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ViewRoomBookings_StudentController implements Initializable{
	@FXML
	private Button Bt_back;
	
	@FXML
	private ListView<Slot> LV_bookings;
	
	@FXML
    private Button Bt_view;

    @FXML
    private Button Bt_cancelBooking;

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
		Constants.sizeit(stageTheEventSourceNodeBelongs);
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		List<Slot> h = null;
		try {
			h = Main.getBookings();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			Main.exit();
		}
		
		ObservableList<Slot> p = FXCollections.observableArrayList(h);
		LV_bookings.setItems(p);
	}
	
	@FXML
    void f_cancelBooking(ActionEvent event) {
		if(LV_bookings.getSelectionModel().isEmpty()) {
    		L_status.setText("No Booking Selected !");
    		return;
    	}
    	
    	Slot h = (Slot)LV_bookings.getSelectionModel().getSelectedItem();
    	try {
			Main.deleteBookings(h);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	List<Slot> h1 = null;
		try {
			h1 = Main.getBookings();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			Main.exit();
		}
		
		ObservableList<Slot> p = FXCollections.observableArrayList(h1);
		LV_bookings.setItems(p);
		
    }

    @FXML
    void f_viewBooking(ActionEvent event) {
    	if(LV_bookings.getSelectionModel().isEmpty()) {
    		L_status.setText("No Booking Selected !");
    		return;
    	}
    	
    	ExtraSlot h = (ExtraSlot)LV_bookings.getSelectionModel().getSelectedItem();
    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Booking Information");
		alert.setHeaderText(h.getSubject());
		alert.getDialogPane().setContent(new Label(h.getDetails()));
		alert.showAndWait();
    }
}
