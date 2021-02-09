/*
 * JCatalog Project
 */
package trackit.service;

import java.util.Date;
import java.util.List;
import trackit.businessobjects.Expense;
import trackit.businessobjects.ExpenseItem;


/**
 * The user business service interface.
 * <p>
 * It contains all user management related business logic.
 *
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 */
public interface ExpenseService {   

    public Expense saveExpense(Expense ex);
    public Expense getExpense(String id);
    public List<Expense> getAllExpensesForAccount(String accountId);
    public List<Expense> getExpensesForAccount(String accountId, Date fromDate, Date toDate);

    public ExpenseItem saveExpenseItem(ExpenseItem ex);
    public ExpenseItem getExpenseItem(String id);
    public List<ExpenseItem> getAllExpenseItemsForExpense(String expenseId);

    public List<Expense> getAllExpensesForBankAccount(String id, Date fromDate, Date toDate);
    public List<Expense> getAllExpensesForCreditCard(String id, Date fromDate, Date toDate);

    public List<Expense> getAllExpensesForExpenseGroup(String groupId);

    public void deleteExpense(String id);
    
    public void deleteExpenseItem(ExpenseItem item);
    
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
