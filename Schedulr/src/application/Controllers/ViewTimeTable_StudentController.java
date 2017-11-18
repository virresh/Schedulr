package application.Controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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
import constants.Constants;
import database.CReader;
import database.Slot;
import database.TimeTable;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

public class ViewTimeTable_StudentController implements Initializable{
	@FXML
	private Button Bt_back;
	@FXML
	private ComboBox<String> CB_select;
	@FXML
	private VBox V_backBox;

	private int minCol,maxCol;
	private String[] days = Constants.days;
	private TimeTable tt;
	private GridPane gp = null;

	@FXML
	public void f_updatedTT(ActionEvent event) {
		try {
			Main.updateUser();
			Main.requestTimeTable();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		V_backBox.getChildren().remove(gp);
		if(CB_select.getSelectionModel().getSelectedItem().equals("All")) {
			tt = Main.tt;
		}
		else {
			tt = Main.tt.getPersonal(Main.u);
		}
		buildTimeTable();		

		V_backBox.getChildren().add(gp);
	}

	// Event Listener on Button[#Bt_back].onAction
	@FXML
	public void f_back(ActionEvent event) {
		// back action
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

	void buildTimeTable() {
		gp = new GridPane();
		getColBounds();
		gp.gridLinesVisibleProperty().set(true);
		//		gp.paddingProperty().set(new Insets(50,10,10,10));
		for(int i=minCol; i<maxCol; i++) {
			String time = i/2+":";
			if(i%2==1) {
				time= time+"30";
			}
			else {
				time = time+"00";
			}
			Label l2 = new Label(time);
			l2.setRotate(270);
			l2.setMinHeight(50);
			gp.add(l2, i+1, 0);
		}
		int row = 1; 
		for(String i:days) {
			List<Slot> l = tt.getSlots(i);			// get list of slots of i-th Day
			Label l1 = new Label(i);	// make a label of the day and add to grid
			Pane pD = new Pane();
			pD.getChildren().add(l1);
			//l1.setStyle("-fx-text-fill: red ;");
			pD.setStyle("-fx-background-color: yellow;-fx-border-color: black;");

			int maxRow =1;
			int[] rowWidth  = new int[49];
			int[] maxRowWidth  = new int[49];
			for(Slot j: l) {
				int calcCol = getSlotNum(j.getStartTime())+1;
				int endCol = getSlotNum(j.getEndTime()) +1;
				for(int p=calcCol; p<endCol; p++) {
					maxRowWidth[p]++;
					if(maxRowWidth[p]>maxRow) { maxRow = maxRowWidth[p]; }
				}
			}
			gp.add(pD,0,row,1,maxRow);	// spanning maxRow rows and 1 col

			for(Slot j: l) {
				Label x = new Label(j.toString());
				int calcCol = getSlotNum(j.getStartTime())+1;
				int endCol = getSlotNum(j.getEndTime()) +1;
				int spanCol = endCol - calcCol;
				//System.out.println(i+" "+j.getCode()+" "+calcCol+ " " + spanCol);
				int rowSpan,calcRNum=1,tmax=1;
				x.setStyle("-fx-text-fill: white;-fx-stroke-width: 4;-fx-stroke: black;");// + hashNegColor(j.getSubject()) + ";");
				//				x.autosize();
				FlowPane p = new FlowPane();
				p.setStyle("-fx-background-color: "+hashColor(j.getSubject()+j.getCode())+";-fx-border-color: black");
				p.getChildren().add(x);
				//				p.autosize();
				p.setPrefWrapLength(40);
				p.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Time Table Information");
						alert.setHeaderText(j.getSubject());
						alert.getDialogPane().setContent(new Label(j.getDetails()));
						alert.showAndWait();
					}
				});
				for(int q=calcCol; q<endCol; q++) {
					rowWidth[q]++;
					if(calcRNum < rowWidth[q]) {
						calcRNum = rowWidth[q];
					}
					if(tmax < maxRowWidth[q]) {
						tmax = maxRowWidth[q];
					}
				}

				rowSpan = maxRow/tmax; // this is the maximum rows that a slot can span

				gp.add(p, calcCol, row+calcRNum-1, spanCol,rowSpan);
			}
			row+=maxRow;
		}
		//		gp.setHgap(1);
		//		gp.setVgap(1);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		CB_select.getItems().add("All");
		CB_select.getItems().add("Personalized");

		// Makes the timeTable magically based of the TimeTable HashMap.
		try {
			Main.requestTimeTable();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			Main.exit();
		}
		tt = Main.tt;
		buildTimeTable();
		V_backBox.getChildren().add(gp);
	}

	private void getColBounds() {
		minCol = -1;
		maxCol = -1;
		int[] slotDays = new int[49];
		for (String i:days) {
			List<Slot> p = tt.getSlots(i);
			for(Slot j:p) {
				int Sindex = getSlotNum(j.getStartTime());
				int Eindex = getSlotNum(j.getEndTime());
				for(int k=Sindex; k<=Eindex; k++) {
					slotDays[k]++;
				}

			}
		}
		for(int i=0; i<49; i++) {
			if(slotDays[i]!=0) {
				if(minCol == -1) {
					minCol = i;
				}
				maxCol = i;
			}
		}
	}

	private int getSlotNum(int time) {
		// converts time into slot number
		int sN = (time/100) * 2;
		if(time%100 >= 30) {
			sN +=1;
		}
		return sN;
	}

	private String hashColor(String code) {
		int intColor = code.hashCode();
		String hexColor = String.format("#%06X", (0xFFFFFF & intColor));
		return hexColor;
	}
}
