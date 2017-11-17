package application.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import database.ExtraSlot;
import database.Slot;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class RespondtoRequestsController implements Initializable{
	@FXML
	private Button Bt_back;
	@FXML
	private Label L_pending;
	@FXML
	private ListView<Slot> LV_requests;
	@FXML
	private Button Bt_accept;
	@FXML
	private Button Bt_reject;
	@FXML
	private Label L_status;

	@FXML
	public void f_edit(ActionEvent event) {
		
	}
	// Event Listener on Button[#Bt_back].onAction
	@FXML
	public void f_back(ActionEvent event) {
		Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)event.getSource()).getScene().getWindow();
		Parent root=null;
		try {
			root = FXMLLoader.load(ViewRoomBookings_StudentController.class.getResource("/application/GUIs/AdminPage1.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		stageTheEventSourceNodeBelongs.setScene(new Scene(root));
		stageTheEventSourceNodeBelongs.sizeToScene();
		stageTheEventSourceNodeBelongs.centerOnScreen();
	}
	// Event Listener on Button[#Bt_accept].onAction
	@FXML
	public void f_accept(ActionEvent event) {
		if(LV_requests.getSelectionModel().isEmpty()) {
			L_status.setText("No Request Selected.");
			return;
		}
		Slot v = LV_requests.getSelectionModel().getSelectedItem();
		try {
			L_status.setText(Main.acceptBookings(v));
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		setup();
	}
	// Event Listener on Button[#Bt_reject].onAction
	@FXML
	public void f_reject(ActionEvent event) {
		if(LV_requests.getSelectionModel().isEmpty()) {
			L_status.setText("No Request Selected.");
			return;
		}
		
		Slot v = LV_requests.getSelectionModel().getSelectedItem();
		try {
			Main.deleteBookings(v);
		} catch (IOException e) {
			L_status.setText("Failure");
			e.printStackTrace();
		}
		setup();		
	}
	
	@FXML
	public void f_viewDetails(ActionEvent event) {
		if(LV_requests.getSelectionModel().isEmpty()) {
			L_status.setText("No Request Selected.");
			return;
		}
		ExtraSlot h = (ExtraSlot)LV_requests.getSelectionModel().getSelectedItem();
    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Booking Information");
		alert.setHeaderText(h.getSubject());
		alert.getDialogPane().setContent(new Label(h.getDetails()));
		alert.showAndWait();
	}
	
	private void setup() {
		List<Slot> o = null;
		try {
			o = Main.getRequests();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		Bt_accept.setDisable(false);
		Bt_reject.setDisable(false);
		ObservableList<Slot> h = FXCollections.observableArrayList(o);
		LV_requests.setItems(h);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setup();
	}
}
