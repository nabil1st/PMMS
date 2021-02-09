package org.me.expensetrackerme.services;

import java.util.List;


import trackit.businessobjects.CreditCard;
import trackit.businessobjects.CreditCardList;
import trackit.businessobjects.Expense;

import trackit.businessobjects.BankAccount;
import trackit.businessobjects.BankAccountList;
import trackit.businessobjects.ExpenseCategory;
import trackit.businessobjects.ExpenseCategoryList;
import trackit.businessobjects.ExpenseGroup;
import trackit.businessobjects.ExpenseGroupList;
import trackit.businessobjects.ExpenseList;
import trackit.businessobjects.ExpenseSubType;
import trackit.businessobjects.ExpenseSubTypeList;

import org.trackit.model.Login;
import trackit.businessobjects.Payee;
import trackit.businessobjects.PayeeList;
import org.me.expesetrackerme.application.AccountInfo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestfulDataAccessService {	
	
//	private static final String URL = "http://10.15.14.48:8080/pmms/rest/";
	
	private static final String URL = "http://192.168.1.8:8080/pmms/rest/";
	
	private static RestfulDataAccessService instance = new RestfulDataAccessService();
	
	private RestfulDataAccessService() {}
	
	public static RestfulDataAccessService getInstance() {
		return instance;
	}
	
	public List<Payee> getAllPayees() {
		String url = URL + "payees" + "/" + String.valueOf(AccountInfo.getInstance().getAccountId());
		
		try {
			RestTemplate restTemplate = new RestTemplate();
			PayeeList payeeList = restTemplate.getForObject(url, PayeeList.class);
		
			return payeeList.getPayeeList();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return null;

	}
	
	public List<ExpenseGroup> getAllExpenseGroups() {
		String url = URL + "expensegroups" + "/" + String.valueOf(AccountInfo.getInstance().getAccountId());
		
		try {
			RestTemplate restTemplate = new RestTemplate();
			ExpenseGroupList expenseGroupList = restTemplate.getForObject(url, ExpenseGroupList.class);
		
			return expenseGroupList.getList();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return null;

	}
	
	public List<CreditCard> getAllCreditCards() {
		String url = URL + "creditcards" + "/" + String.valueOf(AccountInfo.getInstance().getAccountId());
		
		try {
			RestTemplate restTemplate = new RestTemplate();
			CreditCardList creditCardList = restTemplate.getForObject(url, CreditCardList.class);
		
			return creditCardList.getCreditCardList();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
	
	public List<BankAccount> getAllBankAccounts() {
		String url = URL + "bankaccounts" + "/" + String.valueOf(AccountInfo.getInstance().getAccountId());
		
		try {
			RestTemplate restTemplate = new RestTemplate();
			BankAccountList bankAccountList = restTemplate.getForObject(url, BankAccountList.class);
		
			return bankAccountList.getList();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
	
	public List<ExpenseCategory> getAllExpenseCategories() {
		String url = URL + "expensecategories" + "/" + String.valueOf(AccountInfo.getInstance().getAccountId());
		
		try {
			RestTemplate restTemplate = new RestTemplate();
			ExpenseCategoryList expenseCategoryList = 
				restTemplate.getForObject(url, ExpenseCategoryList.class);
		
			return expenseCategoryList.getList();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
	
	public List<ExpenseSubType> getAllExpenseTypesForCategory(Long categoryId) {
		String url = URL + "expensetypes" + "/" + String.valueOf(categoryId);
		
		try {
			RestTemplate restTemplate = new RestTemplate();
			ExpenseSubTypeList expenseSubTypeList = 
				restTemplate.getForObject(url, ExpenseSubTypeList.class);
		
			return expenseSubTypeList.getList();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
	
	public List<Expense> getAllExpenses() {
		String url = URL + "expenses" + "/" + String.valueOf(AccountInfo.getInstance().getAccountId());
		
		try {
			RestTemplate restTemplate = new RestTemplate();
			ExpenseList expenseList = restTemplate.getForObject(
					url, ExpenseList.class);
		
			return expenseList.getList();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
	
	public Expense saveExpense(Expense e) {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(new MediaType("application","xml"));
		
		e.setUserId(AccountInfo.getInstance().getUserId());
		
		HttpEntity<Expense> requestEntity = new HttpEntity<Expense>(e, requestHeaders);
		String url = URL + "newexpense";
		
		RestTemplate restTemplate = new RestTemplate();
		
		try {
			ResponseEntity<Expense> responseEntity = restTemplate.exchange(url, HttpMethod.POST ,requestEntity, Expense.class);
			Expense result = responseEntity.getBody();
			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}
	
	public Login login(Login e) {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(new MediaType("application","xml"));
		
		
		HttpEntity<Login> requestEntity = new HttpEntity<Login>(e, requestHeaders);
		String url = URL + "login";
		
		RestTemplate restTemplate = new RestTemplate();
		try {
			ResponseEntity<Login> responseEntity = restTemplate.exchange(url, HttpMethod.POST ,requestEntity, Login.class);
			Login result = responseEntity.getBody();
			return result;
		} catch (Exception ex) {
			if (ex.getMessage().indexOf("timed out") >= 0 || ex.getMessage().indexOf("I/O error") >= 0) {
				Login result = new Login();
				result.setStatus(Login.CONNECTION_TIMED_OUT);
				return result;
			}
			ex.printStackTrace();
		}

		return null;
	}
	
	
	public Payee createPayee(Payee p) {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(new MediaType("application","xml"));
		
		p.setAccountId(AccountInfo.getInstance().getAccountId());
				
		HttpEntity<Payee> requestEntity = new HttpEntity<Payee>(p, requestHeaders);
		String url = URL + "newpayee";
		
		RestTemplate restTemplate = new RestTemplate();
		
		try {
			ResponseEntity<Payee> responseEntity = restTemplate.exchange(url, HttpMethod.POST ,requestEntity, Payee.class);
			Payee result = responseEntity.getBody();
			return result;			
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

}
