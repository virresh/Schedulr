package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import constants.Constants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BookARoom_StudentController implements Initializable {

    @FXML
    private Button Bt_back;

    @FXML
    private ComboBox<String> CB_daysList;

    @FXML
    private ComboBox<String> CB_timings;

    @FXML
    private ComboBox<String> CB_duration;

    @FXML
    private TextField Tx_purpose;

    @FXML
    private ChoiceBox<String> CB_venues;

    @FXML
    private Button Bt_addSub;

    @FXML
    private Button Bt_submit;

    @FXML
    private Label L_status;
    
    @FXML
    private Label L_venues;
    
    private ArrayList<String> venues;

    
    private String generateVLabel() {
    	String x = "";
    	for(String y: venues) {
    		if(x.equals("")) {
    			x = y;
    		}
    		else{
    			x = x+";"+y;
    		}
    	}
    	return x;
    }
    @FXML
    void f_addSub(ActionEvent event) {
    	if(CB_venues.getSelectionModel().isEmpty()) {
    		L_status.setText("No Venue Selected");
    		return;
    	}
    	
    	String k = CB_venues.getSelectionModel().getSelectedItem();
    	if(venues.contains(k)) {
    		venues.remove(k);
    	}
    	else {
    		venues.add(k);
    	}
    	L_venues.setText(generateVLabel());
    	Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	stageTheEventSourceNodeBelongs.sizeToScene();
    }

    @FXML
    void f_back(ActionEvent event) {
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

    @FXML
    void f_submit(ActionEvent event) {

    }
    
    @FXML
    void f_setDuration(ActionEvent event) {
    	String t = CB_timings.getSelectionModel().getSelectedItem();
    	int time = Integer.parseInt(t.split(":")[0])*100 + Integer.parseInt(t.split(":")[1]);
    	ObservableList<String> ol = FXCollections.observableArrayList();
    	int mins=30;
    	for(int k=time; k<2400;) {
    		String h ="";
    		if(mins / 60 > 0) {
    			h = h + mins/60 +" hr ";
    		}
    		if(mins % 60 > 0) {
    			h += (mins % 60) + " mins";
    		}
    		ol.add(h);
    		mins+=30;
    		if(k%100 == 0) {
    			k+=30;
    		}
    		else {
    			k= k+100-30;
    		}
    	}
    	
    	CB_duration.setItems(ol);
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		L_venues.setText("");
		venues = new ArrayList<String>();
		CB_daysList.setItems(FXCollections.observableArrayList(Constants.days));
		CB_daysList.getSelectionModel().select(0);
		CB_venues.setItems(FXCollections.observableArrayList(Constants.venues));
		CB_venues.getSelectionModel().select(0);
		ObservableList <String> times = FXCollections.observableArrayList();
		
		for(int i=0; i<48; i++) {
			String time = "" + (i/2) + ":" + ((i%2==0)?"00":"30");
			times.add(time);
		}
		CB_timings.setItems(times);		
	}

}
