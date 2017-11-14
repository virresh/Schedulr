package constants;

import java.io.Serializable;

public class RequestObj implements Serializable {
	/**
	 * This is an important class that simplifies communication protocol
	 */
	private static final long serialVersionUID = -3238771585039912986L;
	public String mode;
	public Object x;
	public RequestObj(String a, Object b){
		mode = a;
		x = b;
	}
}
