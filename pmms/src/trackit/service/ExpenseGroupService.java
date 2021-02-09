/*
 * JCatalog Project
 */
package trackit.service;

import java.util.List;
import trackit.businessobjects.ExpenseGroup;

/**
 * The user business service interface.
 * <p>
 * It contains all user management related business logic.
 *
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 */
public interface ExpenseGroupService {
    public ExpenseGroup saveExpenseGroup(ExpenseGroup accnt);
    public ExpenseGroup findExpenseGroup(String id);
    public Object findExpenseGroupByDescription(String description);
    public void deleteExpenseGroup(String id);

    public List<ExpenseGroup> getAllExpenseGroupsForAccount(String accountId);
    public int getNumberOfExpensesWithExpenseGroup(String id);
    public int getNumberOfExpenseItemsWithExpenseGroup(String id);	
	public int getNumberOfIncomesWithExpenseGroup(String id);	
	public int getNumberOfLoansWithExpenseGroup(String id);	
	public int getNumberOfCurrencyOnHandWithExpenseGroup(String id);
}
