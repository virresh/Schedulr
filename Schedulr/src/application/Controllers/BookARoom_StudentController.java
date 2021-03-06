package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Main;
import constants.Constants;
import database.ExtraSlot;
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
    private TextField Tx_capacity;

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
    
    private ExtraSlot exS;
    private String email;

    public ExtraSlot getExSl() {
    	exS = makeSlot();
    	return exS;
    }
    public void setEditAble(ExtraSlot q) {   
    	exS = null;
    	email = q.getType();
    	Tx_purpose.setText(q.getSubject());
    	Tx_capacity.setText(""+q.getCapacity());
    	
    	venues = new ArrayList<String>();
    	String[] x = q.getVenue().split(";");
    	for(String p: x) {
    		venues.add(p);
    	}
    	L_venues.setText(generateVLabel());
    	CB_daysList.getSelectionModel().select(q.getDay());

    	
    	int timiInd = (q.getStartTime()/100)*2 + ((q.getStartTime()%100 == 30)?1:0) ;
    	int timeEnd = (q.getEndTime()/100 - q.getStartTime()/100)*2 + ((q.getEndTime()%100)/30 - (q.getStartTime()%100)/30) - 1;
    	CB_timings.getSelectionModel().select(timiInd);
    	f_setDuration(null);
    	CB_duration.getSelectionModel().select(timeEnd);
    	
    	Tx_capacity.setDisable(true);
    	Tx_purpose.setDisable(true);
    	CB_daysList.setDisable(true);
    	CB_timings.setDisable(true);
    	CB_duration.setDisable(true);
    	Bt_back.setVisible(false);
    	Bt_submit.setVisible(false);

    }
    
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
    
    private ExtraSlot makeSlot() {
    	if(CB_timings.getSelectionModel().isEmpty() || CB_duration.getSelectionModel().isEmpty() || Tx_purpose.getText().isEmpty() || L_venues.getText().isEmpty() || CB_daysList.getSelectionModel().isEmpty() || (!isInteger(Tx_capacity.getText()))) {
    		return null;
    	}
    	int sTime = Integer.parseInt(CB_timings.getSelectionModel().getSelectedItem().split(":")[0])*100 + Integer.parseInt(CB_timings.getSelectionModel().getSelectedItem().split(":")[1]);
    	int minutes = (CB_duration.getSelectionModel().getSelectedIndex() + 1) * 30;
    	int eTime = (sTime/100 + (minutes/60)) * 100 + ((minutes%60 + sTime%100)/60) *100 + (minutes%60 + sTime%100)%60;
    	ExtraSlot xs = new ExtraSlot(sTime,eTime,Tx_purpose.getText(),email,L_venues.getText(),"OTHER",CB_daysList.getSelectionModel().getSelectedItem(),Integer.parseInt(Tx_capacity.getText()));
    	return xs;
    }
    
    private boolean isInteger(String x) {
    	try {
    		Integer.parseInt(x);
    	}catch (NumberFormatException e) {
    		return false;
    	}
    	return true;
    }
    
    @FXML
    void f_submit(ActionEvent event) throws ClassNotFoundException, IOException {
    	if(CB_timings.getSelectionModel().isEmpty() || CB_duration.getSelectionModel().isEmpty() || Tx_purpose.getText().isEmpty() || L_venues.getText().isEmpty() || CB_daysList.getSelectionModel().isEmpty() || (!isInteger(Tx_capacity.getText()))) {
    		L_status.setText("Incomplete Information !");
    		return;
    	}
    	
    	ExtraSlot xs = makeSlot();
    	if(Main.requestRoomBooking(xs)) {
    		if(Main.u.getType().equals("Student")) {
    			L_status.setText("Request Registered Successfully");
    		}
    		else {
    			L_status.setText("Room Booked Successfully");
    		}
    	}
    	else {
    		L_status.setText("Booking Failed");
    	}
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
		email = Main.u.getEmail();
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
