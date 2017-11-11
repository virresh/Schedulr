package application.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import database.CReader;
import database.Slot;
import database.TimeTable;
import javafx.event.ActionEvent;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class ViewTimeTable_StudentController implements Initializable{
	@FXML
	private Button Bt_back;
	@FXML
	private ComboBox CB_select;
	@FXML
	private VBox V_backBox;
	
	private int minCol,maxCol;
	private String[] days = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
	private TimeTable tt;
	
	// Event Listener on Button[#Bt_back].onAction
	@FXML
	public void f_back(ActionEvent event) {
		// back action
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
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Makes the timeTable magically based of the TimeTable HashMap.
		tt = Main.tt;
		getColBounds();
		GridPane gp = new GridPane();
		gp.gridLinesVisibleProperty().set(true);
		gp.paddingProperty().set(new Insets(50,10,10,10));
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
				FlowPane p = new FlowPane();
				p.setStyle("-fx-background-color: "+hashColor(j.getSubject()+j.getCode())+";-fx-border-color: black");
				p.getChildren().add(x);
				
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
