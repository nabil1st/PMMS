/**
 * JCatalog Project
 */
package trackit.servicelocator;

import trackit.service.AccountService;
import trackit.service.BankAccountService;
import trackit.service.BorrowerService;
import trackit.service.CashService;
import trackit.service.CreditCardService;
import trackit.service.CurrencyOnHandService;
import trackit.service.DepositService;
import trackit.service.ExpenseCategoryService;
import trackit.service.ExpenseGroupService;
import trackit.service.ExpenseService;
import trackit.service.ExpenseTypeService;
import trackit.service.IncomeService;
import trackit.service.LoanService;
import trackit.service.MoneyOrderService;
import trackit.service.PayeeService;
import trackit.service.UserService;
import trackit.transaction.service.AccountTransactionalService;
import trackit.transaction.service.BankAccountTransactionalService;
import trackit.transaction.service.CashTransactionalService;
import trackit.transaction.service.CreditCardTransactionalService;
import trackit.transaction.service.DepositTransactionalService;
import trackit.transaction.service.ExpenseTransactionalService;
import trackit.transaction.service.IncomeTransactionalService;
import trackit.transaction.service.LoanTransactionalService;
import trackit.transaction.service.MoneyOrderTransactionalService;
import trackit.transaction.service.PayeeTransactionalService;


/**
 * Interface needs to be implemented by ServiceLocator.
 * <p>
 * ServiceLocator is used to lookup for business services.
 * 
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 */
public interface ServiceLocator {

    public LoanService getLoanService();
	/**
	 * Get the <code>CatalogService</code>.
	 * 
	 * @return the catalog service
	 */
	//public CatalogService getCatalogService();
	
	/**
	 * Get the <code>UserService</code>.
	 * 
	 * @return the user service
	 */
	public UserService getUserService();

    public AccountService getAccountService();

    public BankAccountService getBankAccountService();

    public CreditCardService getCreditCardService();
    
    public ExpenseGroupService getExpenseGroupService();
    public ExpenseTypeService getExpenseTypeService();
    public PayeeService getPayeeService();
    public ExpenseService getExpenseService();

    public IncomeService getIncomeService();

    public DepositService getDepositService();

    public CurrencyOnHandService getCurrencyOnHandService();

    public MoneyOrderService getMoneyOrderService();

    public CashService getCashService();

    public BorrowerService getBorrowerService();

    public AccountTransactionalService getAccountTransactionalService();
    
    public PayeeTransactionalService getPayeeTransactionalService();

    public BankAccountTransactionalService getBankAccountTransactionalService();

    public CreditCardTransactionalService getCreditCardTransactionalService();

    public DepositTransactionalService getDepositTransactionalService();

    public ExpenseTransactionalService getExpenseTransactionalService();

    public IncomeTransactionalService getIncomeTransactionalService();

    public LoanTransactionalService getLoanTransactionalService();

    public MoneyOrderTransactionalService getMoneyOrderTransactionalService();

    public ExpenseCategoryService getExpenseCategoryService();

    public CashTransactionalService getCashTransactionalService();


}
