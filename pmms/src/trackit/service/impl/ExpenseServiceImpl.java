/*
 * JCatalog Project
 */
package trackit.service.impl;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import trackit.businessobjects.Expense;
import trackit.businessobjects.ExpenseItem;
import trackit.dao.ExpenseDao;
import trackit.dao.ExpenseItemDao;
import trackit.service.ExpenseService;
import trackit.service.UserService;


/**
 * The implementation of the <code>UserService</code>.
 * </p>
 * Spring Framework is used to manage this service bean.
 * Since this class is not dependend on Spring API, it can be used outside the Spring IOC container.
 *
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 * @see UserService
 */
public class ExpenseServiceImpl implements ExpenseService {
	//the logger for this class
	private Log logger = LogFactory.getLog(this.getClass());

	private ExpenseDao expenseDao;

    private ExpenseItemDao expenseItemDao;

	/*public void setSmtpHost(String host) {
		this.smtpHost = host;
	}

	public void setDefaultSenderAddress(String newDefaultSenderAddress) {
		this.defaultSenderAddress = newDefaultSenderAddress;
	}

	public void setDefaultSenderName(String newDefaultSenderName) {
		this.defaultSenderName = newDefaultSenderName;
	}

	public void setReceiverAddresses(List newDefaultReceiverAddresses) {
		this.receiverAddresses = newDefaultReceiverAddresses;
	}*/

	public void setExpenseDao(ExpenseDao newExpenseDao) {
		this.expenseDao = newExpenseDao;
	}

    public void setExpenseItemDao(ExpenseItemDao dao) {
        this.expenseItemDao = dao;
    }

    public Expense saveExpense(Expense expense) {
        Expense savedExpense = expenseDao.saveExpense(expense);
        return savedExpense;
    }

    public Expense getExpense(String id) {
        return this.expenseDao.getExpense(id);
    }

    public List<Expense> getAllExpensesForAccount(String accountId) {
        return expenseDao.getAllExpensesForAccount(accountId);
    }

    public List<Expense> getExpensesForAccount(String accountId, Date fromDate, Date toDate) {
        return expenseDao.getExpensesForAccount(accountId, fromDate, toDate);
    }

    public ExpenseItem saveExpenseItem(ExpenseItem expense) {
        ExpenseItem savedExpense = expenseItemDao.saveExpenseItem(expense);
        return savedExpense;
    }

    public ExpenseItem getExpenseItem(String id) {
        return this.expenseItemDao.getExpenseItem(id);
    }

    public List<ExpenseItem> getAllExpenseItemsForExpense(String expenseId) {
        return expenseItemDao.getAllExpenseItemsForExpense(expenseId);
    }

    public List<Expense> getAllExpensesForBankAccount(String id, Date fromDate, Date toDate) {
        return expenseDao.getAllExpensesForBankAccount(id, fromDate, toDate);
    }

    public List<Expense> getAllExpensesForCreditCard(String id, Date fromDate, Date toDate) {
        return expenseDao.getAllExpensesForCreditCard(id, fromDate, toDate);
    }

    public List<Expense> getAllExpensesForExpenseGroup(String groupId) {
        return expenseDao.getExpensesForExpenseGroup(groupId);
    }

    public void deleteExpense(String id) {
        expenseDao.delete(id);
    }
    
    public void deleteExpenseItem(ExpenseItem item) {
    	expenseItemDao.delete(item);
    }
    
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
 		   String projectId) {
    	return expenseDao.getExpenses(accountId, 
    			userId, 
    			startDate, 
    			endDate, 
    			payeeId, 
    			expensePurposeId,
    			expenseCategoryId,
    			expenseTypeId, 
    			paymentMethodId, 
    			bankAccountId, 
    			creditCardId, 
    			projectId);
    }
    
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
	   		   String projectId) {
    	return expenseDao.findExpensesByExpenseItems(accountId, userId, startDate, endDate, payeeId, expensePurposeId, 
    				expenseCategoryId, expenseTypeId, paymentMethodId, bankAccountId, creditCardId, projectId);
    }

}
