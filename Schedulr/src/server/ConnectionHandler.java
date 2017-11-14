package server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import constants.RequestObj;
import database.User;

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
	
	RequestObj doStuff(RequestObj req) {
		RequestObj response = null;
		do {
			try {
				if(lock.tryLock(500,TimeUnit.MILLISECONDS)) {
					if(req.mode.equals("Timetable")) {
						response = new RequestObj( "Acknowleged",ServerRunner.tt);
						System.out.println("Written TimeTable");
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
				out.writeObject(response);
				out.flush();
			}		
		} catch (IOException | ClassNotFoundException e)	{
//			e.printStackTrace();
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
