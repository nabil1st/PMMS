/*
 * JCatalog Project
 */
package trackit.dao;

import java.util.List;
import trackit.businessobjects.ExpenseCategory;
import trackit.businessobjects.ExpenseSubType;


/**
 * The user DAO interface.
 * <p>
 * This class contains user management related data access logic.
 *
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 */
public interface ExpenseCategoryDao {

    public List<ExpenseCategory> getAllExpenseCategoriesForAccount(String accountId);


	/**
	 * Get user by username.
	 *
	 * @param username the username
	 * @return the user associated with the username
	 */
    public ExpenseCategory getExpenseCategory(String id);

    public ExpenseSubType getExpenseSubType(String id);

    public ExpenseCategory saveExpenseCategory(ExpenseCategory account);

    public ExpenseSubType saveExpenseSubType(ExpenseSubType type);

    public List findExpenseCategoryByDescription(String description);

}
