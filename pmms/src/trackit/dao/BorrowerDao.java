/*
 * JCatalog Project
 */
package trackit.dao;

import java.util.List;
import trackit.businessobjects.Borrower;


/**
 * The user DAO interface.
 * <p>
 * This class contains user management related data access logic.
 *
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 */
public interface BorrowerDao {

    public List<Borrower> getAllBorrowersForAccount(String accountId);


	/**
	 * Get user by username.
	 *
	 * @param username the username
	 * @return the user associated with the username
	 */
    public Borrower getBorrower(String id);

    public Borrower saveBorrower(Borrower account);

    public List findBorrowerByName(String name);

}
