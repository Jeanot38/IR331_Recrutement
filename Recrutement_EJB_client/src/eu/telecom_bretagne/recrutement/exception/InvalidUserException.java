package eu.telecom_bretagne.recrutement.exception;

public class InvalidUserException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidUserException(String message) {
		super(message);
	}

}
