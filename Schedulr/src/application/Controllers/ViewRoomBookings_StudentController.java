package application.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import database.CReader;
import database.Slot;
import database.TimeTable;
import javafx.event.ActionEvent;

import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ViewRoomBookings_StudentController{
	@FXML
	private Button Bt_back;
	
	@FXML
	private ListView LV_bookings;
	
	
	// Event Listener on Button[#Bt_back].onAction
	@FXML
	public void f_back(ActionEvent event) {
		Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)event.getSource()).getScene().getWindow();
		Parent root=null;
		try {
			root = FXMLLoader.load(ViewRoomBookings_StudentController.class.getResource("/application/GUIs/StudentLogin.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		stageTheEventSourceNodeBelongs.setScene(new Scene(root));
	}
	
}
