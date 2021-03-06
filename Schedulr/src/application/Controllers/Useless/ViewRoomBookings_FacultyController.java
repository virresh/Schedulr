package application.Controllers.Useless;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

import javafx.event.ActionEvent;

import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class ViewRoomBookings_FacultyController {
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
			root = FXMLLoader.load(ViewRoomBookingsController.class.getResource("/application/GUIs/FacultyLogin.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		stageTheEventSourceNodeBelongs.setScene(new Scene(root));
	}
}
