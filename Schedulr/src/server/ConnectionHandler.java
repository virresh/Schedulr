package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

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
