/*
 * JCatalog Project
 */
package trackit.exception;

/**
 * Exception thrown when a user tries to use a non-existing username to log in.
 * 
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 * @see CatalogException
 */
public class UsernameNotExistException extends EntityException {
	private String username;

	/**
	 * Constructor with non-existing username.
	 * 
	 * @param newUserName the non-existing username
	 */
	public UsernameNotExistException(String newUsername) {
		super("Username " + newUsername + " does not exist");
		this.username = newUsername;
	}
	
	public String getUsername() {
		return this.username;
	}
}
