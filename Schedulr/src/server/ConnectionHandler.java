package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

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

	@Override
	public void run() {	
		try	{	

			while(true) {
				String x = in.readUTF();
				if(x.equals("Timetable")) {
					out.writeUTF("Acknowleged");
					out.flush();
					do {
						try {
							if(lock.tryLock(500,TimeUnit.MILLISECONDS)) {
								out.writeObject(ServerRunner.tt);
								out.flush();
								lock.unlock();
								break;
							}
						} catch (InterruptedException e) {

						}
					}while(true);					
				}
				else if(x.equals("Signup")) {
					User l = null;
					out.writeUTF("Acknowleged");
					out.flush();
					System.out.println("Reading object");
					do {
						try {
							l = (User)in.readObject();
							System.out.println("Reading object done");
						} catch (Exception e1) {
							e1.printStackTrace();
							System.out.println("Reading object failed");
							break;
						}
					}while(l == null);
					do {
						try {
							if(lock.tryLock(500,TimeUnit.MILLISECONDS)) {
								if(ServerRunner.ul.addUser(l)) {
									out.writeUTF("Success");
									out.flush();
									ServerRunner.saveToDisk();
								}
								else {
									out.writeUTF("Failed");
									out.flush();
								}
								lock.unlock();
								break;
							}
						} catch (InterruptedException e) {

						}
					}while(true);
				}
				else if(x.equals("Login")) {
					out.writeUTF("Acknowleged");
					out.flush();
					System.out.println("Reading ID");
					String email=null;
					String pass = null;
					do {
						email = in.readUTF();
					}while(email == null);
					System.out.println("Reading Pass");
					out.writeUTF("Next");
					out.flush();
					do {
						pass = in.readUTF();
					}while(pass==null);
					do {
						try {
							if(lock.tryLock(500,TimeUnit.MILLISECONDS)) {
								User p = ServerRunner.ul.authenticateUser(email, pass);
								if(p!=null) {
									out.writeUTF("Success");
									out.flush();
									String k = null;
									do {
										k = in.readUTF();
										if(k.equals("SendOK")) {
											out.writeObject(p);
											out.flush();
											break;
										}
									}while(k!=null);
								}
								else {
									out.writeUTF("Failure");
									out.flush();
								}
								lock.unlock();
								break;
							}
						} catch (InterruptedException e) {

						}
					}while(true);
				}
				else if(x.equals("End")) {
					break;
				}
			}		
		} catch (IOException e)	{
			e.printStackTrace();
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
