package constants;

import java.io.Serializable;

/**
 * 
 * This class augments communication over sockets.
 * This is the definition of the protocol used for communication over Sockets.
 * This class simplifies the request and response over sockets for any task.
 * Can carry up to three objects.
 * @author Baani Leen and Viresh Gupta
 *
 */

public class RequestObj implements Serializable {
	/**
	 * This is an important class that simplifies communication protocol
	 */
	private static final long serialVersionUID = -3238771585039912986L;
	public String mode;
	public Object x;
	public Object y;
	public RequestObj(String a, Object b){
		mode = a;
		x = b;
		y = null;
	}
	public RequestObj(String a, Object b,Object c){
		mode = a;
		x = b;
		y = c;
	}
}
