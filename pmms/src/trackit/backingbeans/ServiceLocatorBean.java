/**
 * JCatalog Project
 */
package trackit.backingbeans;

import javax.servlet.ServletContext;
//
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
//
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
import trackit.servicelocator.ServiceLocator;
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
import trackit.util.FacesUtils;

/**
 * The implementation of <code>ServiceLocator</code>.
 * <p>
 * This class is managed by the JSF managed bean facility,
 * and is set with application scope.
 * 
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 * @see ServiceLocator
 */
public class ServiceLocatorBean implements ServiceLocator {

    //the user service bean name
	private static final String USER_SERVICE_BEAN_NAME = "userService";
    private static final String ACCOUNT_SERVICE_BEAN_NAME = "accountService";
    private static final String BANK_ACCOUNT_SERVICE_BEAN_NAME = "bankAccountService";
    private static final String CREDIT_CARD_SERVICE_BEAN_NAME = "creditCardService";
    private static final String EXPENSE_GROUP_SERVICE_BEAN_NAME = "expenseGroupService";
    private static final String EXPENSE_TYPE_SERVICE_BEAN_NAME = "expenseTypeService";
    private static final String EXPENSE_CATEGORY_SERVICE_BEAN_NAME = "expenseCategoryService";
    private static final String PAYEE_SERVICE_BEAN_NAME = "payeeService";
    private static final String EXPENSE_SERVICE_BEAN_NAME = "expenseService";
    private static final String INCOME_SERVICE_BEAN_NAME = "incomeService";
    private static final String DEPOSIT_SERVICE_BEAN_NAME = "depositService";
    private static final String CURRENCY_OH_HAND_SERVICE_BEAN_NAME = "currencyOnHandService";
    private static final String MONEY_ORDER_SERVICE_BEAN_NAME = "moneyOrderService";
    private static final String CASH_SERVICE_BEAN_NAME = "cashService";
    private static final String LOAN_SERVICE_BEAN_NAME = "loanService";
    private static final String BORROWER_SERVICE_BEAN_NAME = "borrowerService";
    private static final String ACCOUNT_TRANSACTIONAL_SERVICE = "accountTransactionalService";
    private static final String PAYEE_TRANSACTIONAL_SERVICE = "payeeTransactionalService";
    private static final String BANK_ACCOUNT_TRANSACTIONAL_SERVICE = "bankAccountTransactionalService";
    private static final String CREDIT_CARD_TRANSACTIONAL_SERVICE = "creditCardTransactionalService";
    private static final String DEPOSIT_TRANSACTIONAL_SERVICE = "depositTransactionalService";
    private static final String EXPENSE_TRANSACTIONAL_SERVICE = "expenseTransactionalService";
    private static final String INCOME_TRANSACTIONAL_SERVICE = "incomeTransactionalService";
    private static final String LOAN_TRANSACTIONAL_SERVICE = "loanTransactionalService";
    private static final String MONEY_ORDER_TRANSACTIONAL_SERVICE = "moneyOrderTransactionalService";
    private static final String CASH_TRANSACTIONAL_SERVICE = "cashTransactionalService";
	
	//the logger for this class
	private Log logger = LogFactory.getLog(this.getClass());
	
	//the Spring application context
	private ApplicationContext appContext;
	
	//the cached catalog service
	//private CatalogService catalogService;
	
	//the cached user service
	private UserService userService;

    private AccountService accountService;

    private BankAccountService bankAccountService;

    private CreditCardService creditCardService;

    private ExpenseGroupService expenseGroupService;
    private ExpenseTypeService expenseTypeService;    
    private PayeeService payeeService;
    private ExpenseService expenseService;

    private IncomeService incomeService;

    private DepositService depositService;

    private CurrencyOnHandService currencyOnHandService;

    private MoneyOrderService moneyOrderService;

    private CashService cashService;

    private LoanService loanService;

    private BorrowerService borrowerService;

    private AccountTransactionalService accountTransactionalService;
    
    private PayeeTransactionalService payeeTransactionalService;

    private BankAccountTransactionalService bankAccountTransactionalService;

    private CreditCardTransactionalService creditCardTransactionalService;

    private DepositTransactionalService depositTransactionalService;

    private ExpenseTransactionalService expenseTransactionalService;

    private IncomeTransactionalService incomeTransactionalService;

    private LoanTransactionalService loanTransactionalService;

    private MoneyOrderTransactionalService moneyOrderTransactionalService;

    private ExpenseCategoryService expenseCategoryService;
    
    private CashTransactionalService cashTransactionalService;

	/**
	 * Constructor.
	 * <p>
	 * The following steps being done:
	 * <ul>
	 * <li>retrieve Spring application context from servlet context.
	 * <li>look up <code>CatalogService</code> from Spring application context.
	 * <li>look up <code>UserService</code> from Spring applicatino context.
	 * </ul>
	 */
	public ServiceLocatorBean() {
		ServletContext context = FacesUtils.getServletContext();
		this.appContext = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		//this.catalogService = (CatalogService)this.lookupService(CATALOG_SERVICE_BEAN_NAME);
		this.userService = (UserService)this.lookupService(USER_SERVICE_BEAN_NAME);
        this.accountService = (AccountService)this.lookupService(ACCOUNT_SERVICE_BEAN_NAME);
        this.bankAccountService = (BankAccountService)this.lookupService(BANK_ACCOUNT_SERVICE_BEAN_NAME);
        this.creditCardService = (CreditCardService)this.lookupService(CREDIT_CARD_SERVICE_BEAN_NAME);
        this.expenseGroupService = (ExpenseGroupService)this.lookupService(EXPENSE_GROUP_SERVICE_BEAN_NAME);
        this.expenseTypeService = (ExpenseTypeService)this.lookupService(EXPENSE_TYPE_SERVICE_BEAN_NAME);
        this.payeeService = (PayeeService)this.lookupService(PAYEE_SERVICE_BEAN_NAME);
        this.expenseService = (ExpenseService)this.lookupService(EXPENSE_SERVICE_BEAN_NAME);
        this.incomeService = (IncomeService)this.lookupService(INCOME_SERVICE_BEAN_NAME);
        this.depositService = (DepositService)this.lookupService(DEPOSIT_SERVICE_BEAN_NAME);
        this.currencyOnHandService = (CurrencyOnHandService) this.lookupService(CURRENCY_OH_HAND_SERVICE_BEAN_NAME);
        this.moneyOrderService = (MoneyOrderService) this.lookupService(MONEY_ORDER_SERVICE_BEAN_NAME);
        this.cashService = (CashService) this.lookupService(CASH_SERVICE_BEAN_NAME);
        this.loanService = (LoanService) this.lookupService(LOAN_SERVICE_BEAN_NAME);
        this.borrowerService = (BorrowerService) this.lookupService(BORROWER_SERVICE_BEAN_NAME);
		this.accountTransactionalService = (AccountTransactionalService) this.lookupService(ACCOUNT_TRANSACTIONAL_SERVICE);
		this.payeeTransactionalService = (PayeeTransactionalService) this.lookupService(PAYEE_TRANSACTIONAL_SERVICE);
        this.bankAccountTransactionalService = (BankAccountTransactionalService) this.lookupService(BANK_ACCOUNT_TRANSACTIONAL_SERVICE);
        this.creditCardTransactionalService = (CreditCardTransactionalService) this.lookupService(CREDIT_CARD_TRANSACTIONAL_SERVICE);
        this.depositTransactionalService = (DepositTransactionalService) this.lookupService(DEPOSIT_TRANSACTIONAL_SERVICE);
        this.expenseTransactionalService = (ExpenseTransactionalService) this.lookupService(EXPENSE_TRANSACTIONAL_SERVICE);
        this.incomeTransactionalService = (IncomeTransactionalService) this.lookupService(INCOME_TRANSACTIONAL_SERVICE);
        this.loanTransactionalService = (LoanTransactionalService) this.lookupService(LOAN_TRANSACTIONAL_SERVICE);
        this.moneyOrderTransactionalService = (MoneyOrderTransactionalService) this.lookupService(MONEY_ORDER_TRANSACTIONAL_SERVICE);
        this.expenseCategoryService = (ExpenseCategoryService) this.lookupService(EXPENSE_CATEGORY_SERVICE_BEAN_NAME);
        this.cashTransactionalService = (CashTransactionalService) this.lookupService(CASH_TRANSACTIONAL_SERVICE);

		this.logger.info("Service locator bean is initialized");

	}
	
	/**
	 * Get the <code>CatalogService</code>
	 * 
	 * @return the catalog service
	 */
	//public CatalogService getCatalogService() {
	//	return this.catalogService;
	//}
	
	/**
	 * Get the <code>UserService</code>
	 * 
	 * @return the user service
	 */
	public UserService getUserService() {
		return this.userService;
	}

    public AccountService getAccountService() {
        return this.accountService;
    }

    public BankAccountService getBankAccountService() {
        return this.bankAccountService;
    }

    public CreditCardService getCreditCardService() {
        return this.creditCardService;
    }

    public ExpenseGroupService getExpenseGroupService() {
        return this.expenseGroupService;
    }

    public ExpenseTypeService getExpenseTypeService() {
        return this.expenseTypeService;
    }

    public PayeeService getPayeeService() {
        return this.payeeService;
    }

    public ExpenseService getExpenseService() {
        return this.expenseService;
    }

    public BorrowerService getBorrowerService() {
        return this.borrowerService;
    }
	
	/**
	 * Lookup service based on service bean name.
	 * 
	 * @param serviceBeanName the service bean name
	 * @return the service bean
	 */
	public Object lookupService(String serviceBeanName) {
		return appContext.getBean(serviceBeanName);
	}

    public IncomeService getIncomeService() {
        return this.incomeService;
    }

    public DepositService getDepositService() {
        return this.depositService;
    }

    public CurrencyOnHandService getCurrencyOnHandService() {
        return this.currencyOnHandService;
    }

    public CashService getCashService() {
        return this.cashService;
    }

    public MoneyOrderService getMoneyOrderService() {
        return this.moneyOrderService;
    }

    public LoanService getLoanService() {
        return this.loanService;
    }

    public AccountTransactionalService getAccountTransactionalService() {
        return this.accountTransactionalService;
    }

    public BankAccountTransactionalService getBankAccountTransactionalService() {
        return this.bankAccountTransactionalService;
    }

    public CreditCardTransactionalService getCreditCardTransactionalService() {
        return this.creditCardTransactionalService;
    }

    public DepositTransactionalService getDepositTransactionalService() {
        return this.depositTransactionalService;
    }

    public ExpenseTransactionalService getExpenseTransactionalService() {
        return this.expenseTransactionalService;
    }

    public IncomeTransactionalService getIncomeTransactionalService() {
        return this.incomeTransactionalService;
    }

    public LoanTransactionalService getLoanTransactionalService() {
        return this.loanTransactionalService;
    }

    public MoneyOrderTransactionalService getMoneyOrderTransactionalService() {
        return this.moneyOrderTransactionalService;
    }

    public ExpenseCategoryService getExpenseCategoryService() {
        return this.expenseCategoryService;
    }
    
    public CashTransactionalService getCashTransactionalService() {
    	return this.cashTransactionalService;
    }

	@Override
	public PayeeTransactionalService getPayeeTransactionalService() {
		return this.payeeTransactionalService;
	}





}
