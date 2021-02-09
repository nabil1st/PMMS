package org.me.expensetrackerme.services;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.me.expensetrackerme.database.LocalDataAccess;
import org.me.expensetrackerme.xml.ObjectAlreadyExistsException;
import org.me.expesetrackerme.application.AccountInfo;

import trackit.businessobjects.CreditCard;

import trackit.businessobjects.BankAccount;
import trackit.businessobjects.ExpenseCategory;
import trackit.businessobjects.ExpenseGroup;
import trackit.businessobjects.ExpenseSubType;
import trackit.businessobjects.Payee;



public class CachedData {
	private List<Payee> payees;
	private List<CreditCard> creditCards;
	private List<BankAccount> bankAccounts;
	private List<ExpenseCategory> expenseCategories;
	private List<ExpenseGroup> expenseGroups;
	
	private Map<Long, List<ExpenseSubType>> expenseTypesMap = 
		new HashMap<Long, List<ExpenseSubType>>();
	
	private static CachedData instance = new CachedData();
	
	public static CachedData getInstance() {
		return instance;
	}
	
	private CachedData(){}

	public List<Payee> getPayees() {
		if (payees == null) {
			if (AccountInfo.getInstance().isOffline()) {
				payees = LocalDataAccess.getInstance().getSavedPayees();
			} else {
				payees = RestfulDataAccessService.getInstance().getAllPayees();
				LocalDataAccess.getInstance().updatePayees(payees);
			}
		}
		return payees;
	}

	public List<CreditCard> getCreditCards() {
		if (creditCards == null) {
			creditCards = RestfulDataAccessService.getInstance().getAllCreditCards();
		}
		return creditCards;
	}
	
	public List<BankAccount> getBankAccounts() {
		if (bankAccounts == null) {
			bankAccounts = RestfulDataAccessService.getInstance().getAllBankAccounts();
		}
		
		return bankAccounts;
	}

	public List<ExpenseCategory> getExpenseCategories() {
		if (expenseCategories == null) {
			expenseCategories = RestfulDataAccessService.getInstance().getAllExpenseCategories();
		}
		return expenseCategories;
	}
	
	public List<ExpenseGroup> getExpenseGroups() {
		if (expenseGroups == null) {
			expenseGroups = RestfulDataAccessService.getInstance().getAllExpenseGroups();
		}
		return expenseGroups;
	}
	
	public List<ExpenseSubType> getExpenseTypesForCategory(Long categoryId) {
		if (expenseTypesMap.containsKey(categoryId)) {
			return expenseTypesMap.get(categoryId);
		} else {
			List<ExpenseSubType> list = RestfulDataAccessService.getInstance().getAllExpenseTypesForCategory(categoryId);
			expenseTypesMap.put(categoryId, list);
			return list;
		}
	}
	
	public Payee getPayeeByName(String name) {
		for (Payee p : getPayees()) {
			if (p.getDescription().equals(name)) {
				return p;
			}
		}
		
		return null;
	}
	
	public Payee getPayeeById(Long id) {
		for (Payee p : getPayees()) {
			if (p.getId().longValue() == id.longValue()) {
				return p;
			}
		}		
		return null;
	}
	
	public CreditCard getCreditCardByName(String name) {
		for (CreditCard c : getCreditCards()) {
			if (c.getDescription().equals(name)) {
				return c;
			}
		}
		
		return null;
	}
	
	public CreditCard getCreditCardById(Long id) {
		for (CreditCard c : getCreditCards()) {
			if (c.getId().longValue() == id.longValue()) {
				return c;
			}
		}
		
		return null;
	}
	
	public BankAccount getBankAccountByName(String name) {
		for (BankAccount b : getBankAccounts()) {
			if (b.getDescription().equals(name)) {
				return b;
			}
		}
		
		return null;
	}
	
	public BankAccount getBankAccountById(Long id) {
		for (BankAccount b : getBankAccounts()) {
			if (b.getId().longValue() == id.longValue()) {
				return b;
			}
		}
		
		return null;
	}
	
	public ExpenseGroup getExpenseGroupByName(String name) {
		for (ExpenseGroup eg : getExpenseGroups()) {
			if (eg.getDescription().equals(name)) {
				return eg;
			}
		}
		
		return null;
	}
	
	public ExpenseCategory getExpenseCategoryByName(String name) {
		for (ExpenseCategory e : getExpenseCategories()) {
			if (e.getDescription().equals(name)) {
				return e;
			}
		}
		
		return null;
	}
	
	public Payee createPayee(Payee payee) throws ObjectAlreadyExistsException {
		if (getPayeeByName(payee.getDescription()) != null) {
			throw new ObjectAlreadyExistsException("Payee " + payee.getDescription() + " already exists");
		}
		
		Payee result = null;
		
		if (!AccountInfo.getInstance().isOffline()) {
			result = RestfulDataAccessService.getInstance().createPayee(payee);
			LocalDataAccess.getInstance().insertSyncedPayee(result);			
		} else {
			LocalDataAccess.getInstance().createUnsyncedPayee(payee);
			result = payee;
		}
		
		getPayees().add(result);
		
		Collections.sort(payees, new Comparator<Payee>() {
			@Override
			public int compare(Payee object1, Payee object2) {
				return object1.getDescription().compareTo(object2.getDescription());
			}
			
		});
		
		return result;
	}
}
