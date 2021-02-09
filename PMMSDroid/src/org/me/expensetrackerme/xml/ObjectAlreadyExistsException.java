package org.me.expensetrackerme.xml;

public class ObjectAlreadyExistsException extends Exception {
	private static final long serialVersionUID = -680043758936991189L;

	public ObjectAlreadyExistsException() {
		super();
	}
	
	public ObjectAlreadyExistsException(String message) {
		super(message);
	}
}
