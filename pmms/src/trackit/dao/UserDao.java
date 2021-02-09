/*
 * JCatalog Project
 */
package trackit.dao;

import java.util.List;
import trackit.businessobjects.User;


/**
 * The user DAO interface.
 * <p>
 * This class contains user management related data access logic.
 * 
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 */
public interface UserDao {

    public List<User> getAllUsersForAccount(String currentAccountId);
	/**
	 * Get user by username.
	 * 
	 * @param username the username
	 * @return the user associated with the username
	 */
    public User getUser(String id);

    public User saveUser(User user);
    
    public List findUserByEmail(String userName);
}
