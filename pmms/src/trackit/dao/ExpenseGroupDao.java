/*
 * JCatalog Project
 */
package trackit.dao;

import java.util.List;
import trackit.businessobjects.BankAccount;
import trackit.businessobjects.ExpenseGroup;


/**
 * The user DAO interface.
 * <p>
 * This class contains user management related data access logic.
 *
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 */
public interface ExpenseGroupDao {

    public List<ExpenseGroup> getAllExpenseGroupsForAccount(String accountId);


	/**
	 * Get user by username.
	 *
	 * @param username the username
	 * @return the user associated with the username
	 */
    public ExpenseGroup getExpenseGroup(String id);

    public ExpenseGroup saveExpenseGroup(ExpenseGroup account);

    public List findExpenseGroupByDescription(String description);

	public void deleteExpenseGroup(String id);
	
	public int getNumberOfExpensesWithExpenseGroup(String id);	
	public int getNumberOfExpenseItemsWithExpenseGroup(String id);	
	public int getNumberOfIncomesWithExpenseGroup(String id);	
	public int getNumberOfLoansWithExpenseGroup(String id);	
	public int getNumberOfCurrencyOnHandWithExpenseGroup(String id);

}
