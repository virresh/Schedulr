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
	 
	 public Set<String> getKeySet(){
		 return hm.keySet();
	 }
	 
	 
}
