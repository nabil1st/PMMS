package trackit.web.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;

import trackit.backingbeans.DatedAmount;
import trackit.backingbeans.DescriptionItem;
import trackit.backingbeans.DescriptionItemComparator;
import trackit.businessobjects.BankAccount;
import trackit.businessobjects.CreditCard;
import trackit.businessobjects.Expense;
import trackit.businessobjects.ExpenseCategory;
import trackit.businessobjects.ExpenseGroup;
import trackit.businessobjects.ExpenseItem;
import trackit.businessobjects.ExpensePurpose;
import trackit.businessobjects.ExpenseSubType;
import trackit.businessobjects.Payee;
import trackit.businessobjects.PaymentMethod;
import trackit.businessobjects.User;
import trackit.filters.SessionExpiredFilter;
import trackit.service.BankAccountService;
import trackit.service.CreditCardService;
import trackit.service.ExpenseCategoryService;
import trackit.service.ExpenseGroupService;
import trackit.service.ExpenseService;
import trackit.service.PayeeService;
import trackit.service.UserService;
import trackit.util.ServicesUtils;
import trackit.webservices.ExpenseCategoryStats;
import trackit.webservices.ExpenseStatsDataHolder;

@Service("reportsService")
@RemotingDestination
public class ReportsServiceImpl {
	
	private static Logger logger = Logger.getLogger(SessionExpiredFilter.class);
	
	@Autowired
	private ExpenseService expenseService;
	
	@Autowired
	private ExpenseCategoryService expenseCategoryService;
	
	@Autowired
	private PayeeService payeeService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ExpenseGroupService expenseGroupService;
	
	@Autowired
	private CreditCardService creditCardService;
	
	@Autowired
	private BankAccountService bankAccountService;
	
	public ReportsServiceImpl() {
		
	}
	
	public List<ExpenseCategoryStats> getExpenseCategoryStats(String accountId, Date startDate, Date endDate) {
		Map<Long,String> expenseCategoryLookup = buildExpenseCategoryLookup(accountId);
		return buildStats(expenseCategoryLookup, accountId, startDate, endDate);		
	}
	
	public List<ExpenseCategoryStats> getExpenseSubCategoryStats(String accountId, Date startDate, Date endDate) {
		Map<Long,String> expenseCategoryLookup = buildExpenseSubCategoryLookup(accountId);
		return buildStats(expenseCategoryLookup, accountId, startDate, endDate);
	}
	
	public List<ExpenseCategoryStats> getExpensePayeeStats(String accountId, Date startDate, Date endDate) {
		List<Payee> payees = payeeService.getAllPayeesForAccount(accountId);
		Map<Long,String> payeeLookup = new HashMap<Long,String>();
		for (Payee payee : payees) {
			payeeLookup.put(payee.getId(), payee.getDescription());
		}
		
		List<ExpenseCategoryStats> list = new ArrayList<ExpenseCategoryStats>();
		
		List<Expense> allExpensesForAccount = null;
		
		if (startDate != null && endDate != null) {
			allExpensesForAccount = expenseService.getExpensesForAccount(accountId, startDate, endDate);
		} else {
			allExpensesForAccount = expenseService.getAllExpensesForAccount(
					String.valueOf(accountId));
		}
		
		
		Map<String,BigDecimal> payeeMap = new HashMap<String,BigDecimal>();
		
		List<ExpenseStatsDataHolder> statItems = new ArrayList<ExpenseStatsDataHolder>();
		
		BigDecimal total = new BigDecimal(0);
		
		for (Expense expense : allExpensesForAccount) {
			if (expense.getExpenseItems() != null && expense.getExpenseItems().size() > 0) {
				Set<ExpenseItem> expenseItems = expense.getExpenseItems();
				for (ExpenseItem expenseItem : expenseItems) {
					if (expenseItem.getExpensePurpose().equals(ExpensePurpose.Personal)) {
						statItems.add(new ExpenseStatsDataHolder(expenseItem.getExpenseTypeId(), 
								expense.getPayeeId(), expenseItem.getAmount().add(expenseItem.getTax())));
					}
				}
			} else {
				if (expense.getExpensePurpose().equals(ExpensePurpose.Personal)) {
					statItems.add(new ExpenseStatsDataHolder(expense.getExpenseTypeId(), 
						expense.getPayeeId(), expense.getAmount()));
				}
			}
		}
		
		for (ExpenseStatsDataHolder statItem : statItems) {
			String payee = payeeLookup.get(statItem.getPayeeId());
			BigDecimal totalForPayee = payeeMap.get(payee);
			if (totalForPayee == null) {
				totalForPayee = new BigDecimal(0);
				payeeMap.put(payee, totalForPayee);
			}
			
			totalForPayee = totalForPayee.add(statItem.getAmount());
			payeeMap.remove(payee);
			payeeMap.put(payee, totalForPayee);
			
			total = statItem.getAmount().add(total);
		}
		
		populateList(total, payeeMap, list);
		
		return list;
		
	}
	
	private Map<Long,String> buildExpenseSubCategoryLookup(String accountId) {
		return buildMap(accountId, false);
	}
	
	private List<ExpenseCategoryStats> buildStats(Map<Long,String> expenseCategoryLookup, 
			String accountId, Date startDate, Date endDate) {
		List<ExpenseCategoryStats> list = new ArrayList<ExpenseCategoryStats>();
		List<ExpenseStatsDataHolder> statItems = new ArrayList<ExpenseStatsDataHolder>();		
		
		List<Expense> allExpensesForAccount = null;
		
		if (startDate != null && endDate != null) {
			allExpensesForAccount = expenseService.getExpensesForAccount(accountId, startDate, endDate);
		} else {
			allExpensesForAccount = expenseService.getAllExpensesForAccount(
					String.valueOf(accountId));
		}
		
		Map<String,BigDecimal> categoryMap = new HashMap<String,BigDecimal>();
		
		BigDecimal total = new BigDecimal(0);
		
		for (Expense expense : allExpensesForAccount) {
			if (expense.getExpenseItems() != null && expense.getExpenseItems().size() > 0) {
				Set<ExpenseItem> expenseItems = expense.getExpenseItems();
				for (ExpenseItem expenseItem : expenseItems) {
					if (expenseItem.getExpensePurpose().equals(ExpensePurpose.Personal)) {
						statItems.add(new ExpenseStatsDataHolder(expenseItem.getExpenseTypeId(), 
								expense.getPayeeId(), expenseItem.getAmount().add(expenseItem.getTax())));
					}
				}
			} else {
				if (expense.getExpensePurpose().equals(ExpensePurpose.Personal)) {
					statItems.add(new ExpenseStatsDataHolder(expense.getExpenseTypeId(), 
						expense.getPayeeId(), expense.getAmount()));
				}
			}
		}
		
		
		for (ExpenseStatsDataHolder statItem : statItems) {			  
			// Only show personal expenses			
			String mainCategory = expenseCategoryLookup.get(statItem.getExpenseTypeId());
			BigDecimal totalForCategory = categoryMap.get(mainCategory);
			if (totalForCategory == null) {
				totalForCategory = new BigDecimal(0);
				categoryMap.put(mainCategory, totalForCategory);
			}
			
			totalForCategory = totalForCategory.add(statItem.getAmount());
			categoryMap.remove(mainCategory);
			categoryMap.put(mainCategory, totalForCategory);
			
			total = statItem.getAmount().add(total);
			
		}
		
		populateList(total, categoryMap, list);
		
		return list;
	}
	
	private void populateList(BigDecimal total, Map<String,BigDecimal> map, List<ExpenseCategoryStats> list) {
		for (String category : map.keySet()) {
			ExpenseCategoryStats stats = new ExpenseCategoryStats();
			stats.setCategory(category);
			stats.setAmount(map.get(category).doubleValue());
			stats.setPercentage((int) (stats.getAmount() * 100 / total.doubleValue()));
			list.add(stats);
		}
	}
	
	private Map<Long,String> buildExpenseCategoryLookup(String accountId) {
		return buildMap(accountId, true);
	}
	
	private Map<Long,String> buildMap(String accountId, boolean categories) {
		List<ExpenseCategory> expenseCategories = expenseCategoryService.
			getAllExpenseCategoriesForAccount(accountId);

		Map<Long,String> expenseCategoryLookup = new HashMap<Long,String>();
		for (ExpenseCategory ec : expenseCategories) {
			List<ExpenseSubType> subTypes = ec.getSubTypes();
			for (ExpenseSubType st : subTypes) {
				if (categories) {
					expenseCategoryLookup.put(st.getId(), ec.getDescription());
				} else {
					expenseCategoryLookup.put(st.getId(), st.getDescription());
				}
			}
		}
		
		return expenseCategoryLookup;
	}

	public ExpenseService getExpenseService() {
		return expenseService;
	}

	public void setExpenseService(ExpenseService expenseService) {
		this.expenseService = expenseService;
	}

	public ExpenseCategoryService getExpenseCategoryService() {
		return expenseCategoryService;
	}

	public void setExpenseCategoryService(
			ExpenseCategoryService expenseCategoryService) {
		this.expenseCategoryService = expenseCategoryService;
	}

	public PayeeService getPayeeService() {
		return payeeService;
	}

	public void setPayeeService(PayeeService payeeService) {
		this.payeeService = payeeService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public ExpenseGroupService getExpenseGroupService() {
		return expenseGroupService;
	}

	public void setExpenseGroupService(ExpenseGroupService expenseGroupService) {
		this.expenseGroupService = expenseGroupService;
	}

	public CreditCardService getCreditCardService() {
		return creditCardService;
	}

	public void setCreditCardService(CreditCardService creditCardService) {
		this.creditCardService = creditCardService;
	}

	public BankAccountService getBankAccountService() {
		return bankAccountService;
	}

	public void setBankAccountService(BankAccountService bankAccountService) {
		this.bankAccountService = bankAccountService;
	}
	
		
	public List<DescriptionItem> getBankAccountsForAccount(String accountId) {
		
		//logger.debug("getBankAccountsForAccount" + accountId);
		
		List<BankAccount> ccs = bankAccountService.getAllBankAccountsForAccount(accountId);
		List<DescriptionItem> items = new ArrayList<DescriptionItem>();
		for (BankAccount p : ccs) {			
			items.add(new DescriptionItem(p.getId(), p.getDescription()));			
		}
		
		Collections.sort(items, new DescriptionItemComparator());
		//printList(items);
		return items;
	}

	public List<DescriptionItem> getCreditCardsForAccount(String accountId) {
		//logger.debug("getCreditCardsForAccount" + accountId);
		List<CreditCard> ccs = creditCardService.getAllCreditCardsForAccount(accountId);
		List<DescriptionItem> items = new ArrayList<DescriptionItem>();
		for (CreditCard p : ccs) {			
			items.add(new DescriptionItem(p.getId(), p.getDescription()));			
		}
		
		Collections.sort(items, new DescriptionItemComparator());
		//printList(items);
		return items;
	}

	public List<DescriptionItem> getExpenseGroupsForAccount(String accountId) {
		//logger.debug("getExpenseGroupsForAccount" + accountId);
		List<ExpenseGroup> expenseGroups = expenseGroupService.getAllExpenseGroupsForAccount(accountId);
		List<DescriptionItem> list = new ArrayList<DescriptionItem>();
		for (ExpenseGroup eg : expenseGroups) {
			list.add(new DescriptionItem(eg.getId(), eg.getDescription()));
		}
		
		Collections.sort(list, new DescriptionItemComparator());
		//printList(list);
		return list;
	}
	
	public List<DescriptionItem> getExpenseCategoriesForAccount(String accountId) {
		List<ExpenseCategory> categories = expenseCategoryService.getAllExpenseCategoriesForAccount(accountId);
		List<DescriptionItem> types = new ArrayList<DescriptionItem>();
		for (ExpenseCategory cat : categories) {
			DescriptionItem item = new DescriptionItem();
			item.setDescription(cat.getDescription());
			item.setId(cat.getId());
			types.add(item);
		}
		
		Collections.sort(types, new DescriptionItemComparator());
		return types;
	}
	
	public List<DescriptionItem> getExpenseTypesForCategory(String categoryId) {
		ExpenseCategory cat = expenseCategoryService.findExpenseCategory(categoryId);
		List<DescriptionItem> types = new ArrayList<DescriptionItem>();
		List<ExpenseSubType> subTypes = cat.getSubTypes();
		for (ExpenseSubType subType : subTypes) {
			DescriptionItem item = new DescriptionItem();
			item.setDescription(subType.getDescription());
			item.setId(subType.getId());
			types.add(item);
		}
		
		
		Collections.sort(types, new DescriptionItemComparator());
		return types;
		
	}

	public List<DescriptionItem> getExpenseSubTypesForAccount(String accountId) {
		//logger.debug("getExpenseSubTypesForAccount" + accountId);
		List<ExpenseCategory> categories = expenseCategoryService.getAllExpenseCategoriesForAccount(accountId);
		List<DescriptionItem> types = new ArrayList<DescriptionItem>();
		for (ExpenseCategory cat : categories) {
			List<ExpenseSubType> subTypes = cat.getSubTypes();
			for (ExpenseSubType subType : subTypes) {
				DescriptionItem item = new DescriptionItem();
				item.setDescription(cat.getDescription() + ":" + subType.getDescription());
				item.setId(subType.getId());
				types.add(item);
			}
		}
		
		Collections.sort(types, new DescriptionItemComparator());
		//printList(types);
		return types;
	}

	public List<DescriptionItem> getPayeesForAccount(String accountId) {
		//logger.debug("getPayeesForAccount" + accountId);
		List<Payee> payees = payeeService.getAllPayeesForAccount(accountId);
		List<DescriptionItem> items = new ArrayList<DescriptionItem>();
		for (Payee p : payees) {			
			items.add(new DescriptionItem(p.getId(), p.getDescription()));			
		}
		
		Collections.sort(items, new DescriptionItemComparator());
		//printList(items);
		return items;
	}

	public List<DescriptionItem> getUsersForAccount(String accountId) {
		//logger.debug("getUsersForAccount" + accountId);
		//logger.debug("calling userService.getAllUsersForAccount....");
		List<User> users = userService.getAllUsersForAccount(accountId);
		//logger.debug("number of users returned from service = " + users.size());
		List<DescriptionItem> items = new ArrayList<DescriptionItem>();
		for (User p : users) {			
			items.add(new DescriptionItem(p.getId(), p.getFirstName()));			
		}
		
		Collections.sort(items, new DescriptionItemComparator());
		//printList(items);
		return items;
	}
	
	private void printList(List<DescriptionItem> list) {
		for (DescriptionItem item : list) {
			logger.debug(item.getDescription());
		}
	}

	public List<DescriptionItem> getExpensePurposeTypes() {
		List<DescriptionItem> list = new ArrayList<DescriptionItem>();
		list.add(new DescriptionItem(new Long(ExpensePurpose.Personal.toInt()), ExpensePurpose.Personal.toString()));
		list.add(new DescriptionItem(new Long(ExpensePurpose.Business.toInt()), ExpensePurpose.Business.toString()));
		list.add(new DescriptionItem(new Long(ExpensePurpose.Loan.toInt()), ExpensePurpose.Loan.toString()));
		//printList(list);
		return list;
	}

	public List<DescriptionItem> getPaymentMethods() {
		List<DescriptionItem> list = new ArrayList<DescriptionItem>();
		list.add(new DescriptionItem(new Long(PaymentMethod.CASH.toInt()), "Cash"));
		list.add(new DescriptionItem(new Long(PaymentMethod.CHECK.toInt()), "Check"));
		list.add(new DescriptionItem(new Long(PaymentMethod.CASHIERS_CHECK.toInt()), "Cashier's Check"));
		list.add(new DescriptionItem(new Long(PaymentMethod.MONEY_ORDER.toInt()), "Money Order"));
		list.add(new DescriptionItem(new Long(PaymentMethod.CREDIT.toInt()), "Credit"));
		list.add(new DescriptionItem(new Long(PaymentMethod.DEBIT.toInt()), "Debit"));
		list.add(new DescriptionItem(new Long(PaymentMethod.E_PAYMENT.toInt()), "E-Payment"));
		//printList(list);
		return list;
	}
	
	public List<trackit.backingbeans.ExpenseItem> getExpenseData(String accountId,
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
		List<trackit.backingbeans.ExpenseItem> list = new ArrayList<trackit.backingbeans.ExpenseItem>();
		
		List<Expense> expenses = expenseService.getExpenses(accountId, 
				userId, startDate, endDate, payeeId, expensePurposeId, expenseCategoryId, 
				expenseTypeId, paymentMethodId, bankAccountId, creditCardId, projectId);
		
		Map<Long,String> projectLookup = ServicesUtils.getExpenseGroupLookup(
				expenseGroupService, accountId);
		Map<Long,String> payeeLookup = ServicesUtils.getPayeeLookup(payeeService, accountId);
		Map<Long,String> userLookup = ServicesUtils.getUserNameLookup(userService, accountId);
		Map<Long,String> expenseSubTypeLookup = ServicesUtils.getExpenseSubTypeLookup(expenseCategoryService, accountId);
		Map<Long,String> expenseTypeLookup = (expenseCategoryId != null)
				?ServicesUtils.getExpenseTypeLookup(expenseCategoryService, expenseCategoryId)
				:ServicesUtils.getExpenseTypeLookupByAccountId(expenseCategoryService, accountId);
		
		Map<Long,String> expenseCategoryLookupByTypeId = ServicesUtils.getExpenseCategoryLookupByTypeId(expenseCategoryService, accountId);
		Map<Long,String> expenseCategoryLookup = ServicesUtils.getExpenseCategoryLookup(expenseCategoryService, accountId);
		Map<Long,String> creditCardLookup = ServicesUtils.getCreditCardLookup(creditCardService, accountId);
		Map<Long,String> bankAccountLookup = ServicesUtils.getBankAccountLookup(bankAccountService, accountId);
		
		boolean showItemizedDetails = expenseCategoryId != null || expenseTypeId != null || expensePurposeId != null || projectId != null;
		for (Expense expense : expenses) {
			// TODO: getExpenseItems() on each expense may cause very bad performance
			if (expense.getExpenseItems().size() > 0 && showItemizedDetails) {
				continue;
			}
			
			trackit.backingbeans.ExpenseItem item = new trackit.backingbeans.ExpenseItem();
			item.setAmount(expense.getAmount());
			item.setDate(expense.getDate());
			item.setExpenseCategory(expenseCategoryLookupByTypeId.get(expense.getExpenseTypeId()));
			item.setExpenseType(expenseTypeLookup.get(expense.getExpenseTypeId()));
			item.setGroup(projectLookup.get(expense.getExpenseGroupId()));
			item.setPayee(payeeLookup.get(expense.getPayeeId()));
			item.setPaymentMethod(expense.getPaymentMethod().toString());
			item.setUserName(userLookup.get(expense.getUserId()));		
			list.add(item);
		}
		
		// The previous query will not return itemized expenses if any of the following fields was set
		if (showItemizedDetails) {
			List<Object> results = expenseService.findExpensesByExpenseItems(accountId, userId, startDate, endDate, payeeId, expensePurposeId, 
					expenseCategoryId, expenseTypeId, paymentMethodId, bankAccountId, creditCardId, projectId);
			
			List<ExpenseItem> itemList = new ArrayList<ExpenseItem>();
			if (results != null) {
				for (int i=0; i<results.size(); i++) {
					Object[] item = (Object[]) results.get(i);
					Expense e = (Expense) item[0];
					ExpenseItem eItem = (ExpenseItem) item[1];
					
					trackit.backingbeans.ExpenseItem it = new trackit.backingbeans.ExpenseItem();
					it.setAmount(eItem.getAmount().add(eItem.getTax()));
					it.setDate(e.getDate());
					it.setExpenseCategory(expenseCategoryLookupByTypeId.get(eItem.getExpenseTypeId()));
					it.setExpenseType(expenseTypeLookup.get(eItem.getExpenseTypeId()));
					it.setGroup(projectLookup.get(eItem.getExpenseGroupId()));
					it.setPayee(payeeLookup.get(e.getPayeeId()));
					it.setPaymentMethod(e.getPaymentMethod().toString());
					it.setUserName(userLookup.get(e.getUserId()));		
					list.add(it);
					
				}
			}
			
		}
		return list;
	}

	public List<DatedAmount> getExpenseDataForCharting(String accountId,
			String userId, Date startDate, Date endDate, String payeeId,
			String expensePurposeId, String expenseCategoryId,
			String expenseTypeId, String paymentMethodId, String bankAccountId,
			String creditCardId, String projectId, int period) {
		List<trackit.backingbeans.ExpenseItem> list = getExpenseData(accountId, userId, startDate, endDate, payeeId, expensePurposeId,
				expenseCategoryId, expenseTypeId, paymentMethodId, bankAccountId, creditCardId, projectId);
		
//		SimpleDateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");;
//		
//		if (period == 2) {
//			dateFormat = new SimpleDateFormat("mm/yyyy");
//		}
		
		Map<Date,BigDecimal> dataMap = new TreeMap<Date,BigDecimal>(new Comparator<Date>(){
			@Override
			public int compare(Date d1, Date d2) {
				return d1.compareTo(d2);
			}			
		});
		
		for (trackit.backingbeans.ExpenseItem ei : list) {
			Date date = ei.getDate();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			
			if (period == 2) {
				cal.set(Calendar.DAY_OF_MONTH, 1);
			}			
			
			date = cal.getTime();
			
			BigDecimal amount = ei.getAmount();
			if (dataMap.containsKey(date)) {
				amount = amount.add(dataMap.get(date));
			}
			
			dataMap.put(date, amount);			
		}
		
		List<DatedAmount> results = new ArrayList<DatedAmount>();
		for (Date key : dataMap.keySet()) {
			results.add(new DatedAmount(key, dataMap.get(key)));
		}
		
		return results;
	}
	
	

}
