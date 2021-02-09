/*
 * JCatalog Project
 */
package trackit.backingbeans;

import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import trackit.businessobjects.BankAccount;
import trackit.businessobjects.Borrower;
import trackit.businessobjects.CreditCard;
import trackit.businessobjects.CurrencyOnHand;
import trackit.businessobjects.Expense;
import trackit.businessobjects.ExpenseGroup;
import trackit.businessobjects.IncomeSource;
import trackit.businessobjects.Payee;
import trackit.businessobjects.User;
import trackit.servicelocator.ServiceLocator;
import trackit.util.UserPermissions;

/**
 * The managed bean with session scope. 
 * <p>
 * It is used as a session scope cache.
 * In JSF, the properties are set by bean management facility.
 * 
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 */
public class SessionBean extends BaseBean {
    
    private String currentUserId;
    private String currentAccountId;
    private Expense currentExpense;
    private String currentBankAccountId;
    private String currentCreditCardId;
    private String followOnAction;
    private ExpenseBean expenseBean;
    private ServiceLocator serviceLocator;
    private String currentLoanId;
    private String selectedUserId;
    private boolean createNewUser;
    
    
	private List<ExpenseGroup> expenseGroupList;
	private Map<Long,String> expenseGroupLookup;
	private List<IncomeSource> incomeSourceList;
	private List<Borrower> borrowersList;
	private List<Payee> payeeList;
	private List<BankAccount> bankAccounts;
	private List<CreditCard> creditCards;
	private List<SelectItem> expenseCategoryTypeList;
	private List<User> users;
	private Map<Long, String> expenseTypeLookup;
	private Map<Long, IncomeSource> incomeSourceLookup;
	private List<CurrencyOnHand> availableMoneyOrderList;
	private String statementCSV;
	private List<StatementMatchItem> statementMatchModel;
    
    public SessionBean() {
    }

        
    public void setCurrentAccountId(String accountId) {
        this.currentAccountId = accountId;
    }

    public String getCurrentAccountId() {
        return currentAccountId;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String id) {
        this.currentUserId = id;
    }

    public void setCurrentExpense(Expense expense) {
        this.currentExpense = expense;
    }

    public Expense getCurrentExpense() {
        return this.currentExpense;
    }

    public void setExpenseBean(ExpenseBean bean) {
        this.expenseBean = bean;
    }

    public ExpenseBean getExpenseBean() {
        return this.expenseBean;
    }

    public String getCurrentBankAccountId() {
        return this.currentBankAccountId;
    }

    public void setCurrentBankAccountId(String currentBankAccountId) {
        this.currentBankAccountId = currentBankAccountId;
    }

    public String getCurrentCreditCardId() {
        return currentCreditCardId;
    }

    public void setCurrentCreditCardId(String currentCreditCardId) {
        this.currentCreditCardId = currentCreditCardId;
    }

    public void setFollowOnAction(String action) {
        this.followOnAction = action;
    }
    
    public String getFollowOnAction() {
        return this.followOnAction;
    }

    public void setServiceLocator(ServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;
    }

    public ServiceLocator getServiceLocator() {
        return serviceLocator;
    }

    public String getCurrentLoanId() {
        return currentLoanId;
    }

    public void setCurrentLoanId(String idstr) {
        this.currentLoanId = idstr;
    }
    
    public void setSelectedUserId(String id) {
    	this.selectedUserId = id;
    }
    
    public String getSelectedUserId() {
    	return this.selectedUserId;
    }
    
    public Boolean canViewAccountUsers() {
    	User currentUser = this.serviceLocator.getUserService().findUser(currentUserId);
    	List<String> permissions = UserPermissions.parseDBValue(currentUser.getPermissions());
    	return permissions.contains("ROLE_CAN_VIEW_USER_INFO");
    }
    
    public Boolean canViewExpenseInfo() {
    	User currentUser = this.serviceLocator.getUserService().findUser(currentUserId);
    	List<String> permissions = UserPermissions.parseDBValue(currentUser.getPermissions());
    	return permissions.contains("ROLE_CAN_VIEW_EXPENSE_INFO");
    }
    
    public Boolean canViewCashTransfers() {
    	User currentUser = this.serviceLocator.getUserService().findUser(currentUserId);
    	List<String> permissions = UserPermissions.parseDBValue(currentUser.getPermissions());
    	return permissions.contains("ROLE_CAN_VIEW_CASH_TRANSFERS");
    }
    
    public Boolean canViewBankAccounts() {
    	User currentUser = this.serviceLocator.getUserService().findUser(currentUserId);
    	List<String> permissions = UserPermissions.parseDBValue(currentUser.getPermissions());
    	return permissions.contains("ROLE_CAN_VIEW_BANK_INFO");
    }
    
    public Boolean canViewCreditCards() {
    	User currentUser = this.serviceLocator.getUserService().findUser(currentUserId);
    	List<String> permissions = UserPermissions.parseDBValue(currentUser.getPermissions());
    	return permissions.contains("ROLE_CAN_VIEW_CREDIT_CARD_INFO");
    }
    
    public Boolean canViewLoanSummary() {
    	User currentUser = this.serviceLocator.getUserService().findUser(currentUserId);
    	List<String> permissions = UserPermissions.parseDBValue(currentUser.getPermissions());
    	return permissions.contains("ROLE_CAN_VIEW_LOAN_INFO");
    }
    
    public Boolean canViewCurrencyOnHand() {
    	User currentUser = this.serviceLocator.getUserService().findUser(currentUserId);
    	List<String> permissions = UserPermissions.parseDBValue(currentUser.getPermissions());
    	return permissions.contains("ROLE_CAN_VIEW_CURRENCY_ON_HAND");
    }
    
    public Boolean canViewExpenseGroupReport() {
    	User currentUser = this.serviceLocator.getUserService().findUser(currentUserId);
    	List<String> permissions = UserPermissions.parseDBValue(currentUser.getPermissions());
    	return permissions.contains("ROLE_CAN_VIEW_EXPENSE_GROUP_REPORT");
    }
    
    public Boolean canViewCapitalReport() {
    	User currentUser = this.serviceLocator.getUserService().findUser(currentUserId);
    	List<String> permissions = UserPermissions.parseDBValue(currentUser.getPermissions());
    	return permissions.contains("ROLE_CAN_VIEW_CAPITAL_REPORT");
    }
    
    public Boolean canViewReports() {
    	User currentUser = this.serviceLocator.getUserService().findUser(currentUserId);
    	List<String> permissions = UserPermissions.parseDBValue(currentUser.getPermissions());
    	return permissions.contains("ROLE_CAN_VIEW_CHARTS");
    }
    
    public Boolean canViewCharts() {
    	User currentUser = this.serviceLocator.getUserService().findUser(currentUserId);
    	List<String> permissions = UserPermissions.parseDBValue(currentUser.getPermissions());
    	return permissions.contains("ROLE_CAN_VIEW_CHARTS");
    }
    
    public String getUserName() {
    	User currentUser = this.serviceLocator.getUserService().findUser(currentUserId);
    	return currentUser.getFirstName();
    }


	public boolean isCreateNewUser() {
		return createNewUser;
	}


	public void setCreateNewUser(boolean createNewUser) {
		this.createNewUser = createNewUser;
	}


	public List<ExpenseGroup> getExpenseGroupList() {
		return this.expenseGroupList;
	}
	
	public void setExpenseGroupList(List<ExpenseGroup> list) {
		this.expenseGroupList = list;
	}


	public Map<Long, String> getExpenseGroupLookup() {
		return expenseGroupLookup;
	}


	public void setExpenseGroupLookup(Map<Long, String> expenseGroupLookup) {
		this.expenseGroupLookup = expenseGroupLookup;
	}


	public List<IncomeSource> getIncomeSourceList() {
		return this.incomeSourceList;
	}
	
	public void setIncomeSourceList(List<IncomeSource> list) {
		this.incomeSourceList = list;
	}


	public List<Borrower> getBorrowersList() {
		return this.borrowersList;
	}
	
	public void setBorrowersList(List<Borrower> list) {
		this.borrowersList = list;
	}


	public List<Payee> getPayeeList() {
		return this.payeeList;
	}
	
	public void setPayeeList(List<Payee> list) {
		this.payeeList = list;
	}


	public List<BankAccount> getBankAccounts() {
		return this.bankAccounts;
	}
	
	public void setBankAccounts(List<BankAccount> list) {
		this.bankAccounts = list;
	}


	public List<CreditCard> getCreditCards() {
		return this.creditCards;
	}
	
	public void setCreditCards(List<CreditCard> list) {
		this.creditCards = list;
	}


	public List<SelectItem> getExpenseCategoryTypeList() {
		return expenseCategoryTypeList;
	}
	
	public void setExpenseCategoryTypeList(List<SelectItem> list) {
		this.expenseCategoryTypeList = list;
	}


	public List<User> getUsers() {
		return this.users;
	}
	
	public void setUsers(List<User> users) {
		this.users = users;
	}


	public Map<Long,String> getExpenseTypeLookup() {
		return this.expenseTypeLookup;
	}
	
	public void setExpenseTypeLookup(Map<Long,String> map) {
		this.expenseTypeLookup = map;
	}


	public Map<Long, IncomeSource> getIncomeSourceLookup() {
		return this.incomeSourceLookup;
	}
	
	public void setIncomeSourceLookup(Map<Long, IncomeSource> map) {
		this.incomeSourceLookup = map;
	}


	public List<CurrencyOnHand> getAvailableMoneyOrderList() {
		return this.availableMoneyOrderList;
	}
	
	public void setAvailableMoneyOrderList(List<CurrencyOnHand> list) {
		this.availableMoneyOrderList = list;
	}


	public void resetMoneyOrderInfo() {
		this.availableMoneyOrderList = null;
	}
	
	public void resetExpenseGroupInfo() {
		this.expenseGroupList = null;
		this.expenseGroupLookup = null;
	}
	
	public void resetIncomeSourceInfo() {
		this.incomeSourceList = null;
		this.incomeSourceLookup = null;
	}
	
	public void resetBorrowerInfo() {
		this.borrowersList = null;
	}
	
	public void resetPayeeInfo() {
		this.payeeList = null;
	}
	
	public void resetBankAccountInfo() {
		this.bankAccounts = null;
	}
	
	public void resetCreditCardInfo() {
		this.creditCards = null;
	}
	
	public void resetExpenseCategoryTypeInfo() {
		this.expenseCategoryTypeList = null;
		this.expenseTypeLookup = null;
	}
	
	public void resetUserInfo() {
		this.users = null;
	}


	public void setStatementCSV(String str) {
		this.statementCSV = str;		
	}
	
	public String getStatementCSV() {
		return this.statementCSV;
	}


	public void setStatementMatchModel(List<StatementMatchItem> matchedItems) {
		this.statementMatchModel = matchedItems;
		
	}


	public List<StatementMatchItem> getStatementMatchModel() {
		return statementMatchModel;
	}
	
}
