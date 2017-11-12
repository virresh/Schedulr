package server;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import constants.Constants;

public class TestClient {

	static Socket server = null;
	static Integer time;
	static void demandTimeTable() throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
		out.writeUTF("getNum");
		out.flush();
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String	serverName = Constants.address; //or remote IP Address		
		/*	Connect	to	server	that	is	already	listening	*/

		try {
			server	=	new	Socket(serverName,	Constants.port);	
			System.out.println("Just connected to "+server.getRemoteSocketAddress());

			demandTimeTable();

			ObjectInputStream in = new ObjectInputStream(server.getInputStream());
			while(true) {
				String y=null;
				try {
					y = in.readUTF();
				}
				catch(EOFException e) {
					break;
				}
				if(y.equals("End")) {
					break;
				}
				System.out.println("Server says " + y);
			}	
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
