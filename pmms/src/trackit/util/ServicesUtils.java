package trackit.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import trackit.businessobjects.BankAccount;
import trackit.businessobjects.CreditCard;
import trackit.businessobjects.ExpenseCategory;
import trackit.businessobjects.ExpenseGroup;
import trackit.businessobjects.ExpenseSubType;
import trackit.businessobjects.Payee;
import trackit.businessobjects.User;
import trackit.service.BankAccountService;
import trackit.service.CreditCardService;
import trackit.service.ExpenseCategoryService;
import trackit.service.ExpenseGroupService;
import trackit.service.PayeeService;
import trackit.service.UserService;

public class ServicesUtils {
	
	public static Map<Long,String> getExpenseGroupLookup(ExpenseGroupService service, String accountId) {
    	List<ExpenseGroup> ets = getExpenseGroups(service, accountId);
    		
    	Map<Long,String> lookup = new HashMap<Long,String>();
		for (ExpenseGroup eg : ets) {
			lookup.put(eg.getId(), eg.getDescription());
		}
		
		return lookup;
    }
	
	public static List<ExpenseGroup> getExpenseGroups(ExpenseGroupService service, String accountId) {
		return service.getAllExpenseGroupsForAccount(accountId);
	}
	
	public static Map<Long,String> getPayeeLookup(PayeeService service, String currentAccountId) {
        Map<Long,String> payeeLookup = new HashMap<Long,String>();
        List<Payee> payees = service.getAllPayeesForAccount(currentAccountId);

        for (Payee payee : payees) {
            payeeLookup.put(payee.getId(), payee.getDescription());
        }

        return payeeLookup;
    }
	
	public static Map<Long,String> getExpenseSubTypeLookup(ExpenseCategoryService service, String currentAccountId) {
        
        Map<Long,String> expenseTypeLookup = new HashMap<Long,String>();
        List<ExpenseCategory> expenseCategories = 
        	getExpenseCategories(service, currentAccountId);

        for (ExpenseCategory cat : expenseCategories) {
        	List<ExpenseSubType> types = cat.getSubTypes();
        	for (ExpenseSubType st : types) {
        		expenseTypeLookup.put(st.getId(), cat.getDescription() + ":" + st.getDescription());
        	}
        }

        return expenseTypeLookup;
    }
	
	public static Map<Long,String> getExpenseCategoryLookupByTypeId(ExpenseCategoryService service, String currentAccountId) {
        
        Map<Long,String> expenseTypeLookup = new HashMap<Long,String>();
        List<ExpenseCategory> expenseCategories = 
        	getExpenseCategories(service, currentAccountId);

        for (ExpenseCategory cat : expenseCategories) {
        	List<ExpenseSubType> types = cat.getSubTypes();
        	for (ExpenseSubType st : types) {
        		expenseTypeLookup.put(st.getId(), cat.getDescription());
        	}
        }

        return expenseTypeLookup;
    }
	
	public static Map<Long,String> getExpenseCategoryLookup(ExpenseCategoryService service, String currentAccountId) {
        
        Map<Long,String> expenseCategoryLookup = new HashMap<Long,String>();
        List<ExpenseCategory> expenseCategories = 
        	getExpenseCategories(service, currentAccountId);

        for (ExpenseCategory cat : expenseCategories) {        	
       		expenseCategoryLookup.put(cat.getId(), cat.getDescription());
        	
        }

        return expenseCategoryLookup;
    }
	
	public static Map<Long,String> getExpenseTypeLookup(ExpenseCategoryService service, String categoryId) {        
        Map<Long,String> expenseTypeLookup = new HashMap<Long,String>();
        ExpenseCategory cat = service.findExpenseCategory(categoryId);

        List<ExpenseSubType> types = cat.getSubTypes();
    	for (ExpenseSubType st : types) {
    		expenseTypeLookup.put(st.getId(), st.getDescription());
    	}        

        return expenseTypeLookup;
    }
	
	public static Map<Long,String> getExpenseTypeLookupByAccountId(ExpenseCategoryService service, String currentAccountId) {
        
        Map<Long,String> expenseTypeLookup = new HashMap<Long,String>();
        List<ExpenseCategory> expenseCategories = 
        	getExpenseCategories(service, currentAccountId);

        for (ExpenseCategory cat : expenseCategories) {
        	List<ExpenseSubType> types = cat.getSubTypes();
        	for (ExpenseSubType st : types) {
        		expenseTypeLookup.put(st.getId(), st.getDescription());
        	}
        }

        return expenseTypeLookup;
    }
	
	public static List<ExpenseCategory> getExpenseCategories(ExpenseCategoryService service, String accountId) {
		return service.getAllExpenseCategoriesForAccount(accountId);
	}
	
	public static Map<Long,String> getUserNameLookup(UserService service, String currentAccountId) {
        
        Map<Long,String> lookup = new HashMap<Long,String>();
        
        List<User> list = getUsers(service, currentAccountId);

        for (User user : list) {
            lookup.put(user.getId(), user.getFirstLastName());
            
        }

        return lookup;
    }
	
	public static List<User> getUsers(UserService service, String accountId) {
		return service.getAllUsersForAccount(accountId);
	}
	
	public static Map<Long,String> getBankAccountLookup(BankAccountService service, String currentAccountId) {
    	Map<Long,String> bankAccountLookup = new HashMap<Long,String>();
        List<BankAccount> bankAccounts =
                getBankAccounts(service, currentAccountId);

        for (BankAccount acct : bankAccounts) {
            bankAccountLookup.put(acct.getId(), acct.getDescription());
        }

        return bankAccountLookup;
    }
	
	public static List<BankAccount> getBankAccounts(BankAccountService service, String accountId) {
		return service.getAllBankAccountsForAccount(accountId);
	}
    
    public static Map<Long,String> getCreditCardLookup(CreditCardService service, String currentAccountId) {
    	
        Map<Long,String> creditCardLookup = new HashMap<Long,String>();
        List<CreditCard> creditCards =
                getCreditCards(service, currentAccountId);

        for (CreditCard acct : creditCards) {
        	creditCardLookup.put(acct.getId(), acct.getDescription());
        }

        return creditCardLookup;
    }
    
    public static List<CreditCard> getCreditCards(CreditCardService service, String accountId) {
    	return service.getAllCreditCardsForAccount(accountId);
    }

}
