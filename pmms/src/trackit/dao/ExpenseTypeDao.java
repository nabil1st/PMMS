/*
 * JCatalog Project
 */
package trackit.dao;

import java.util.List;
import trackit.businessobjects.ExpenseType;


/**
 * The user DAO interface.
 * <p>
 * This class contains user management related data access logic.
 *
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 */
public interface ExpenseTypeDao {

    public List<ExpenseType> getAllExpenseTypesForAccount(String accountId);


	/**
	 * Get user by username.
	 *
	 * @param username the username
	 * @return the user associated with the username
	 */
    public ExpenseType getExpenseType(String id);

    public ExpenseType saveExpenseType(ExpenseType account);

    public List findExpenseTypeByDescription(String description);

}
