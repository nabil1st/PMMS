/*
 * JCatalog Project
 */
package trackit.service;

import java.util.List;
import trackit.businessobjects.User;
import trackit.exception.UsernameNotExistException;


/**
 * The user business service interface.
 * <p>
 * It contains all user management related business logic.
 * 
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 */
public interface UserService {

    public List<User> getAllUsersForAccount(String currentAccountId);
	/**
	 * Login a user using username and password.
	 * 
	 * @param username the username to be used
	 * @param password the password to be used
	 * @return the user associated with the username and password
	 * @throws CatalogException
	 */
	public User login(String username, String password) throws UsernameNotExistException;
	
	/**
	 * Send email to the administrator.
	 * 
	 * @param senderAddress the email address of the sender
	 * @param senderName the name of the email sender
	 * @param sub the subject of the email
	 * @param msg the email message
	 * @throws CatalogException
	 */
	//public void sendEmail(String senderAddress, String senderName, String sub, String msg) throws CatalogException;
        
        public User saveUser(User user);
        
        public User findUser(String userName);

        public User findUserByEmail(String email);
}
