/*
 * JCatalog Project
 */
package trackit.dao;

import java.util.Date;
import java.util.List;
import trackit.businessobjects.Expense;
import trackit.businessobjects.ExpenseItem;


/**
 * The user DAO interface.
 * <p>
 * This class contains user management related data access logic.
 *
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 */
public interface ExpenseItemDao {
	/**
	 * Get user by username.
	 *
	 * @param username the username
	 * @return the user associated with the username
	 */
    public ExpenseItem getExpenseItem(String id);

    public ExpenseItem saveExpenseItem(ExpenseItem expense);

    public List<ExpenseItem> getAllExpenseItemsForExpense(String expenseId);
    
    public void delete(ExpenseItem item);
    
    

}
