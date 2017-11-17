package server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import constants.RequestObj;
import database.ExtraSlot;
import database.Slot;
import database.User;

/**
 *This class will serve a single client and process it's requests and send appropriate replies.
 * @author Baani Leen and Viresh Gupta
 *
 */

public class ConnectionHandler implements Runnable {

	Socket connection;
	ObjectOutputStream out=null;
	ObjectInputStream in = null;

	static ReentrantLock lock = new ReentrantLock() ;

	ConnectionHandler(Socket s){
		connection = s;
		try {
			out = new ObjectOutputStream(connection.getOutputStream());
			in = new ObjectInputStream(connection.getInputStream()); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {

		}
	}

	/**
	 * This method will process the request from a client.
	 * @param req
	 * @return
	 */
	RequestObj doStuff(RequestObj req) {
		RequestObj response = null;
		do {
			try {
				if(lock.tryLock(500,TimeUnit.MILLISECONDS)) {
					if(req.mode.equals("Timetable")) {
						response = new RequestObj( "Acknowleged",ServerRunner.tt);
						System.out.println("Written TimeTable");
					}
					else if(req.mode.equals("CourseAll")) {
						response = new RequestObj("Acknowleged",ServerRunner.cl.getAllCourses());
					}
					else if(req.mode.equals("UserGet")) {
						User x = (User)req.x;
						User y = ServerRunner.ul.getUser(x);
						response = new RequestObj("Acknowleged",y);
					}
					else if(req.mode.equals("UserPut")) {
						User x = (User)req.x;
						ServerRunner.ul.putUser(x);
						ServerRunner.saveToDisk();
					}
					else if(req.mode.equals("CourseAddDrop")) {
						User x = (User)req.x;
						String c = (String)req.y;
						if(ServerRunner.ul.getUser(x).hasRegistered(c)){
							String res = ServerRunner.ul.getUser(x).deregister(c);
							response = new RequestObj("Deregistration from course "+c+" "+res,ServerRunner.ul.getUser(x));
						}
						else {
							String res = ServerRunner.ul.getUser(x).registerCourse(ServerRunner.cl.getCourse(c));
							response = new RequestObj("Registration from course "+c+" "+res,ServerRunner.ul.getUser(x));
						}
						ServerRunner.saveToDisk();
					}
					else if(req.mode.equals("Login")) {
						String email=null;
						String pass = null;
						String[] pR = (String[])req.x;
						email = pR[0];
						pass = pR[1];

						User p = ServerRunner.ul.authenticateUser(email, pass);
						if(p!=null) {
							response = new RequestObj("Success",p);
						}
						else {
							response = new RequestObj("Failure",null);
						}
					}
					else if(req.mode.equals("Signup")) {
						if(ServerRunner.ul.addUser((User)req.x)) {
							response = new RequestObj("Success",null);
							ServerRunner.saveToDisk();
						}
						else {
							response = new RequestObj("Failure",null);
							out.writeObject(response);
							out.flush();
						}
					}
					else if(req.mode.equals("RoomBookRequest")) {
						User ux = (User)req.y;
						ExtraSlot p = (ExtraSlot)req.x;
						if(ux.getType().equals("Student")) {
							if(ServerRunner.tt.hasClash(p) == null) {
								ServerRunner.bookings.add(p);
								System.out.println("Adding Request To Book Room");
								response = new RequestObj("Acknowleged",null);
								ServerRunner.saveToDisk();
							}
							else {
								response = new RequestObj("Failed",null);
							}
						}
						else {
							if(ServerRunner.tt.hasClash(p) == null) {
								ServerRunner.tt.addSlot(p.getDay(), p);
								response = new RequestObj("Acknowleged",null);
								ServerRunner.saveToDisk();
							}
							else {
								response = new RequestObj("Failed",null);
							}
						}
					}
					else if(req.mode.equals("GetBookings")) {
						User a = (User)req.x;
						List<Slot> bk = new ArrayList<Slot>();
						for(Slot t : ServerRunner.bookings) {
							if(t.getType().equals(a.getEmail()) || a.getType().equals("Admin")) {
								bk.add(t);
							}
							
						}

						for(Slot t: ServerRunner.tt.getAllSlots()) {
							if(t.getType().equals(a.getEmail())) {
								bk.add(t);
							}
							else if(a.getType().equals("Admin") && t.getSlotType().equals("ExtraSlot")) {
								bk.add(t);
							}
						}
						response = new RequestObj("Acknowleged",bk);
					}
					else if(req.mode.equals("CancelBooking")) {
						Slot h = (Slot)req.x;
						try {
							ServerRunner.tt.removeSlot((ExtraSlot) h);
							ServerRunner.bookings.remove(h);
							System.out.println(ServerRunner.bookings.contains(h));
						}catch(Exception ex) {
							ex.printStackTrace();
						}
						ServerRunner.saveToDisk();
					}
					else if(req.mode.equals("AcceptBooking")) {
						ExtraSlot k = (ExtraSlot)req.x;
						String p = ServerRunner.tt.hasClash(k);
						if(p != null) {
							response = new RequestObj("Acknowleged",p);
						}
						else {
							ServerRunner.bookings.remove(k);
							ServerRunner.tt.addSlot(k.getDay(), k);
							ServerRunner.saveToDisk();
							response = new RequestObj("Acknowleged","Success");
						}
					}
					else if(req.mode.equals("GetRequests")) {
						response = new RequestObj("Acknowleged",ServerRunner.bookings);
					}
					else if(req.mode.equals("End")) {

					}
					lock.unlock();
					break;
				}
			} catch (InterruptedException e) {

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}while(true);	
		return response;
	}

	@Override
	public void run() {	
		try	{	

			RequestObj req = null;
			RequestObj response = null;
			while(true) {
				req = (RequestObj) in.readObject();
				response = doStuff(req);
				if(req.mode.equals("End")) {
					break;
				}
				if(response != null) {
					out.reset();
					out.writeObject(response);
					out.flush();
				}
			}		
		} catch (IOException | ClassNotFoundException e)	{
//				e.printStackTrace();
		} finally {
			try {
				System.out.println("Closing connection.");
				connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
