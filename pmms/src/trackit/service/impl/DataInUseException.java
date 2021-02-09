package trackit.service.impl;

public class DataInUseException extends Exception {
	private static final long serialVersionUID = -4867370520523459437L;

	public DataInUseException(String message) {
		super(message);
	}
}
