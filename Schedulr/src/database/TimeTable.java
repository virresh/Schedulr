package database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class TimeTable implements Serializable {
	 /**
	 * 
	 */
	private static final long serialVersionUID = -7389886342572778766L;
	private HashMap<String,List<Slot>> hm;
	
	TimeTable(){
		 hm = new HashMap<String,List<Slot>>();
		 hm.put("Monday", new ArrayList<Slot>());
		 hm.put("Tuesday", new ArrayList<Slot>());
		 hm.put("Wednesday", new ArrayList<Slot>());
		 hm.put("Thursday", new ArrayList<Slot>());
		 hm.put("Friday", new ArrayList<Slot>());
		 hm.put("Saturday", new ArrayList<Slot>());
		 hm.put("Sunday", new ArrayList<Slot>());
	 }
	 
	 public void addSlot(String day,Slot t) {
		 if(hm.containsKey(day)) {
			 hm.get(day).add(t);
		 }
		 else {
			 throw new NullPointerException("No such Day found.");
		 }
	 }
	 
	 public List<Slot> getSlots(String day){
		 if(hm.containsKey(day)) {
			 List<Slot> l = hm.get(day);
			 Collections.sort(l);
			 return l;
		 }
		 else {
			 throw new NullPointerException("No such Day found");
		 }
	 }
	 
	 public List<Slot> getAllSlots(){
		 List<Slot> p = new ArrayList<Slot>();
		 for(List<Slot> i : hm.values()) {
			 for(Slot x:i) {
				 p.add(x);
			 }
		 }
		 return p;
	 }
	 
	 public Set<String> getKeySet(){
		 return hm.keySet();
	 }
	 
	 public TimeTable getPersonal(User x) {
		 TimeTable o = new TimeTable();
		 for(String day: hm.keySet()) {
			 for(Slot l : hm.get(day)) {
				 if(x.hasRegistered(l.getCode())) {
					 o.addSlot(day, l);
				 }
			 }
		 }
		 return o;
	 }
	 
	 public String hasClash(Slot x) {
		 if(x.getSlotType().equals("Default")) {
			 return "";
		 }
		 else if(x.getSlotType().equals("ExtraSlot")) {
			 ExtraSlot y = (ExtraSlot)x;
			 for(Slot b : hm.get(y.getDay())) {
				 if(b.clashes(y)) {
					 String[] venueB = b.getVenue().split(";");
					 String[] venueY = y.getVenue().split(";");
					 for(int i=0; i<venueB.length; i++)
					 {
						 for(int j=0; j<venueY.length; j++) {
							 if(venueB[i].equals(venueY[j])){
								 return b.getCode();
							 }
						 }
					 }
				 }
			 }
			 return null; 
		 }
		 return "";
	 }
	 
}
