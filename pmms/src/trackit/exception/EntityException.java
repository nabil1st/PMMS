/*
 * JCatalog Project
 */
package trackit.exception;

/**
 * Base checked exception for the JCatalog Project.
 * 
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 */
public class EntityException extends Exception{
	/**
	 * Constructor with error message.
	 * 
	 * @param msg the error message associated with the exception
	 */
	public EntityException(String msg) {
		super(msg);
	}
	
	/**
	 * Constructor with error message and root cause.
	 * 
	 * @param msg the error message associated with the exception
	 * @param cause the root cause of the exception
	 */
	public EntityException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
