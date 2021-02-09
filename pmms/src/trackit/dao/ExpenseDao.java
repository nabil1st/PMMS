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
public interface ExpenseDao {

    public List<Expense> getAllExpensesForBankAccount(String id, Date fromDate, Date toDate);

    public List<Expense> getAllExpensesForCreditCard(String id, Date fromDate, Date toDate);
	/**
	 * Get user by username.
	 *
	 * @param username the username
	 * @return the user associated with the username
	 */
    public Expense getExpense(String id);

    public Expense saveExpense(Expense expense);

    public List<Expense> getAllExpensesForAccount(String accountId);

    public List<Expense> getExpensesForAccount(String accountId, Date fromDate, Date toDate);

    public List<Expense> getExpensesForExpenseGroup(String groupId);

    public void delete(String id);
    
    public List<Expense> getExpenses(String accountId,
       	   String userId,
   		   Date startDate,
   		   Date endDate,
   		   String payeeId,
   		   String expensePurposeId,
   		   String expenseCategoryId,
   		   String expenseTypeId,
   		   String paymentMethodId,
   		   String bankAccountId,
   		   String creditCardId,
   		   String projectId);
    
    public List<Object> findExpensesByExpenseItems(String accountId,
	       	   String userId,
	   		   Date startDate,
	   		   Date endDate,
	   		   String payeeId,
	   		   String expensePurposeId,
	   		   String expenseCategoryId,
	   		   String expenseTypeId,
	   		   String paymentMethodId,
	   		   String bankAccountId,
	   		   String creditCardId,
	   		   String projectId);

}
